/* 
 * Copyright 2013 - 2015, Neemworks Nigeria <nw.orm@nimworks.com>
 Permission to use, copy, modify, and distribute this software for any
 purpose with or without fee is hereby granted, provided that the above
 copyright notice and this permission notice appear in all copies.

 THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package nw.orm.core.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nw.orm.core.NwormEntity;
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
	protected <X> void addQueryParameters(TypedQuery<X> query, QueryParameter ... params){
		if(params != null){
			for(QueryParameter qp: params){
				query.setParameter(qp.getName(), qp.getValue());
			}
		}
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getById(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T getById(Class<T> entityClass, Serializable id) {
		return getById(entityClass, id, false);
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getById(java.lang.Class, java.io.Serializable, boolean)
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
	 * @see nw.orm.core.service.NwormService#getAll(java.lang.Class)
	 */
	@Override
	public <T> List<T> getAll(Class<T> resultClazz) {
		EntityManager em = emFactory.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(resultClazz);
		Root<T> from = query.from(resultClazz);
		query.select(from);
		TypedQuery<T> tq = em.createQuery(query);
		return tq.getResultList();
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
	 * @see nw.orm.core.service.NwormService#getBySQL(java.lang.Class, java.lang.String, nw.orm.core.query.SQLModifier, nw.orm.core.query.QueryParameter[])
	 */
	@Override
	public <T> List<T> getBySQL(Class<T> returnClazz, String sql, SQLModifier sqlMod, QueryParameter... params) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#executeSQLUpdate(java.lang.String, nw.orm.core.query.QueryParameter[])
	 */
	@Override
	public int executeSQLUpdate(String sql, QueryParameter... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#executeHQLUpdate(java.lang.String, nw.orm.core.query.QueryParameter[])
	 */
	@Override
	public int executeHQLUpdate(String hql, QueryParameter... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#softDelete(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public boolean softDelete(Class<? extends NwormEntity<?>> paramClass,
			Serializable paramSerializable) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#bulkSoftDelete(java.lang.Class, java.util.List)
	 */
	@Override
	public boolean bulkSoftDelete(Class<? extends NwormEntity<?>> paramClass,
			List<Serializable> paramList) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#remove(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public boolean remove(Class<?> paramClass, Serializable paramSerializable) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#bulkRemove(java.lang.Class, java.util.List)
	 */
	@Override
	public boolean bulkRemove(Class<?> paramClass, List<Serializable> paramList) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#create(java.lang.Object)
	 */
	@Override
	public Serializable create(Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#createBulk(java.util.List)
	 */
	@Override
	public List<Serializable> createBulk(List<?> paramList) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#update(java.lang.Object)
	 */
	@Override
	public boolean update(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#updateBulk(java.util.List)
	 */
	@Override
	public boolean updateBulk(List<?> paramList) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#toggleActive(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public boolean toggleActive(Class<? extends NwormEntity<?>> paramClass,
			Serializable paramSerializable) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#createOrUpdate(java.lang.Object)
	 */
	@Override
	public boolean createOrUpdate(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

}
