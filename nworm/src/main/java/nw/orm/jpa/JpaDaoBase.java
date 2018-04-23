package nw.orm.jpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import nw.orm.core.Entity;
import nw.orm.core.query.QueryParameter;

abstract class JpaDaoBase {
	
	protected EntityManagerFactory em;
	private boolean managedTransaction;
	
	public JpaDaoBase(EntityManagerFactory em, boolean managedTransaction) {
		this.em = em;
		this.managedTransaction = managedTransaction;
	}
	
	/**
	 * Creates an EntityManager instance and begins a transaction
	 * @return EntityManager instance with an active transaction
	 */
	protected EntityManager getEntityManager() {
		EntityManager mgr = em.createEntityManager();
		if(!managedTransaction && !mgr.getTransaction().isActive()){
			mgr.getTransaction().begin();
		}
		return mgr;
	}
	
	/***
	 * Commits and closes a transaction
	 * @param mgr EntityManager
	 */
	public void commit(EntityManager mgr) {
		
		if(!managedTransaction && mgr.isOpen() && mgr.getTransaction().isActive()){
			mgr.getTransaction().commit();
			mgr.clear();
		}
	}
	
	/***
	 * Commits and closes a transaction
	 * @param mgr EntityManager
	 */
	public void rollback(EntityManager mgr) {
		
		if(!managedTransaction && mgr.isOpen() && mgr.getTransaction().isActive()){
			mgr.getTransaction().rollback();;
			mgr.clear();
		}
	}
	
	public void restartTransaction(EntityManager mgr) {
		mgr.getTransaction().commit();
		mgr.clear();
		mgr.getTransaction().begin();
	}
	
	protected boolean isWormEntity(Class<?> clazz) {
		if(Entity.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}
	
	protected String addParameters(boolean mapped, QueryParameter ...criterias) {
		String query = "";
		int start = 0;
		
		if(mapped) {
			query += " WHERE deleted = :deleted";
		}
		
		for (QueryParameter param : criterias) {
			
			if(start == 0 && !mapped) {
				query += " WHERE ";
				start += 1;
			}else {
				query += " AND ";
			}
			
			query += param.toSqlExpression();
			
			start += 1;
		}
		
		return query;
	}
	
	protected void setParameters(boolean mapped, Query query, QueryParameter ...parameters) {
		for (QueryParameter param : parameters) {
			String title = param.getName();
			
			if(param.getTitle() != null) {
				title = param.getTitle();
			}
			query.setParameter(title, param.getValue());
		}
		
		if(mapped){
			query.setParameter("deleted", false);
		}
	}
	
	protected void setParameters(boolean mapped, Query query, Map<String, Object> parameters) {
		for (String name : parameters.keySet()) {
			query.setParameter(name, parameters.get(name));
		}
		
		if(mapped){
			query.setParameter("deleted", false);
		}
	}

}
