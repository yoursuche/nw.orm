package nw.orm.core.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import nw.orm.core.NwormEntity;
import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;
import nw.orm.core.service.NwormFactory;
import nw.orm.core.service.NwormService;
import nw.orm.core.session.HibernateSessionService;

public abstract class NwormJpaImpl implements NwormService {

	protected EntityManagerFactory emFactory;

	protected static NwormJpaImpl getManager(String unitName) {
		return (NwormJpaImpl) NwormFactory.getManager("JPA:" + unitName);
	}

	protected static void putManager(String unitName, NwormJpaImpl manager) {
		NwormFactory.putManager("JPA:" + unitName, manager);
	}

	@Override
	public <T> T getById(Class<T> entityClass, Serializable id) {
		return getById(entityClass, id, false);
	}

	@Override
	public <T> T getById(Class<T> entityClass, Serializable id, boolean lock) {
		T out = null;
		EntityManager em = emFactory.createEntityManager();
		if(lock){
			out = em.find(entityClass, id, LockModeType.WRITE);
		}else{
			out = em.find(entityClass, id);
		}
//		em.flush();
		em.close();
		return out;
	}

	@Override
	public <T> List<T> getAll(Class<T> paramClass) {
		EntityManager em = emFactory.createEntityManager();
//		em.getA
		return null;
	}

	@Override
	public <T> T getByCriteria(Class<T> paramClass,
			Criterion... paramArrayOfCriterion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getListByCriteria(Class<T> paramClass,
			Criterion... paramArrayOfCriterion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getByHQL(String hql, Map<String, Object> paramMap,
			Class<T> paramClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getByHQL(Class<T> paramClass, String hql,
			QueryParameter... paramArrayOfQueryParameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getListByHQL(String hql, Map<String, Object> paramMap,
			Class<T> paramClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getListByHQL(Class<T> paramClass, String paramString,
			QueryParameter... paramArrayOfQueryParameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getBySQL(Class<T> returnClazz, String sql,
			SQLModifier sqlMod, QueryParameter... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getByCriteria(Class<T> returnClazz, QueryModifier qm,
			Criterion... paramArrayOfCriterion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getListByCriteria(Class<T> returnClazz,
			QueryModifier qm, Criterion... paramArrayOfCriterion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getByExample(Class<T> clazz, Example example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getListByExample(QueryModifier qm, Example example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int executeSQLUpdate(String sql, QueryParameter... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeHQLUpdate(String hql, QueryParameter... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean softDelete(Class<? extends NwormEntity<?>> paramClass,
			Serializable paramSerializable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bulkSoftDelete(Class<? extends NwormEntity<?>> paramClass,
			List<Serializable> paramList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Class<?> paramClass, Serializable paramSerializable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bulkRemove(Class<?> paramClass, List<Serializable> paramList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable create(Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Serializable> createBulk(List<?> paramList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBulk(List<?> paramList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggleActive(Class<? extends NwormEntity<?>> paramClass,
			Serializable paramSerializable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createOrUpdate(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HibernateSessionService getSessionService() {
		// TODO Auto-generated method stub
		return null;
	}

}
