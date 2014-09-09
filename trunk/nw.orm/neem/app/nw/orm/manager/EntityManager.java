package nw.orm.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nw.commons.NeemClazz;
import nw.orm.base.Entity;
import nw.orm.base.contract.IEntityManager;
import nw.orm.examples.model.Person;
import nw.orm.query.QueryAlias;
import nw.orm.query.QueryModifier;
import nw.orm.query.QueryParameter;
import nw.orm.query.SQLModifier;
import nw.orm.session.core.HibernateConfiguration;
import nw.orm.session.core.SessionManager;

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

/**
 * Base class for entity management
 * @author kulgan
 *
 */
public abstract class EntityManager extends NeemClazz implements IEntityManager {
	
	protected HibernateConfiguration conf;
	protected SessionManager sxnManager;
	
	private boolean initializedSuccessfully;
	
	private static ConcurrentHashMap<String, EntityManager> activeManagers = new ConcurrentHashMap<String, EntityManager>();
	protected boolean useCurrentSession = true;

	protected static EntityManager getManager(String configFile) {
		return (EntityManager) activeManagers.get(configFile);
	}

	protected static void putManager(String file, EntityManager manager) {
		activeManagers.put(file, manager);
	}
	
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

	public boolean isClassMapped(Class<?> clazz) {
		try {
			return sxnManager.getFactory().getClassMetadata(
					HibernateProxyHelper.getClassWithoutInitializingProxy(clazz
							.newInstance())) != null;
		} catch (InstantiationException e) {
			this.logger.error("Exception: ", e);
		} catch (IllegalAccessException e) {
			this.logger.error("Exception: ", e);
		} 
		return false;
	}
	
	public <T> T getById(Class<T> clazz, Serializable id) {
		return getById(clazz, id, false);
	}

	@SuppressWarnings("unchecked")
	public <T> T getById(Class<T> clazz, Serializable id, boolean lock) {
		T out = null;
		sxnManager.getManagedSession();
		Session session = sxnManager.getManagedSession();
		try {
			if (!lock){
				out = (T) session.load(clazz, id, LockOptions.READ);
			}else{
				out = (T) session.load(clazz, id, LockOptions.UPGRADE);
			}
			sxnManager.commit(session);
		} catch (HibernateException e) {
			sxnManager.rollback(session);
			this.logger.error("Exception: ", e);
		}
		sxnManager.closeSession(session);
		return out;
	}

	public <T> List<T> getAll(Class<T> clazz) {
		return getListByCriteria(clazz);
	}
	
