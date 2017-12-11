package nw.orm.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import nw.orm.core.Entity;
import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryAlias;
import nw.orm.core.query.QueryFetchMode;
import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;
import nw.orm.core.session.HibernateSessionFactory;
import nw.orm.core.session.HibernateSessionService;
import nw.orm.dao.Dao;
import nw.orm.dao.DaoFactory;
import nw.orm.dao.GenericQueryDao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxyHelper;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reference implementation for {@link NwormService} for Hibernate Session Management.
 *
 * @author Ogwara O. Rowland
 * @see NwormService
 */
public abstract class NwormImpl implements NwormHibernateService {

	/** Hibernate Session Factory instance. */
	protected HibernateSessionFactory conf;

	/** The sxn manager. */
	protected HibernateSessionService sxnManager;

	/** The initialized successfully. */
	private boolean initializedSuccessfully;

	private String classId = UUID.randomUUID().toString();
	
	private DaoFactory factory;
	
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

	/**
	 * Configures how nworm uses transactions and sessions.
	 *
	 * @param useTxns configures how nworm uses transactions (to use JTA, use false)
	 * @param useCurrent configures nworm to use current session instead of openning session each time
	 */
	public void configureSessionManager(boolean useTxns, boolean useCurrent){
		if(useCurrent){
			sxnManager.enableCurrentSession();
		}else{
			sxnManager.disableCurrentSession();
		}
		if(useTxns){
			sxnManager.enableTransactions();
		}else{
			sxnManager.disableTransactions();
		}
	}

