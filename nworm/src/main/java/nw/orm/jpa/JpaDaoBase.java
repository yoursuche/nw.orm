package nw.orm.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nw.orm.core.Entity;
import nw.orm.core.query.QueryParameter;
import nw.orm.filters.Filter;

abstract class JpaDaoBase {
	
	EntityManagerFactory em;
	boolean managedTransaction;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
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
			mgr.close();
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
			mgr.close();
		}
	}
	
	public void restartTransaction(EntityManager mgr) {
		mgr.getTransaction().commit();
		mgr.clear();
		mgr.getTransaction().begin();
	}
	
	protected boolean isMappedEntity(Class<?> clazz) {
		try {
			em.getMetamodel().managedType(clazz);
			return true;
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return false;
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
	
	protected <T> String buildQuery(Class<T> daoEntity, boolean isWorm, Filter ...filters) {
		
		String query = "FROM " + daoEntity.getSimpleName();
		if(isWorm) {
			query += " WHERE deleted = :deleted";
		}
		
		int start = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		for (Filter filter : filters) {
			if(start == 0 && !isWorm) {
				query += " WHERE ";
				start += 1;
			}else {
				query += " AND ";
			}
			query += filter.query();
			params.putAll(filter.params());
		}
		return query;
	}

}