	/**
	 * Filters out deleted entries from queries
	 */
	public void addSoftRestrictions(Criteria te, Class<?> clazz) {
		if (Entity.class.isAssignableFrom(clazz)){
			te.add(Restrictions.eq("deleted", Boolean.valueOf(false)));
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getByCriteria(Class<T> clz, Criterion ... criteria) {
		T out = null;
		boolean isMapped = isClassMapped(clz);
		Session session = sxnManager.getManagedSession();
		try {
			Criteria te = session.createCriteria(clz);
			for (Criterion c : criteria) {
				te.add(c);
			}
			addSoftRestrictions(te, clz);
			if (isMapped){
				out = (T) te.uniqueResult();
			}else{
				out = (T) te.setResultTransformer(Transformers.aliasToBean(clz)).uniqueResult();
			}
			sxnManager.commit(session);
		} catch (HibernateException e) {
			sxnManager.rollback(session);
			logger.error("Exception: ", e);
		}
		sxnManager.closeSession(session);
		return out;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getListByCriteria(Class<T> clz, Criterion ... criteria) {
		List<T> out = new ArrayList<T>();
		boolean isMapped = isClassMapped(clz);
		Session session = sxnManager.getManagedSession();
		try {
			Criteria te = session.createCriteria(clz);
			for (Criterion c : criteria) {
				te.add(c);
			}
			addSoftRestrictions(te, clz);
			if (isMapped){
				out = (List<T>) te.list();
			}else{
				out = (List<T>) te.setResultTransformer(Transformers.aliasToBean(clz)).list();
			}
			sxnManager.commit(session);
		} catch (HibernateException e) {
			this.logger.error("Exception: ", e);
			sxnManager.rollback(session);
		}
		sxnManager.closeSession(session);
		return out;
	}

	public <T> T getByHQL(String hql, Map<String, Object> parameters, Class<T> resultClass) {
		T out = getByHQL(resultClass, hql, QueryParameter.fromMap(parameters));
		return out;
	}

	@SuppressWarnings("unchecked")
	public <T> T getByHQL(Class<T> resultClass, String hql, QueryParameter ... parameters) {
		T out = null;
		boolean isMapped = isClassMapped(resultClass);
		Session session = sxnManager.getManagedSession();
		try {
			
			if (Entity.class.isAssignableFrom(resultClass)) {
				hql = modifyHQL(hql, resultClass);
			}
			
			Query query = session.createQuery(hql);
			for (QueryParameter rp : parameters) {
				query.setParameter(rp.getName(), rp.getValue());
			}
			
			if (Entity.class.isAssignableFrom(resultClass)) {
				query.setParameter("deleted", Boolean.valueOf(false));
			}
			if (isMapped){
				out = (T) query.uniqueResult();
			}else {
				out = (T) query.setResultTransformer(Transformers.aliasToBean(resultClass)).uniqueResult();
			}
			sxnManager.commit(session);
		} catch (HibernateException e) {
			this.logger.error("Exception: ", e);
			sxnManager.rollback(session);
		}
		sxnManager.closeSession(session);
		return out;
	}
	
	public <T> List<T> getListByHQL(String hql, Map<String, Object> parameters, Class<T> resultClass) {
		List<T> out = getListByHQL(resultClass, hql, QueryParameter.fromMap(parameters));
		return out;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getListByHQL(Class<T> resultClass, String hql, QueryParameter ... parameters) {
		List<T> out = new ArrayList<T>();
		boolean isMapped = isClassMapped(resultClass);
		Session session = sxnManager.getManagedSession();
		try {
			if (Entity.class.isAssignableFrom(resultClass)) {
				hql = modifyHQL(hql, resultClass);
			}
			Query query = session.createQuery(hql);
			for (QueryParameter rp : parameters) {
				query.setParameter(rp.getName(), rp.getValue());
			}
			if (Entity.class.isAssignableFrom(resultClass)) {
				query.setBoolean("deleted", false);
			}
			if (isMapped){
				out = query.list();
			}else{
				out = query.setResultTransformer(Transformers.aliasToBean(resultClass)).list();
			}
						
			sxnManager.commit(session);
		} catch (HibernateException e) {
			this.logger.error("Exception: ", e);
			sxnManager.rollback(session);
		}
		sxnManager.closeSession(session);
		return out;
	}
	
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
			if(returnClazz != null){
				te.addEntity(returnClazz);
			}
			if(Entity.class.isAssignableFrom(returnClazz)){
				te.setParameter("deleted", false);
			}
			if(sqlMod.isPaginated()){
				te.setFirstResult(sqlMod.getPageIndex());
				te.setMaxResults(sqlMod.getMaxResult());
			}
		}
		
		out = te.list();
		sxnManager.closeSession(session);
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria){
		T out = null;
		Session session = sxnManager.getManagedSession();
		try {
			Criteria te = session.createCriteria(qm.getQueryClazz());
			for (Criterion c : criteria) {
				te.add(c);
			}
			modifyCriteria(te, qm);
			if(!qm.isTransformResult()){
				out = (T) te.uniqueResult();
			}else{
				out = (T) te.setResultTransformer(Transformers.aliasToBean(returnClazz)).uniqueResult();
			}
			sxnManager.commit(session);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			sxnManager.rollback(session);
		}
		sxnManager.closeSession(session);
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getListByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria){
		List<T> out = new ArrayList<T>();
		Session session = sxnManager.getManagedSession();
		try {
			Criteria te = session.createCriteria(qm.getQueryClazz());
			for (Criterion c : criteria) {
				te.add(c);
			}
			modifyCriteria(te, qm);
			if(!qm.isTransformResult()){
				out = (List<T>) te.list();
			}else{
				out = (List<T>) te.setResultTransformer(Transformers.aliasToBean(returnClazz)).list();
			}
			sxnManager.commit(session);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			sxnManager.rollback(session);
		}
		sxnManager.closeSession(session);
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getByExample(Class<T> clazz, Example example){
		T out = null;
		Session sxn = sxnManager.getManagedSession();
		Criteria te = sxn.createCriteria(clazz).add(example);
		try {
			out = (T) te.uniqueResult();
			sxnManager.commit(sxn);
		} catch (HibernateException e) {
			logger.error("Exception ", e);
		}
		sxnManager.closeSession(sxn);
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getListByExample(QueryModifier qm, Example example){
		List<T> items = new ArrayList<T>();
		Session sxn = sxnManager.getManagedSession();
		Criteria te = sxn.createCriteria(qm.getQueryClazz()).add(example);
		try {
			modifyCriteria(te, qm);
			items = (List<T>) te.list();
			sxnManager.commit(sxn);
		} catch (HibernateException e) {
			logger.error("Exception ", e);
		}
		sxnManager.closeSession(sxn);
		return items;
	}
	
	public int executeSQLUpdate(String sql, QueryParameter ... params){
		Session session = sxnManager.getManagedSession();
		SQLQuery query = session.createSQLQuery(sql);
		if (params != null) {
			for (QueryParameter param : params) {
				query.setParameter(param.getName(), param.getValue());
			}
		}
		int o = query.executeUpdate();
		sxnManager.commit(session);
		sxnManager.closeSession(session);
		return o;
	}
	
	public int executeHQLUpdate(String hql, QueryParameter ... params){
		Session session = sxnManager.getManagedSession();
		org.hibernate.Query query = session.createQuery(hql);
		if (params != null) {
			for (QueryParameter param : params) {
				query.setParameter(param.getName(), param.getValue());
			}
		}
		int o = query.executeUpdate();
		sxnManager.commit(session);
		sxnManager.closeSession(session);
		return o;
	}

	public boolean softDelete(Class<? extends Entity<?>> clazz, Serializable id) {
		if (Entity.class.isAssignableFrom(clazz)) {
			logger.debug("Unsupported class specified.");
			return false;
		}
		Object bc = getByCriteria(clazz, Restrictions.idEq(id));
		if ((bc instanceof Entity)) {
			Entity<?> e = (Entity<?>) bc;
			e.setDeleted(true);
		}
		return update(bc);
	}

	public boolean bulkSoftDelete(Class<? extends Entity<?>> clazz, List<Serializable> ids) {
		StatelessSession session = sxnManager.getStatelessSession();
		if (Entity.class.isAssignableFrom(clazz)) {
			logger.debug("Unsupported class specified.");
			return false;
		}
		
		try {
			for (Serializable s : ids) {
				Object entity = session.get(clazz, s);
				Entity<?> e = (Entity<?>) entity;
				e.setDeleted(true);
				session.update(entity);
			}
			if(sxnManager.useTransactions()){
				session.getTransaction().commit();
			}
			session.close();
			return true;
		} catch (HibernateException e) {
			if(sxnManager.useTransactions()){
				session.getTransaction().rollback();
			}
			this.logger.error("Exception", e);
		}
		return false;
	}

	public boolean remove(Object obj) {
		boolean outcome = false;
		Session session = sxnManager.getManagedSession();
		try {
			session.delete(obj);
			sxnManager.commit(session);
			outcome = true;
		} catch (HibernateException e) {
			sxnManager.rollback(session);
			this.logger.error("Exception", e);
		}
		sxnManager.closeSession(session);
		return outcome;
	}

	public boolean remove(Class<?> clazz, Serializable pk) {
		boolean outcome = false;
		Session session = sxnManager.getManagedSession();
		try {
			session.delete(session.get(clazz, pk));
			sxnManager.commit(session);
			outcome = true;
		} catch (HibernateException e) {
			this.logger.error("Exception ", e);
			sxnManager.rollback(session);
		}
		sxnManager.closeSession(session);
		return outcome;
	}

	public boolean bulkRemove(Class<?> clazz, List<Serializable> pks) {
		StatelessSession session = sxnManager.getStatelessSession();
		try {
			for (Serializable pk : pks) {
				session.delete(session.get(clazz, pk));
			}
			if(sxnManager.useTransactions()){
				session.getTransaction().commit();
			}
			session.close();
			return true;
		} catch (HibernateException e) {
			this.logger.error("Exception ", e);
			if(sxnManager.useTransactions()){
				session.getTransaction().rollback();
			}
		}
		session.close();
		return false;
	}

	public Serializable create(Object obj) {
		Serializable pk = null;
		Session session = sxnManager.getManagedSession();
		try {
			pk = session.save(obj);
			sxnManager.commit(session);
		} catch (HibernateException e) {
			sxnManager.rollback(session);
			this.logger.error("Exception ", e);
		}
		sxnManager.closeSession(session);
		return pk;
	}

	public List<Serializable> createBulk(List<?> items) {
		List<Serializable> ids = new ArrayList<Serializable>();
		StatelessSession session = sxnManager.getStatelessSession();
		try {
			for (Object item: items) {
				ids.add(session.insert(item));
			}
			if(sxnManager.useTransactions()){
				session.getTransaction().commit();
			}
		} catch (HibernateException e) {
			this.logger.error("Exception ", e);
			if(sxnManager.useTransactions()){
				session.getTransaction().rollback();
			}
		}
		session.close();
		return ids;
	}

	/**
	 * Updates a serializable entity
	 */
	public boolean update(Object obj) {
		boolean outcome = false;
		Session session = sxnManager.getManagedSession();
		try {
			session.update(obj);
			sxnManager.commit(session);
			outcome = true;
		} catch (HibernateException e) {
			sxnManager.rollback(session);
			this.logger.error("Exception ", e);
		}
		sxnManager.closeSession(session);
		return outcome;
	}

	/**
	 * @param objs list of objects to update
	 * @return true if update completed without errors, 
	 * returns false with rollback if an error occurs
	 */
	public boolean updateBulk(List<?> items) {
		boolean outcome = false;
		StatelessSession session = sxnManager.getStatelessSession();
		try {
			for (Object item: items) {
				session.update(item);
			}
			if(sxnManager.useTransactions()){
				session.getTransaction().commit();
			}
			outcome = true;
		} catch (HibernateException e) {
			this.logger.error("Exception ", e);
			if(sxnManager.useTransactions()){
				session.getTransaction().rollback();
			}
		}
		session.close();
		return outcome;
	}

	public boolean toggleActive(Class<? extends Entity<?>> clazz, Serializable id) {
		Object bc = getByCriteria(clazz, new Criterion[] { Restrictions.idEq(id) });
		if ((bc instanceof Entity)) {
			Entity<?> e = (Entity<?>) bc;
			e.setActive(!e.isActive());
		}
		return update(bc);
	}

	public boolean createOrUpdate(Object obj) {
		boolean outcome = false;
		Session session = sxnManager.getManagedSession();
		try {
			session.saveOrUpdate(obj);
			sxnManager.commit(session);
			outcome = true;
		} catch (Exception e) {
			sxnManager.rollback(session);
			this.logger.error("Exception ", e);
		}
		return outcome;
	}

	public void enableJTABasedSession() {
		this.useCurrentSession = true;
	}

	public void disableJTABasedSession() {
		this.useCurrentSession = false;
	}

	protected String modifyHQL(String hql, Class<?> clazz) {
		if (Entity.class.isAssignableFrom(clazz)) {
			if (hql.toLowerCase().contains(" where ")) {
				return hql + " and deleted = :deleted";
			}
			return hql + " where deleted = :deleted";
		}
		return hql;
	}

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

	public static void main(String[] args) {
		System.out.println(Entity.class.isAssignableFrom(Person.class));
	}

	public boolean isInitializedSuccessfully() {
		return initializedSuccessfully;
	}

	public void setInitializedSuccessfully(boolean initializedSuccessfully) {
		this.initializedSuccessfully = initializedSuccessfully;
	}
}