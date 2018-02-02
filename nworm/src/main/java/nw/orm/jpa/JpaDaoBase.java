package nw.orm.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import nw.orm.core.query.QueryParameter;

public abstract class JpaDaoBase {
	
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
	
	protected String addParameter(QueryParameter ...criterias) {
		String query = "";
		int start = 0;
		for (QueryParameter param : criterias) {
			
			if(start == 0) {
				query += " WHERE ";
				start += 1;
			}else {
				query += " AND ";
			}
			
			if(param.getTitle() != null)
				query += param.getName() + " = :" + param.getTitle();
			else {
				query += param.getName() + " = :" + param.getName();
			}
			
		}
		return query;
	}

}
