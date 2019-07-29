package nw.orm.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nw.orm.core.Entity;
import nw.orm.core.NwormEntity;
import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;
import nw.orm.core.session.HibernateSessionService;
import nw.orm.dao.Dao;
import nw.orm.dao.QueryExecutor;
import nw.orm.hibernate.HDao;
import nw.orm.hibernate.HibernateExecutor;
import nw.orm.hibernate.HibernateDaoFactory;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reference implementation for {@link NwormService} for Hibernate Session
 * Management.
 *
 * @author Ogwara O. Rowland
 * @see NwormService
 */
public abstract class NwormImpl implements NwormHibernateService {

    protected HibernateDaoFactory factory;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Gets the manager.
     *
     * @param configFile the config file
     * @return the manager
     */
    protected static NwormImpl getManager(String configFile) {
        return (NwormImpl) NwormFactory.getManager(configFile);
    }

    /**
     * Put manager.
     *
     * @param file the file
     * @param manager the manager
     */
    protected static void putManager(String file, NwormImpl manager) {
        NwormFactory.putManager(file, manager);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getById(java.lang.Class, java.io.Serializable)
     */
    @Override
    public <T> T getById(Class<T> clazz, Long id) {
        return getById(clazz, id, false);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getById(java.lang.Class, java.io.Serializable, boolean)
     */
    @Override
    public <T> T getById(Class<T> clazz, Long id, boolean lock) {

        HDao<T> dao = factory.getDao(clazz);
        T out = dao.getById(id);
        return out;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getAll(java.lang.Class)
     */
    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return getListByCriteria(clazz);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getListByCriteria(java.lang.Class, org.hibernate.criterion.Criterion[])
     */
    @Override
    public <T> List<T> getListByCriteria(Class<T> clz, Criterion... criteria) {

        HDao<T> dao = factory.getDao(clz);
        List<T> out = dao.list(criteria);
        return out;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getByCriteria(java.lang.Class, org.hibernate.criterion.Criterion[])
     */
    @Override
    public <T> T getByCriteria(Class<T> entityClass, Criterion... criteria) {

        HDao<T> dao = factory.getDao(entityClass);
        T out = dao.find(criteria);
        return out;
    }


    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getByHQL(java.lang.String, java.util.Map, java.lang.Class)
     */
    @Override
    public <T> T getByHQL(String hql, Map<String, Object> parameters, Class<T> resultClass) {

        T out = getByHQL(resultClass, hql, QueryParameter.fromMap(parameters));
        return out;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getByHQL(java.lang.Class, java.lang.String, nw.orm.core.query.QueryParameter[])
     */
    @Override
    public <T> T getByHQL(Class<T> resultClass, String hql, QueryParameter... parameters) {

        QueryExecutor qdao = factory.getExecutor();
        return qdao.query(resultClass, hql, parameters);

    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getListByHQL(java.lang.String, java.util.Map, java.lang.Class)
     */
    @Override
    public <T> List<T> getListByHQL(String hql, Map<String, Object> parameters, Class<T> resultClass) {
        List<T> out = getListByHQL(resultClass, hql, QueryParameter.fromMap(parameters));
        return out;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getListByHQL(java.lang.Class, java.lang.String, nw.orm.core.query.QueryParameter[])
     */
    @Override
    public <T> List<T> getListByHQL(Class<T> resultClass, String hql, QueryParameter... parameters) {

        QueryExecutor qdao = factory.getExecutor();
        return qdao.queryList(resultClass, hql, parameters);

    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getBySQL(java.lang.Class, java.lang.String, nw.orm.core.query.SQLModifier, nw.orm.core.query.QueryParameter[])
     */
    @Override
    public <T> List<T> getBySQL(Class<T> returnClazz, String sql, SQLModifier sqlMod, QueryParameter... params) {
        QueryExecutor dao = factory.getExecutor();
        return dao.getBySQL(returnClazz, sql, sqlMod, params);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getByCriteria(java.lang.Class, nw.orm.core.query.QueryModifier, org.hibernate.criterion.Criterion[])
     */
    @Override
    public <T> T getByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion... criteria) {
        HibernateExecutor dao = factory.getExecutor();
        T out = dao.find(returnClazz, qm, criteria);
        return out;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getListByCriteria(java.lang.Class, nw.orm.core.query.QueryModifier, org.hibernate.criterion.Criterion[])
     */
    @Override
    public <T> List<T> getListByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion... criteria) {
        HibernateExecutor dao = factory.getExecutor();
        return dao.list(returnClazz, qm, criteria);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getByExample(java.lang.Class, org.hibernate.criterion.Example)
     */
    @Override
    public <T> T getByExample(Class<T> clazz, Example example) {
        HDao<T> dao = factory.getDao(clazz);
        List<T> samples = dao.getByExample(example);
        return samples.get(0);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getListByExample(nw.orm.core.query.QueryModifier, org.hibernate.criterion.Example)
     */
    @Override
    public <T> List<T> getListByExample(QueryModifier qm, Example example) {
        HibernateExecutor dao = factory.getExecutor();
        return dao.getListByExample(qm, example);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#executeSQLUpdate(java.lang.String, nw.orm.core.query.QueryParameter[])
     */
    @Override
    public int executeSQLUpdate(String sql, QueryParameter... params) {
        HibernateExecutor dao = factory.getExecutor();
        return dao.execute(sql, params);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#executeHQLUpdate(java.lang.String, nw.orm.core.query.QueryParameter[])
     */
    @Override
    public int executeHQLUpdate(String hql, QueryParameter... params) {
        HibernateExecutor dao = factory.getExecutor();
        return dao.execute(hql, params);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#softDelete(java.lang.Class, java.io.Serializable)
     */
    @Override
    public boolean softDelete(Class<? extends Entity> clazz, Long id) {

        Dao<? extends Entity> dao = factory.getDao(clazz);
        dao.softDelete(id);
        return true;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#bulkSoftDelete(java.lang.Class, java.util.List)
     */
    @Override
    public boolean bulkSoftDelete(Class<? extends Entity> clazz, List<Long> ids) {
        Dao<? extends Entity> dao = factory.getDao(clazz);
        dao.bulkSoftDelete(ids);
        return true;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#remove(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object obj) {
        HDao<? extends Object> dao = factory.getDao(obj.getClass());
        NwormEntity<? extends Long> e = (NwormEntity<? extends Long>) obj;
        dao.deleteById(e.getPk());
        return true;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#remove(java.lang.Class, java.io.Serializable)
     */
    @Override
    public boolean remove(Class<?> clazz, Long pk) {
        Dao<?> dao = factory.getDao(clazz);
        dao.deleteById(pk);
        return true;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#bulkRemove(java.lang.Class, java.util.List)
     */
    @Override
    public boolean bulkRemove(Class<?> clazz, List<Long> pks) {
        Dao<?> dao = factory.getDao(clazz);
        dao.bulkIdDelete(pks);
        return true;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#create(java.lang.Object)
     */
    @Override
    @Deprecated
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Long create(Object obj) {
        HDao dao = factory.getDao(obj.getClass());
        NwormEntity saved = (NwormEntity) dao.save(obj);
        return (Long) saved.getPk();
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#createBulk(java.util.List)
     */
    @Override
    @Deprecated
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<Long> createBulk(List<?> items) {
        Object obj = items.get(0);
        HDao dao = factory.getDao(obj.getClass());
        dao.bulkSave(items);

        ArrayList<Long> ls = new ArrayList<Long>();
        for (Object item : items) {
            NwormEntity saved = (NwormEntity) item;
            ls.add((Long) saved.getPk());
        }

        return ls;
    }

    /**
     * Updates a serializable entity.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    @Deprecated
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean update(Object obj) {
        HDao dao = factory.getDao(obj.getClass());
        Object entity = dao.update(obj);
        return entity != null;
    }

    /**
     * Update bulk.
     *
     * @param items the items
     * @return true if update completed without errors, returns false with
     * rollback if an error occurs
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean updateBulk(List<?> items) {
        Object obj = items.get(0);
        HDao dao = factory.getDao(obj.getClass());
        dao.bulkSave(items);
        return true;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#toggleActive(java.lang.Class, java.io.Serializable)
     */
    @Override
    public boolean toggleActive(Class<? extends Entity> clazz, Long id) {
        Object bc = getByCriteria(clazz, new Criterion[]{Restrictions.idEq(id)});
        if ((bc instanceof Entity)) {
            Entity e = (Entity) bc;
            e.setActive(!e.isActive());
        }
        return update(bc);
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#createOrUpdate(java.lang.Object)
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean createOrUpdate(Object obj) {

        HDao dao = factory.getDao(obj.getClass());
        return dao.saveOrUpdate(obj);
    }

    /**
     * Modify hql.
     *
     * @param hql the hql
     * @param clazz the clazz
     * @return the string
     */
    protected String modifyHQL(String hql, Class<?> clazz) {
        if (Entity.class.isAssignableFrom(clazz)) {
            if (hql.toLowerCase().contains(" where ")) {
                return hql + " and deleted = :deleted";
            }
            return hql + " where deleted = :deleted";
        }
        return hql;
    }

    @Override
    public HibernateSessionService getSessionService() {
        if (factory.isIsInitialized()) {
            return factory.getSxnManager();
        }
        return null;
    }

}
