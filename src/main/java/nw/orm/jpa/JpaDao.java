package nw.orm.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.criterion.Criterion;

import nw.orm.core.query.QueryParameter;
import nw.orm.dao.Dao;
import nw.orm.dao.Paging;

public class JpaDao<T> implements Dao<T> {
	
	private Class<T> entityClass;
	private EntityManagerFactory em;

	protected JpaDao(EntityManagerFactory em, Class<T> clazz) {
		this.em = em;
		this.entityClass = clazz;
	}
	
	public T getById(Serializable id) {
		EntityManager mgr = getEntityManager();
		T item = mgr.find(entityClass, id);
		close(mgr);
		return item;
	}
	
	/**
	 * Creates an EntityManager instance and begins a transaction
	 * @return EntityManager instance with an active transaction
	 */
	private EntityManager getEntityManager() {
		EntityManager mgr = em.createEntityManager();
		mgr.getTransaction().begin();
		return mgr;
	}
	
	/***
	 * Commits and closes a transaction
	 * @param mgr
	 */
	private void close(EntityManager mgr) {
		mgr.getTransaction().commit();
		mgr.clear();
	}

	@Override
	public T save(T item) {
		EntityManager mgr = getEntityManager();
		mgr.persist(item);
		close(mgr);
		return item;
	}

	@Override
	public void delete(T item) {
		EntityManager mgr = getEntityManager();
		item = mgr.merge(item);
		mgr.remove(item);
		close(mgr);
		
	}

	@Override
	public T update(T item) {
		EntityManager mgr = getEntityManager();
		T t = mgr.merge(item);
		close(mgr);
		return t;
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
	public T get(Criterion... criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Serializable pk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bulkSave(List<T> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bulkDelete(List<T> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bulkIdDelete(List<Serializable> pks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void softDelete(Serializable id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bulkSoftDelete(List<Serializable> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<T> list(Criterion... criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> list(Paging paging, Criterion... criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
