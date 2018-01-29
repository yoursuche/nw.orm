package nw.orm.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nw.orm.core.Entity;
import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryParameter;
import nw.orm.dao.Paging;

public class JpaDao<T> extends JpaDaoBase implements JDao<T> {
	
	private Class<T> entityClass;

	protected JpaDao(EntityManagerFactory em, Class<T> clazz, boolean managedTransaction) {
		super(em, managedTransaction);
		this.entityClass = clazz;
	}
	
	public T getById(Serializable id) {
		EntityManager mgr = getEntityManager();
		T item = mgr.find(entityClass, id);
		commit(mgr);
		return item;
	}
	
	@Override
	public T save(T item) {
		EntityManager mgr = getEntityManager();
		try {
			mgr.persist(item);
			commit(mgr);
			return item;
		} catch (EntityExistsException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Entity already exists", e);
		} catch (TransactionRequiredException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}catch (IllegalArgumentException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Object not an entity", e);
		}
	}

	@Override
	public void delete(T item) {
		EntityManager mgr = getEntityManager();
		try {
			T merged = mgr.merge(item);
			mgr.remove(merged);
			commit(mgr);
		} catch (TransactionRequiredException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}catch (IllegalArgumentException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Object not an entity or already detached", e);
		}
		
	}
	
	@Override
	public void deleteById(Serializable pk) {
		EntityManager mgr = getEntityManager();
		try {
			T item = mgr.find(entityClass, pk);
			mgr.remove(item);
			commit(mgr);
		} catch (TransactionRequiredException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}catch (IllegalArgumentException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Not an entity or already detached", e);
		}
	}

	@Override
	public T update(T item) {
		EntityManager mgr = getEntityManager();
		T t;
		try {
			t = mgr.merge(item);
			commit(mgr);
			return t;
		} catch (TransactionRequiredException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}catch (IllegalArgumentException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Not an entity or already removed", e);
		}
	}
	

//	@Override
	@SuppressWarnings("unchecked")
	public T getByQuery(String query, QueryParameter... parameters) {
		EntityManager mgr = getEntityManager();
		Query cQuery = mgr.createQuery(query);
		
		for (QueryParameter param : parameters) {
			cQuery.setParameter(param.getName(), param.getValue());
		}
		
		try {
			T singleResult = (T)cQuery.getSingleResult();
			return singleResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
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
		} catch (EntityExistsException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Entity already exists", e);
		} catch (TransactionRequiredException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}catch (IllegalArgumentException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Object not an entity", e);
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
		} catch (TransactionRequiredException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - No active transaction to use", e);
		}catch (IllegalArgumentException e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception - Object not an entity", e);
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
			
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	public List<T> list(CriteriaQuery<T> query) {
		EntityManager mgr = getEntityManager();
		TypedQuery<T> q = mgr.createQuery(query);
		return null;
	}

	@Override
	public List<T> list(QueryParameter ... parameters) {
		
		return list(null, parameters);
	}

	@Override
	public List<T> list(Paging paging, QueryParameter... parameters) {
		
		EntityManager mgr = getEntityManager();
		CriteriaBuilder builder = mgr.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> root = query.from(entityClass);
		
		TypedQuery<T> q = mgr.createQuery(query);
		for (QueryParameter param : parameters) {
			q.setParameter(param.getName(), param.getValue());
		}
		
		if(paging != null) {
			q.setFirstResult(paging.getPageOffset());
			q.setMaxResults(paging.getPageSize());
		}
		List<T> result = q.getResultList();
		return q.getResultList();
	}
	
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		EntityManager mgr = getEntityManager();
		return mgr.getCriteriaBuilder();
	}

}