	/**
	 * Checks if is class mapped.
	 *
	 * @param clazz the clazz
	 * @return true, if is class mapped
	 */
	public boolean isClassMapped(Class<?> clazz) {
		try {
			return sxnManager.getFactory().getClassMetadata(
					HibernateProxyHelper.getClassWithoutInitializingProxy(clazz.newInstance())) != null;
		} catch (InstantiationException e) {
			this.logger.error("Exception: ", e);
		} catch (IllegalAccessException e) {
			this.logger.error("Exception: ", e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getById(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T getById(Class<T> clazz, Serializable id) {
		return getById(clazz, id, false);
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getById(java.lang.Class, java.io.Serializable, boolean)
	 */
	@Override
	public <T> T getById(Class<T> clazz, Serializable id, boolean lock) {
		
		Dao<T> dao = factory.getGenericDao(clazz);
		T out = dao.getById(clazz);
		return out;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getAll(java.lang.Class)
	 */
	@Override
	public <T> List<T> getAll(Class<T> clazz) {
		return getListByCriteria(clazz);
	}

	/**
	 * Filters out deleted entries from queries.
	 *
	 * @param te the te
	 * @param clazz the clazz
	 */
	public void addSoftRestrictions(Criteria te, Class<?> clazz) {
		if (Entity.class.isAssignableFrom(clazz)){
			te.add(Restrictions.eq("deleted", Boolean.valueOf(false)));
		}
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getByCriteria(java.lang.Class, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <T> T getByCriteria(Class<T> entityClass, Criterion ... criteria) {
		Dao<T> dao = factory.getGenericDao(entityClass);
		T out = dao.getByCriteria(criteria);
		return out;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getListByCriteria(java.lang.Class, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <T> List<T> getListByCriteria(Class<T> clz, Criterion ... criteria) {
		
		Dao<T> dao = factory.getGenericDao(clz);
		List<T> out = dao.getListByCriteria(criteria);
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
	@SuppressWarnings("unchecked")
	public <T> T getByHQL(Class<T> resultClass, String hql, QueryParameter ... parameters) {
		T out = null;
		boolean isMapped = isClassMapped(resultClass);
		Session session = sxnManager.getManagedSession();
		try {

//	TODO		if (Entity.class.isAssignableFrom(resultClass)) {
//				hql = modifyHQL(hql, resultClass);
//			}

			Query query = session.createQuery(hql);
			for (QueryParameter rp : parameters) {
				query.setParameter(rp.getName(), rp.getValue());
			}

			if (isMapped){
				out = (T) query.uniqueResult();
				Entity entity = (Entity)out;
				if(entity.isDeleted()){
					out = null;
				}
			}else {
				out = (T) query.setResultTransformer(Transformers.aliasToBean(resultClass)).uniqueResult();
			}
			sxnManager.commit(session);
		} catch (HibernateException e) {
			sxnManager.rollback(session);
			sxnManager.closeSession(session);
			throw new NwormQueryException("", e);
		}
		sxnManager.closeSession(session);
		return out;
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
	@SuppressWarnings("unchecked")
	public <T> List<T> getListByHQL(Class<T> resultClass, String hql, QueryParameter ... parameters) {
		List<T> out = new ArrayList<T>();
		boolean isMapped = isClassMapped(resultClass);
		Session session = sxnManager.getManagedSession();
		try {
//			if (Entity.class.isAssignableFrom(resultClass)) {
//	TODO			hql = modifyHQL(hql, resultClass);
//			}
			Query query = session.createQuery(hql);
			for (QueryParameter rp : parameters) {
				query.setParameter(rp.getName(), rp.getValue());
			}
//			if (Entity.class.isAssignableFrom(resultClass)) {
//				query.setBoolean("deleted", false);
//			}
			if (isMapped){
				out = query.list();
			}else{
				out = query.setResultTransformer(Transformers.aliasToBean(resultClass)).list();
			}

			sxnManager.commit(session);
		} catch (HibernateException e) {
			sxnManager.rollback(session);
			sxnManager.closeSession(session);
			throw new NwormQueryException("", e);
		}
		sxnManager.closeSession(session);
		return out;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getBySQL(java.lang.Class, java.lang.String, nw.orm.core.query.SQLModifier, nw.orm.core.query.QueryParameter[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getBySQL(Class<T> returnClazz, String sql, SQLModifier sqlMod, QueryParameter ... params){
		List<T> out = new ArrayList<T>();
		Session session = sxnManager.getManagedSession();
		SQLQuery te = session.createSQLQuery(sql);

		if (params != null) {
			for (QueryParameter param : params) {
				te.setParameter(param.getName(), param.getValue());
			}
		}

		if(sqlMod != null){
			if(returnClazz != null && isClassMapped(returnClazz)){
				te.addEntity(returnClazz);
			}

			if(returnClazz != null && !isClassMapped(returnClazz)){
				te.setResultTransformer(Transformers.aliasToBean(returnClazz));
			}
//			if(Entity.class.isAssignableFrom(returnClazz)){
//				te.setParameter("deleted", false);
//			}
			if(sqlMod.isPaginated()){
				te.setFirstResult(sqlMod.getPageIndex());
				te.setMaxResults(sqlMod.getMaxResult());
			}
		}

		try {
			out = te.list();
		} catch (Exception e) {
			sxnManager.rollback(session);
			sxnManager.closeSession(session);
			throw new NwormQueryException("", e);
		}
		sxnManager.commit(session);
		sxnManager.closeSession(session);
		return out;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getByCriteria(java.lang.Class, nw.orm.core.query.QueryModifier, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <T> T getByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria){
		Dao<T> dao = factory.getGenericDao(returnClazz);
		T out = dao.getByCriteria(qm, criteria);
		return out;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getListByCriteria(java.lang.Class, nw.orm.core.query.QueryModifier, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <T> List<T> getListByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria){
		Dao<T> dao = factory.getGenericDao(returnClazz);
		List<T> out = dao.getListByCriteria(qm, criteria);
		return out;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getByExample(java.lang.Class, org.hibernate.criterion.Example)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getByExample(Class<T> clazz, Example example){
		T out = null;
		Session sxn = sxnManager.getManagedSession();
		Criteria te = sxn.createCriteria(clazz).add(example);
		try {
			logger.debug(te.list() + "");
			out = (T) te.list().get(0);
			sxnManager.commit(sxn);
		} catch (HibernateException e) {
			sxnManager.rollback(sxn);
			sxnManager.closeSession(sxn);
			throw new NwormQueryException("", e);
		}
		sxnManager.closeSession(sxn);
		return out;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getListByExample(nw.orm.core.query.QueryModifier, org.hibernate.criterion.Example)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getListByExample(QueryModifier qm, Example example){
		List<T> items = new ArrayList<T>();
		Session sxn = sxnManager.getManagedSession();
		Criteria te = sxn.createCriteria(qm.getQueryClazz()).add(example);
		try {
			modifyCriteria(te, qm);
			items = te.list();
			sxnManager.commit(sxn);
		} catch (HibernateException e) {
			sxnManager.rollback(sxn);
			sxnManager.closeSession(sxn);
			throw new NwormQueryException("", e);
		}
		sxnManager.closeSession(sxn);
		return items;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#executeSQLUpdate(java.lang.String, nw.orm.core.query.QueryParameter[])
	 */
	@Override
	public int executeSQLUpdate(String sql, QueryParameter ... params){
		Session session = sxnManager.getManagedSession();
		SQLQuery query = session.createSQLQuery(sql);
		if (params != null) {
			for (QueryParameter param : params) {
				query.setParameter(param.getName(), param.getValue());
			}
		}
		int o = -1;
		try {
			o = query.executeUpdate();
		} catch (Exception e) {
			sxnManager.rollback(session);
			sxnManager.closeSession(session);
			throw new NwormQueryException("", e);
		}
		sxnManager.commit(session);
		sxnManager.closeSession(session);
		return o;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#executeHQLUpdate(java.lang.String, nw.orm.core.query.QueryParameter[])
	 */
	@Override
	public int executeHQLUpdate(String hql, QueryParameter ... params){
		Session session = sxnManager.getManagedSession();
		org.hibernate.Query query = session.createQuery(hql);
		if (params != null) {
			for (QueryParameter param : params) {
				query.setParameter(param.getName(), param.getValue());
			}
		}
		int o = -1;
		try {
			o = query.executeUpdate();
		} catch (Exception e) {
			sxnManager.rollback(session);
			sxnManager.closeSession(session);
			throw new NwormQueryException("", e);
		}
		sxnManager.commit(session);
		sxnManager.closeSession(session);
		return o;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#softDelete(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public boolean softDelete(Class<? extends Entity> clazz, Serializable id) {
		
		Dao<? extends Entity> dao = factory.getGenericDao(clazz);
		return dao.softDelete(id);
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#bulkSoftDelete(java.lang.Class, java.util.List)
	 */
	@Override
	public boolean bulkSoftDelete(Class<? extends Entity> clazz, List<Serializable> ids) {
		Dao<? extends Entity> dao = factory.getGenericDao(clazz);
		return dao.bulkSoftDelete(ids);
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object obj) {
		GenericQueryDao dao = factory.getGenericQueryDao();
		return dao.delete(obj);
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#remove(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public boolean remove(Class<?> clazz, Serializable pk) {
		Dao<?> dao = factory.getGenericDao(clazz);
		dao.deleteById(pk);
		return true;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#bulkRemove(java.lang.Class, java.util.List)
	 */
	@Override
	public boolean bulkRemove(Class<?> clazz, List<Serializable> pks) {
		Dao<?> dao = factory.getGenericDao(clazz);
		return dao.bulkDelete(pks);
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#create(java.lang.Object)
	 */
	@Override
	public Serializable create(Object obj) {
		GenericQueryDao dao = factory.getGenericQueryDao();
		return dao.save(obj);
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#createBulk(java.util.List)
	 */
	@Override
	public List<Serializable> createBulk(List<?> items) {
		GenericQueryDao dao = factory.getGenericQueryDao();
		return dao.bulkSave(items);
	}

	/**
	 * Updates a serializable entity.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean update(Object obj) {
		GenericQueryDao dao = factory.getGenericQueryDao();
		return dao.update(obj);
	}

	/**
	 * Update bulk.
	 *
	 * @param items the items
	 * @return true if update completed without errors,
	 * returns false with rollback if an error occurs
	 */
	@Override
	public boolean updateBulk(List<?> items) {
		GenericQueryDao dao = factory.getGenericQueryDao();
		return dao.bulkUpdate(items);
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#toggleActive(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public boolean toggleActive(Class<? extends Entity> clazz, Serializable id) {
		Object bc = getByCriteria(clazz, new Criterion[] { Restrictions.idEq(id) });
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
	public boolean createOrUpdate(Object obj) {
		
		GenericQueryDao dao = factory.getGenericQueryDao();
		return dao.saveOrUpdate(obj);
	}

	/**
	 * Enables jta by disabling all references to transactions.
	 * Its expected that starting and controlling the transaction will be controlleed by the user
	 */
	public void enableJTA() {
		this.sxnManager.disableTransactions();
	}

	/**
	 * Disables jta by enabling all references to transactions.
	 * Its expected that starting and controlling the transaction will be controlleed by nworm
	 */
	public void disableJTA() {
		this.sxnManager.enableTransactions();
	}

	/**
	 * Enables the use of current session from session actory
	 */
	public void enableSessionByContext() {
		this.sxnManager.enableCurrentSession();
	}

	/**
	 * Disables the use of current session, all new session will call openSession
	 */
	public void disableSessionByContext() {
		this.sxnManager.disableCurrentSession();
	}


	/**
	 * Enable jta based session.
	 */
	@Deprecated
	public void enableJTABasedSession() {
		configureSessionManager(false, true);
	}

	/**
	 * Disable jta based session.
	 */
	@Deprecated
	public void disableJTABasedSession() {
		configureSessionManager(true, false);
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

	/**
	 * Modify a given criteria.
	 *
	 * @param te the criteria to be modified
	 * @param qm {@link QueryModifier} reference
	 */
	protected void modifyCriteria(Criteria te, QueryModifier qm) {
		List<QueryAlias> aliases = qm.getAliases();
		for (QueryAlias qa : aliases) {
			if ((qa.getJoinType() == null) && (qa.getWithClause() == null))
				te.createAlias(qa.getAssociationPath(), qa.getAlias());
			else if ((qa.getWithClause() == null) && (qa.getJoinType() != null))
				te.createAlias(qa.getAssociationPath(), qa.getAlias(), qa.getJoinType());
			else {
				te.createAlias(qa.getAssociationPath(), qa.getAlias(), qa.getJoinType(), qa.getWithClause());
			}
		}
		List<QueryFetchMode> fms = qm.getFetchModes();
		for(QueryFetchMode fm: fms){
			te.setFetchMode(fm.getAlias(), fm.getFetchMode());
		}

		if (qm.isPaginated()) {
			te.setFirstResult(qm.getPageIndex());
			te.setMaxResults(qm.getMaxResult());
		}

		List<Order> orderBys = qm.getOrderBys();
		for (Order order : orderBys) {
			te.addOrder(order);
		}

		List<Projection> projections = qm.getProjections();
		if (projections.size() > 0) {
			ProjectionList pl = Projections.projectionList();
			for (Projection p : projections)
				pl.add(p);
			te.setProjection(pl);
		}

		addSoftRestrictions(te, qm.getQueryClazz());
	}

	/**
	 * Checks if is initialized successfully.
	 *
	 * @return true, if is initialized successfully
	 */
	public boolean isInitializedSuccessfully() {
		return initializedSuccessfully;
	}

	/**
	 * Sets the initialized successfully.
	 *
	 * @param initializedSuccessfully the new initialized successfully
	 */
	public void setInitializedSuccessfully(boolean initializedSuccessfully) {
		this.initializedSuccessfully = initializedSuccessfully;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.service.NwormService#getSessionService()
	 */
	@Override
	public HibernateSessionService getSessionService() {
		if(isInitializedSuccessfully()){
			return this.sxnManager;
		}
		return null;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
}