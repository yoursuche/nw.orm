package nw.orm.base.contract;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import nw.orm.base.REntityManagerException;
import nw.orm.query.QueryParameter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Criterion;

public abstract interface IEntityManager {
	public abstract <T> List<T> getAll(Class<? extends T> paramClass);

	public abstract <T> T getById(Class<? extends T> paramClass, Serializable paramSerializable);

	public abstract <T> T getById(Class<? extends T> paramClass, Serializable paramSerializable, boolean paramBoolean);

	public abstract void addSoftRestrictions(Criteria paramCriteria, Class<?> paramClass);

	public abstract <T> T getByCriteria(Class<? extends T> paramClass, Criterion ... paramArrayOfCriterion);

	public abstract <T> List<T> getListByCriteria(Class<? extends T> paramClass, Criterion ... paramArrayOfCriterion);

	public abstract <T> T getByHQL(String paramString, Map<String, Object> paramMap, Class<? extends T> paramClass);

	public abstract <T> T getByHQL(Class<? extends T> paramClass, String paramString, QueryParameter ... paramArrayOfQueryParameter);

	public abstract <T> List<T> getListByHQL(String paramString, Map<String, Object> paramMap, Class<? extends T> paramClass);

	public abstract <T> List<T> getListByHQL(Class<? extends T> paramClass, String paramString, QueryParameter ... paramArrayOfQueryParameter);

	public abstract boolean softDelete(Class<?> paramClass, Serializable paramSerializable);

	public abstract boolean bulkSoftDelete(Class<?> paramClass, List<Serializable> paramList);

	public abstract boolean remove(Object paramObject);

	public abstract boolean remove(Class<?> paramClass, Serializable paramSerializable);

	public abstract boolean bulkRemove(Class<?> paramClass, List<Serializable> paramList);

	public abstract Serializable create(Object paramObject);

	public abstract List<Serializable> createBulk(List<Object> paramList);

	public abstract boolean update(Object paramObject);

	public abstract boolean updateBulk(List<Object> paramList);

	public abstract boolean toggleActive(Class<?> paramClass, Serializable paramSerializable);

	public abstract boolean createOrUpdate(Object paramObject);

	public abstract Session getCurrentSession();

	public abstract SessionFactory getSessionFactory();

	public abstract Session openSession() throws REntityManagerException;

	public abstract Session getActiveSession() throws REntityManagerException;

	public abstract StatelessSession getStatelessSession()
			throws REntityManagerException;

	public abstract SessionFactory getCurrentSessionFactory()
			throws REntityManagerException;
}