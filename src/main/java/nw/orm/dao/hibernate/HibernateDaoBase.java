package nw.orm.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nw.orm.core.Entity;
import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryAlias;
import nw.orm.core.query.QueryFetchMode;
import nw.orm.core.query.QueryModifier;

/**
 * This is just a base class with lots of method stubs used by
 * the hibernate dao class
 * @author Ogwara O. Rowland
 *
 * @param <T> Entity class
 */
public abstract class HibernateDaoBase {
	
	
	/**
	 * Enabling JTA disables beginning and committing transaction
	 */
	protected boolean jtaEnabled;
	
	/**
	 * Controls whether a new session is open each time or a session is bound to the context
	 * true bounds a session to context 
	 */
	protected boolean useCurrentSession;
	
	protected SessionFactory sxnFactory;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public HibernateDaoBase(SessionFactory sxnFactory, boolean jtaEnabled, boolean useCurrentSession) {
		
		this.sxnFactory = sxnFactory;
		this.jtaEnabled = jtaEnabled;
		this.useCurrentSession = useCurrentSession;
		
	}
	
	
	/**
	 * Checks if is class mapped.
	 *
	 * @param clazz the clazz
	 */
	public void isClassMapped(Class<?> clazz) {
		try {
			sxnFactory.getClassMetadata(
					HibernateProxyHelper.getClassWithoutInitializingProxy(clazz.newInstance()));
		} catch (Exception e) {
			throw new NwormQueryException("Nw.orm Exception", e);
		}
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
	
	/**
	 * Modify a given criteria.
	 *
	 * @param te the criteria to be modified
	 * @param qm {@link QueryModifier} reference
	 */
	protected void modifyCriteria(Criteria te, QueryModifier qm) {
		
		if(qm == null) {
			return;
		}
		
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
	
	protected Session getSession() {
		if(useCurrentSession){
			return getCurrentSession();
		}
		return getRawSession();
	}
	
	protected Session getCurrentSession() {
		Session sxn = sxnFactory.getCurrentSession();
		sxn.setFlushMode(FlushMode.AUTO);
		beginTransaction(sxn);
		return sxn;
	}
	
	protected Session getRawSession() {
		Session sxn = sxnFactory.openSession();
		sxn.setFlushMode(FlushMode.AUTO);
		beginTransaction(sxn);
		return sxn;
	}
	
	protected StatelessSession getStatelessSession() {
		StatelessSession ss = sxnFactory.openStatelessSession();
		if(!jtaEnabled){
			ss.beginTransaction();
		}
		return ss;
	}
	
	protected void commit(Session sxn) throws HibernateException{
		logger.trace("Commit in progress ");
		if(!jtaEnabled){
			sxn.getTransaction().commit();
		}
	}
	
	protected void rollback(Session sxn) throws HibernateException{
		logger.trace("Rollback in progress ");
		if(!jtaEnabled){
			sxn.getTransaction().rollback();
		}
	}
	
	protected void beginTransaction(Session sxn){
		if(!jtaEnabled){
			sxn.beginTransaction();
		}
	}
	
	protected void closeSession(Session sxn) {
		if ((sxn != null) && (!this.useCurrentSession)){
			sxn.close();
		}
	}

}
