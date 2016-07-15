package nw.orm.core.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nw.orm.core.Entity;
import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;
import nw.orm.core.service.NwormFactory;

// TODO: Auto-generated Javadoc
/**
 * Nworm for JPA.
 *
 * @author Rowland
 */
public abstract class NwormJpaImpl implements NwormJpaService {

	/** The entity manager factory. */
	protected EntityManagerFactory emFactory;

	/**
	 * Gets the cached entity manager factory
	 *
	 * @param unitName the unit name
	 * @return the manager
	 */
	protected static NwormJpaImpl getManager(String unitName) {
		return (NwormJpaImpl) NwormFactory.getManager("JPA:" + unitName);
	}

	/**
	 * Put manager in cache.
	 *
	 * @param unitName the unit name
	 * @param manager the manager
	 */
	protected static void putManager(String unitName, NwormJpaImpl manager) {
		NwormFactory.putManager("JPA:" + unitName, manager);
	}

	/**
	 * Adds the query parameters to jpa TypedQuery.
	 *
	 * @param <X> the generic type
	 * @param query the query
	 * @param params the params
	 */
	protected <X> void addQueryParameters(Query query, QueryParameter ... params){
		if(params != null){
			for(QueryParameter qp: params){
				query.setParameter(qp.getName(), qp.getValue());
			}
		}
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#getById(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T getById(Class<T> entityClass, Serializable id) {
		return getById(entityClass, id, false);
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#getById(java.lang.Class, java.io.Serializable, boolean)
	 */
	@Override
	public <T> T getById(Class<T> entityClass, Serializable id, boolean lock) {
		T out = null;
		EntityManager em = emFactory.createEntityManager();
		if(lock){
			out = em.find(entityClass, id, LockModeType.WRITE);
		}else{
			out = em.find(entityClass, id);
		}
		em.close();
		return out;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#getAll(java.lang.Class)
	 */
	@Override
	public <T> List<T> getAll(Class<T> resultClazz) {

		List<T> out = new ArrayList<T>();

		EntityManager em = emFactory.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(resultClazz);
		Root<T> from = query.from(resultClazz);
		query.select(from);
		TypedQuery<T> tq = em.createQuery(query);

		try {
			out = tq.getResultList();
		} catch (Exception e) {
			throw new NwormQueryException("", e);
		}

		return out;
	}

	/**
	 * Gets the by jpql.
	 *
	 * @param <T> the generic type
	 * @param returnClazz the return clazz
	 * @param jpql the jpql
	 * @param params the params
	 * @return the by jpql
	 */
	public <T> T getByJPQL(Class<T> returnClazz, String jpql, QueryParameter ... params){
		EntityManager em = emFactory.createEntityManager();

		TypedQuery<T> query = em.createQuery(jpql, returnClazz);
		addQueryParameters(query, params);
		return query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#getBySQL(java.lang.Class, java.lang.String, nw.orm.core.query.SQLModifier, nw.orm.core.query.QueryParameter[])
	 */
	@Override
	public <T> List<T> getBySQL(Class<T> returnClazz, String sql, SQLModifier sqlMod, QueryParameter... params) {
		EntityManager em = emFactory.createEntityManager();
		Query query = em.createQuery(sql);
		addQueryParameters(query, params);
		return null;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#executeSQLUpdate(java.lang.String, nw.orm.core.query.QueryParameter[])
	 */
	@Override
	public int executeSQLUpdate(String sql, QueryParameter... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#executeHQLUpdate(java.lang.String, nw.orm.core.query.QueryParameter[])
	 */
	@Override
	public int executeHQLUpdate(String hql, QueryParameter... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#softDelete(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public boolean softDelete(Class<? extends Entity<?>> paramClass,
			Serializable paramSerializable) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#bulkSoftDelete(java.lang.Class, java.util.List)
	 */
	@Override
	public boolean bulkSoftDelete(Class<? extends Entity<?>> paramClass,
			List<Serializable> paramList) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#remove(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public boolean remove(Class<?> paramClass, Serializable paramSerializable) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#bulkRemove(java.lang.Class, java.util.List)
	 */
	@Override
	public boolean bulkRemove(Class<?> paramClass, List<Serializable> paramList) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#create(java.lang.Object)
	 */
	@Override
	public Serializable create(Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#createBulk(java.util.List)
	 */
	@Override
	public List<Serializable> createBulk(List<?> paramList) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#update(java.lang.Object)
	 */
	@Override
	public boolean update(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#updateBulk(java.util.List)
	 */
	@Override
	public boolean updateBulk(List<?> paramList) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#toggleActive(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public boolean toggleActive(Class<? extends Entity<?>> paramClass,
			Serializable paramSerializable) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.IService#createOrUpdate(java.lang.Object)
	 */
	@Override
	public boolean createOrUpdate(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

}
