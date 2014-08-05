package nw.orm.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nw.commons.NeemClazz;
import nw.orm.base.Entity;
import nw.orm.base.REntityManagerException;
import nw.orm.base.contract.IEntityManager;
import nw.orm.base.contract.IHibernateUtil;
import nw.orm.examples.model.Person;
import nw.orm.query.QueryAlias;
import nw.orm.query.QueryModifier;
import nw.orm.query.QueryParameter;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
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
	
	private static ConcurrentHashMap<String, EntityManager> activeManagers = new ConcurrentHashMap<String, EntityManager>();
	protected IHibernateUtil hUtil;
	protected boolean useCurrentSession = true;

	protected static EntityManager getManager(String configFile) {
		return (EntityManager) activeManagers.get(configFile);
	}

	protected static void putManager(String file, EntityManager manager) {
		activeManagers.put(file, manager);
	}

	protected boolean isClassMapped(Class<?> clazz) {
		try {
			return getCurrentSessionFactory().getClassMetadata(
					HibernateProxyHelper.getClassWithoutInitializingProxy(clazz
							.newInstance())) != null;
		} catch (InstantiationException e) {
			this.logger.error("Exception: ", e);
		} catch (IllegalAccessException e) {
			this.logger.error("Exception: ", e);
		} catch (REntityManagerException e) {
			this.logger.error("Exception: ", e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> T getById(Class<? extends T> clazz, Serializable id, boolean lock) {
		T out = null;
		Transaction tx = null;
		Session session = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			if (lock){
				out = (T) session.load(clazz, id);
			}else{
				out = (T) session.load(clazz, id, LockOptions.UPGRADE);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null){
				tx.rollback();
			}
			this.logger.error("Exception: ", e);
		}
		closeSession(session);
		return out;
	}

	public <T> T getById(Class<? extends T> clazz, Serializable id) {
		return getById(clazz, id, false);
	}

	public <T> List<T> getAll(Class<? extends T> clazz) {
		return getListByCriteria(clazz, new Criterion[0]);
	}

	@SuppressWarnings("unchecked")
	public <T> T getByCriteria(Class<? extends T> clz, Criterion ... criteria) {
		T out = null;
		boolean isMapped = isClassMapped(clz);
		Transaction tx = null;
		Session session = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
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
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			this.logger.error("Exception: ", e);
		}
		closeSession(session);
		return out;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getListByCriteria(Class<? extends T> clz, Criterion ... criteria) {
		List<T> out = new ArrayList<T>();
		Transaction tx = null;
		Session session = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			Criteria te = session.createCriteria(clz);
			for (Criterion c : criteria) {
				te.add(c);
			}
			addSoftRestrictions(te, clz);
			out = te.list();
			tx.commit();
		} catch (Exception e) {
			this.logger.error("Exception: ", e);
			if (tx != null)
				tx.rollback();
		}
		closeSession(session);
		return out;
	}

	public <T> T getByHQL(String hql, Map<String, Object> parameters,Class<? extends T> resultClass) {
		T out = getByHQL(resultClass, hql, QueryParameter.fromMap(parameters));
		return out;
	}

	@SuppressWarnings("unchecked")
	public <T> T getByHQL(Class<? extends T> resultClass, String hql, QueryParameter ... parameters) {
		T out = null;
		boolean isMapped = isClassMapped(resultClass);
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			hql = modifyHQL(hql, resultClass);
			Query query = session.createQuery(hql);
			for (QueryParameter rp : parameters) {
				query.setParameter(rp.getName(), rp.getValue());
			}
			if (Entity.class.isAssignableFrom(resultClass)) {
				query.setParameter("deleted", Boolean.valueOf(false));
			}
			if (isMapped)
				out = (T) query.uniqueResult();
			else {
				out = (T) query.setResultTransformer(
						Transformers.aliasToBean(resultClass)).uniqueResult();
			}
			tx.commit();
		} catch (Exception e) {
			this.logger.error("Exception: ", e);
			if (tx != null)
				tx.rollback();
		}
		closeSession(session);
		return out;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getListByHQL(Class<? extends T> resultClass, String hql, QueryParameter ... parameters) {
		List<T> out = new ArrayList<T>();
		boolean isMapped = isClassMapped(resultClass);
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			hql = modifyHQL(hql, resultClass);
			Query query = session.createQuery(hql);
			for (QueryParameter rp : parameters) {
				query.setParameter(rp.getName(), rp.getValue());
			}
			if (Entity.class.isAssignableFrom(resultClass)) {
				query.setBoolean("deleted", false);
			}
			if (isMapped)
				out = query.list();
			else
				out = query.setResultTransformer(
						Transformers.aliasToBean(resultClass)).list();
			tx.commit();
		} catch (Exception e) {
			this.logger.error("Exception: ", e);
			if (tx != null)
				tx.rollback();
		}
		closeSession(session);
		return out;
	}

	public <T> List<T> getListByHQL(String hql, Map<String, Object> parameters, Class<? extends T> resultClass) {
		List<T> out = getListByHQL(resultClass, hql,
				QueryParameter.fromMap(parameters));
		return out;
	}

	public boolean softDelete(Class<?> clazz, Serializable id) {
		Object bc = getByCriteria(clazz, new Criterion[] { Restrictions.idEq(id) });
		if ((bc instanceof Entity)) {
			Entity<?> e = (Entity<?>) bc;
			e.setDeleted(true);
		}
		return update(bc);
	}

	public boolean bulkSoftDelete(Class<?> clazz, List<Serializable> ids) {
		StatelessSession session = null;
		Transaction tx = null;
		try {
			session = getStatelessSession();
			tx = session.beginTransaction();
			for (Serializable s : ids) {
				Object entity = session.get(clazz, s);
				if ((entity instanceof Entity)) {
					Entity<?> e = (Entity<?>) entity;
					e.setDeleted(true);
				}
				session.update(entity);
			}
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			if (tx != null){
				tx.rollback();
			}
			this.logger.error("Exception", e);
		}
		return false;
	}

	public boolean remove(Object obj) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
			closeSession(session);
			return true;
		} catch (Exception e) {
			if (tx != null){
				tx.rollback();
				closeSession(session);
			}
			this.logger.error("Exception", e);
		}
		return false;
	}

	public boolean remove(Class<?> clazz, Serializable pk) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.delete(session.get(clazz, pk));
			tx.commit();
			closeSession(session);
			return true;
		} catch (Exception e) {
			this.logger.error("Exception ", e);
			if (tx != null){
				tx.rollback();
			}
		}
		return false;
	}

	public boolean bulkRemove(Class<?> clazz, List<Serializable> pks) {
		StatelessSession statelessSession = null;
		Transaction tx = null;
		try {
			statelessSession = getStatelessSession();
			tx = statelessSession.beginTransaction();

			for (Serializable pk : pks) {
				statelessSession.delete(statelessSession.get(clazz, pk));
			}
			tx.commit();
			statelessSession.close();
			return true;
		} catch (Exception e) {
			if (tx != null){
				tx.rollback();
			}
			this.logger.error("Exception ", e);
		}
		return false;
	}

	public Serializable create(Object obj) {
		Serializable pk = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			pk = session.save(obj);
			tx.commit();
		} catch (Exception e) {
			if (tx != null){
				tx.rollback();
			}
			this.logger.error("Exception ", e);
		}
		return pk;
	}

	public List<Serializable> createBulk(List<Object> items) {
		List<Serializable> ids = null;
		StatelessSession statelessSession = null;
		Transaction tx = null;
		try {
			ids = new ArrayList<Serializable>();
			statelessSession = getStatelessSession();
			tx = statelessSession.beginTransaction();
			for (Object item: items) {
				ids.add(statelessSession.insert(item));
			}
			tx.commit();
			statelessSession.close();
		} catch (Exception e) {
			if (tx != null){
				tx.rollback();
			}
			this.logger.error("Exception ", e);
		}
		return ids;
	}

	/**
	 * Updates a serializable entity
	 */
	public boolean update(Object obj) {
		Session session = null;
		Transaction txn = null;
		try {
			session = getSession();
			txn = session.beginTransaction();
			session.update(obj);
			txn.commit();
			closeSession(session);
			return true;
		} catch (Exception e) {
			if (txn != null){
				txn.rollback();
				closeSession(session);
			}
			this.logger.error("Exception ", e);
		}
		return false;
	}

	/**
	 * @param objs list of objects to update
	 * @return true if update completed without errors, 
	 * returns false with rollback if an error occurs
	 */
	public boolean updateBulk(List<Object> objs) {
		StatelessSession session = null;
		Transaction txn = null;
		try {
			session = getStatelessSession();
			txn = session.beginTransaction();
			for (Object obj: objs) {
				session.update(obj);
			}
			txn.commit();
			return true;
		} catch (Exception e) {
			this.logger.error("Exception ", e);
			if (txn != null){
				txn.rollback();
			}
		}
		return false;
	}

	public boolean toggleActive(Class<?> clazz, Serializable id) {
		Object bc = getByCriteria(clazz, new Criterion[] { Restrictions.idEq(id) });
		if ((bc instanceof Entity)) {
			Entity<?> e = (Entity<?>) bc;
			e.setActive(!e.isActive());
		}
		return update(bc);
	}

	public boolean createOrUpdate(Object obj) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(obj);
			tx.commit();
			closeSession(session);
			return true;
		} catch (Exception e) {
			if (tx != null){
				tx.rollback();
				closeSession(session);
			}
			this.logger.error("Exception ", e);
		}
		return false;
	}

	public Session getActiveSession() throws REntityManagerException {
		SessionFactory sf = getCurrentSessionFactory();
		return sf.getCurrentSession();
	}

	public Session openSession() throws REntityManagerException {
		SessionFactory sf = getCurrentSessionFactory();
		return sf.openSession();
	}

	public StatelessSession getStatelessSession()
			throws REntityManagerException {
		SessionFactory sf = getCurrentSessionFactory();
		return sf.openStatelessSession();
	}

	@Deprecated
	public Session getCurrentSession() {
		if (getSessionFactory() != null)
			return getSessionFactory().getCurrentSession();
		return null;
	}

	@Deprecated
	public SessionFactory getSessionFactory() {
		return this.hUtil.getSessionFactory();
	}

	public SessionFactory getCurrentSessionFactory()
			throws REntityManagerException {
		if (this.hUtil == null)
			throw new REntityManagerException(
					"Session Factory could not be located");
		return this.hUtil.getSessionFactory();
	}

	protected Session getSession() throws REntityManagerException {
		if (this.useCurrentSession) {
			return getActiveSession();
		}
		return openSession();
	}

	protected void closeSession(Session sxn) {
		if ((sxn != null) && (!this.useCurrentSession))
			sxn.close();
	}

	public void enableJTABasedSession() {
		this.useCurrentSession = true;
	}

	public void disableJTABasedSession() {
		this.useCurrentSession = false;
	}

	/**
	 * Adds 
	 */
	public void addSoftRestrictions(Criteria te, Class<?> clazz) {
		if (Entity.class.isAssignableFrom(clazz)){
			te.add(Restrictions.eq("deleted", Boolean.valueOf(false)));
		}
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
}