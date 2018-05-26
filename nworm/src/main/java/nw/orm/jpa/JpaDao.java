package nw.orm.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import nw.orm.core.Entity;
import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryParameter;
import nw.orm.dao.Paging;
import nw.orm.filters.Filter;

public class JpaDao<T> extends JpaDaoBase implements JDao<T> {
	
	private String entityName;
	private Class<T> entityClass;
	
	protected JpaDao(EntityManagerFactory em, Class<T> clazz, boolean managedTransaction) {
		super(em, managedTransaction);
		this.entityClass = clazz;
		this.entityName = clazz.getSimpleName();
	}
	
	public T getById(Serializable id) {
		EntityManager mgr = getEntityManager();
		T item = mgr.find(entityClass, id);
		commit(mgr);
		
		if(item != null && isWormEntity(entityClass)) {
			Entity e = (Entity) item;
			if(e.isDeleted()) {
				return null;
			}
		}
		
		return item;
	}
	
	@Override
	public T save(T item) {
		EntityManager mgr = getEntityManager();
		try {
			mgr.persist(item);
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - ", e);
		}
		return item;
	}
	
	@Override
	public boolean saveOrUpdate(T entity) {
		
		try {
			save(entity);
		} catch (EntityExistsException e) {
			update(entity);
		}
		return true;
	}

	@Override
	public void delete(T item) {
		EntityManager mgr = getEntityManager();
		try {
			T merged = mgr.merge(item);
			mgr.remove(merged);
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}
		
	}
	
	@Override
	public void deleteById(Serializable pk) {
		EntityManager mgr = getEntityManager();
		try {
			T item = mgr.find(entityClass, pk);
			mgr.remove(item);
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}
	}

	@Override
	public T update(T item) {
		EntityManager mgr = getEntityManager();
		T t;
		try {
			t = mgr.merge(item);
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}
		return t;
	}
	
	@Override
	public T find(QueryParameter... parameters) {
		
		T result = null;
		boolean isMapped = isWormEntity(entityClass);
		String query = "FROM " + this.entityName + this.addParameters(isMapped, parameters);
		EntityManager mgr = getEntityManager();
		
		try {
			TypedQuery<T> cQuery = mgr.createQuery(query, entityClass);
			
			setParameters(isMapped, cQuery, parameters);
			result = cQuery.getSingleResult();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			logger.warn("Nworm Error - ", e);
		}
		return result;
	}

	@Override
	public void bulkSave(List<T> entities) {
		EntityManager mgr = getEntityManager();
		
		try {
			long counter = 0l;
			for (T entity : entities) {
				mgr.persist(entity);
				
				if (counter == 10000L) {
					restartTransaction(mgr);
				}else {
					counter = 0L;
				}
				counter += 1;
			}
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - ", e);
		}
	}

	@Override
	public void bulkDelete(List<T> entities) {
		EntityManager mgr = getEntityManager();
		try {
			long counter = 0l;
			for (T entity : entities) {
				
				T merged = mgr.merge(entity);
				mgr.remove(merged);
				
				if (counter == 10000l) {
					restartTransaction(mgr);
				}else {
					counter = 0l;
				}
				counter += 1l;
			}
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception -", e);
		}
	}

	@Override
	public void bulkIdDelete(List<Serializable> pks) {
		EntityManager mgr = getEntityManager();
		try {
			long counter = 0l;
			for (Serializable pk : pks) {
				T entity = mgr.find(entityClass, pk);
				mgr.remove(entity);
				
				if (counter == 10000l) {
					restartTransaction(mgr);
				}else {
					counter = 0l;
				}
				counter += 1l;
			}
			commit(mgr);
		} catch (TransactionRequiredException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}catch (IllegalArgumentException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Object not an entity", e);
		}
	}

	@Override
	public void softDelete(Serializable id) {
		EntityManager mgr = getEntityManager();
		try {
			T entity = mgr.find(entityClass, id);
			if ((entity instanceof Entity)) {
				Entity e = (Entity) entity;
				e.setDeleted(true);
				mgr.merge(e);
			}
			commit(mgr);
		} catch (TransactionRequiredException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}catch (IllegalArgumentException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Not an entity or already removed", e);
		}
	}

	@Override
	public void bulkSoftDelete(List<Serializable> pks) {
		EntityManager mgr = getEntityManager();
		try {
			long counter = 0l;
			for (Serializable pk : pks) {
				T entity = mgr.find(entityClass, pk);
				if ((entity instanceof Entity)) {
					Entity e = (Entity) entity;
					e.setDeleted(true);
					mgr.merge(e);
				}
				
				if (counter == 10000l) {
					restartTransaction(mgr);
				}else {
					counter = 0l;
				}
				counter += 1l;
			}
			commit(mgr);
		} catch (TransactionRequiredException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}catch (IllegalArgumentException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Object not an entity", e);
		}
	}
	
	public T get(CriteriaQuery<T> query, QueryParameter ...parameters) {
		
		EntityManager mgr = getEntityManager();
		try {
			TypedQuery<T> q = mgr.createQuery(query);
			for (QueryParameter param : parameters) {
				q.setParameter(param.getName(), param.getValue());
			}
			if(isWormEntity(entityClass)) {
				q.setParameter("deleted", false);
			}
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}

	@Override
	public List<T> list(QueryParameter ... parameters) {
		return list(null, parameters);
	}

	@Override
	public List<T> list(Paging paging, QueryParameter... parameters) {
		return list(Boolean.TRUE, paging, parameters);
		
	}
	
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		EntityManager mgr = getEntityManager();
		return mgr.getCriteriaBuilder();
	}

	@Override
	public List<T> deleted(Paging paging) {
		return list(Boolean.TRUE, paging);
	}
	
	@SuppressWarnings("unchecked")
	private List<T> list(boolean hard, Paging paging, QueryParameter... parameters) {
		
		List<T> list = new ArrayList<T>();
		EntityManager mgr = getEntityManager();
		
		String query = "FROM " + this.entityName + this.addParameters(isWormEntity(entityClass), parameters);
		Query cQuery = mgr.createQuery(query);
		
		setParameters(isWormEntity(entityClass), cQuery, parameters);
		
		if(paging != null) {
			cQuery.setFirstResult(paging.getPageOffset());
			cQuery.setMaxResults(paging.getPageSize());
		}
		
		try {
			list = cQuery.getResultList();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			logger.warn("Nworm Error - ", e);
		}
		
		return list;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> filter(Paging paging, Filter... filters) {
		
		List<T> list = new ArrayList<T>();
		EntityManager mgr = getEntityManager();
		
		String query = "FROM " + this.entityName;
		
		boolean isWorm = isWormEntity(entityClass);
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
		Query cQuery = mgr.createQuery(query);
		setParameters(isWorm, cQuery, params);
		
		if(paging != null) {
			cQuery.setFirstResult(paging.getPageOffset());
			cQuery.setMaxResults(paging.getPageSize());
		}
		
		try {
			list = cQuery.getResultList();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			logger.warn("Nworm Error - ", e);
		}
		
		return list;
	}

	@Override
	public T select(Filter... filters) {
		
		T selected = null;
		EntityManager mgr = getEntityManager();
		
		String query = "FROM " + this.entityName;
		
		boolean isWorm = isWormEntity(entityClass);
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
		TypedQuery<T> cQuery = mgr.createQuery(query, entityClass);
		setParameters(isWorm, cQuery, params);
		
		
		
		try {
			selected = cQuery.getSingleResult();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			logger.warn("Nworm Error - ", e);
		}
		
		return selected;
		
	}

}
