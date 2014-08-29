package nw.orm.manager;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.OperationNotSupportedException;

import nw.orm.base.Entity;
import nw.orm.base.RAudit;
import nw.orm.base.contract.IHibernateUtil;
import nw.orm.examples.model.Person;
import nw.orm.query.QueryModifier;
import nw.orm.query.QueryParameter;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;

/**
 * Neemworks Limited Database.
 * Transaction processing using HQL, and criteria.
 *
 * @author Ogwara O. Rowland (r.ogwara@nimworks.com)
 * @version 0.6
 * @since 6th Nov, 2013
 *
 *
 */
public class REntityManager extends EntityManager {
	

	/**
	 *@param configFile
	 *		Hibernate configuration file name to be used for this connection. The file name
	 *		is case sensitive only for case sensitive file system
	 *@param props
	 *		Extra configuration parameters. Useful in cases where modification of some properties from an exisiting config is needed
	 *		It must contain a property named config.name
	 * @return a single database service instance
	 * @throws OperationNotSupportedException
	 */

	public static REntityManager getInstance(String configFile, Properties props) throws OperationNotSupportedException {
		return getInstance(configFile, props, null);
	}
	
	public static REntityManager getInstance(String configFile, Properties props, IHibernateUtil hUtil) throws OperationNotSupportedException {
		REntityManager service = null;
		if(props != null){
			String cname = props.getProperty("config.name");
			if(cname == null || (cname != null && cname.isEmpty()))
				throw new OperationNotSupportedException("A Property named config.name must be specified in this property object");
			service = (REntityManager) getManager(configFile + "_" + cname);
		}else{
			service = (REntityManager) getManager(configFile);
		}
		if (service == null) {
			synchronized (REntityManager.class) {
				service = new REntityManager(configFile, props, hUtil);
			}
		}
		return service;
	}

	/**
	 *@param configFile
	 *		Hibernate configuration file name to be used for this connection. The file name
	 *		is case sensitive only for case sensitive file system
	 * @return a single database service instance
	 */

	public static REntityManager getInstance(String configFile) {
		REntityManager service = null;
		try {
			service = getInstance(configFile, null, null);
		} catch (OperationNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return service;
	}
	
	public static REntityManager getInstance(IHibernateUtil hUtil, String configFile) {
		REntityManager service = null;
		try {
			service = getInstance(configFile, null, hUtil);
		} catch (OperationNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return service;
	}

	/**
	 * Creates and Entity Manager using default configuration file name hibernate.cfg.xml
	 * @return a single database service instance
	 */

	public static REntityManager getInstance() {
		String configFile = "hibernate.cfg.xml";
		return getInstance(configFile);
	}
	
	public static REntityManager getInstance(IHibernateUtil hUtil) {
		String configFile = "hibernate.cfg.xml";
		return getInstance(hUtil, configFile);
	}

	private REntityManager(String configFile, Properties props) {
		this(configFile, props, null);
	}
	
	private REntityManager(String configFile, Properties props, IHibernateUtil hUtil) {
		
//		if(!PlatformUtil.isFileSystemCaseSensitive()){ // windows systems
//			configFile = configFile.toLowerCase();
//		}
		
		if(props == null){
			putManager(configFile, this);
		}else{
			putManager(configFile + "_" + props.getProperty("config.name"), this);
		}
		if(hUtil == null){
			try {
				Class<?> clz = Class.forName("nw.orm.manager.HibernateUtil");
				Constructor<?> cstr = clz.getConstructor();
				this.hUtil = (IHibernateUtil) cstr.newInstance();
				this.hUtil.init(props, configFile);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
	}
	
    public <T>List<T> getByExample(Class<? extends T> clazz, Example example) {
		return getListByCriteria(clazz, example);
    }
	

	/**
	 * Retrieves a List of database objects based on specified query
	 * @param <T>
	 *
	 * @param hql
	 *            <i> target HQL </i>
	 * @param parameters
	 *            <i> parameter name value pair </i>
	 * @param pageIndex
	 *            <i> Start row number </i>
	 * @param pageSize
	 *            <i> Size of items to retrieve </i>
	 * @return <i> A list of objects based on the result of the query
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public <T> List<T> getListByHQL(String hql, Map<String, Object> parameters, int pageIndex, int pageSize, Class<? extends T> resultClass) {
		List<T> out;
		boolean isMapped = isClassMapped(resultClass);
		Session session = hUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(hql);
		buildParameter(resultClass, query, parameters);
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		if(isMapped)
			out = query.list();
		else
			out = query.setResultTransformer(Transformers.aliasToBean(resultClass)).list();
		tx.commit();
		return out;
	}

	/**
	 * Retrieves a List of database objects based on specified query
	 * @param <T>
	 *
	 * @param hql
	 *            <i> target HQL </i>
	 * @param parameters
	 *            <i> parameter name value pair </i>
	 * @param pageIndex
	 *            <i> Start row number </i>
	 * @param pageSize
	 *            <i> Size of items to retrieve </i>
	 * @return <i> A list of objects based on the result of the query
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListByHQL(Class<? extends T> resultClass, String hql, int pageIndex, int pageSize, QueryParameter ... parameters) {
		List<T> out = new ArrayList<T>();
		boolean isMapped = isClassMapped(resultClass);
		Session session = hUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			buildParameter(resultClass, query, parameters);
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
			if(isMapped)
				out = query.list();
			else
				out = query.setResultTransformer(Transformers.aliasToBean(resultClass)).list();
			tx.commit();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			tx.rollback();		}
		return out;
	}

	/**
	 * Adds parameters to supplied query
	 *
	 * @param query
	 * @param parameters
	 *            <i> parameter name value pair </i>
	 */
	private void buildParameter(Class<?> clazz, Query query, Map<String, Object> parameters) {
		if (parameters != null) {
			for (Object param : parameters.keySet()) {
				query.setParameter(param.toString(), parameters.get(param));
			}
		}
		if(clazz != Object.class && clazz.isAssignableFrom(Entity.class)){
			query.setParameter("deleted", false);
			query.setParameter("active", true);
		}
	}

	private void buildParameter(Class<?> clazz, Query query, QueryParameter ...parameters){
		for(QueryParameter rp: parameters){
			query.setParameter(rp.getName(), rp.getValue());
		}
		if(clazz != Object.class && clazz.isAssignableFrom(Entity.class)){
			query.setParameter("deleted", false);
			query.setParameter("active", true);
		}
	}
	
	/**
	 * Searches the database based on set criteria
	 * @param returnClazz the return object type
	 * @param qm extra specifications as regards the result
	 * @param criteria search criteria
	 * @return
	 */
	public <T> T getByCriteria(Class<? extends T> queryClazz, QueryModifier qm, Criterion... criteria) {
		return getByCriteria(queryClazz, qm, false, criteria);
	}
	
	/**
	 * Searches the database based on set criteria
	 * @param returnClazz output object type
	 * @param qm
	 * @param transformResult whether the result should be transformed or not
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getByCriteria(Class<? extends T> returnClazz, QueryModifier qm, boolean transformResult, Criterion... criteria) {
		T out = null;
		Class<?> queryClazz;
		if(qm.getQueryClazz() == null)
			queryClazz = returnClazz;
		else
			queryClazz = qm.getQueryClazz();
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			Criteria te = session.createCriteria(queryClazz);
			for (Criterion c : criteria) {
				te.add(c);
			}
			modifyCriteria(te, qm);
			if(!transformResult)
				out = (T) te.uniqueResult();
			else
				out = (T) te.setResultTransformer(Transformers.aliasToBean(returnClazz)).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			if(tx != null)
				tx.rollback();
		}
		closeSession(session);
		return out;
	}
	
	/**
	 * @param <T>
	 * @param clz
	 *            <i> target entity to search </i>
	 * @param pageIndex
	 *            <i> Start row number </i>
	 * @param pageSize
	 *            <i> Size of items to retrieve </i>
	 * @param criteria
	 *            <i> infinite list of criteria used t retrieve object </i>
	 * @return a list of objects
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public <T> List<T> getListByCriteria(Class<? extends T> clz, int pageIndex, int pageSize, Order order, Criterion... criteria) {
		List<T> out;
		SessionFactory sf = hUtil.getSessionFactory();
		Session session = sf.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria te = session.createCriteria(clz);
		for (Criterion c : criteria) {
			te.add(c);
		}
		if(order != null)
			te.addOrder(order);
		te.setFirstResult(pageIndex);
		te.setMaxResults(pageSize);
		out = te.list();
		tx.commit();
		return out;
	}

	
	/**
	 * @param <T>
	 * @param clz
	 *            <i> target entity to search </i>
	 * @param criteria
	 *            <i> infinite list of criteria used t retrieve object </i>
	 * @param order
	 *            <i> result order by </i>
	 * @return a list of objects
	 */
	public <T> List<T> getListByCriteria(Class<? extends T> clz, Order order, Criterion... criteria) {
		QueryModifier qm = new QueryModifier(clz);
		qm.addOrderBy(order);
		return getListByCriteria(clz, qm, false, criteria);
	}


	/**
	 * @param <T>
	 * @param clz
	 *            <i> target entity to search </i>
	 * @param criteria
	 *            <i> infinite list of criteria used t retrieve object </i>
	 * @param order
	 *            <i> result order by </i>
	 * @return a list of objects
	 */
	@SuppressWarnings("unchecked")
	public <T>List<T> getListByCriteria(Class<? extends T> clz, QueryModifier qm, boolean transformResult, Criterion... criteria) {
		List<T> out = new ArrayList<T>();
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			Criteria te = session.createCriteria(qm.getQueryClazz());
			for (Criterion c : criteria) {
				te.add(c);
			}
			modifyCriteria(te, qm);
			if(!transformResult)
				out = te.list();
			else
				out = te.setResultTransformer(Transformers.aliasToBean(clz)).list();
			tx.commit();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			if(tx != null)
				tx.rollback();
		}
		closeSession(session);
		return out;
	}

	/**
	 *
	 * @param <T>
	 * @param sql
	 *            <i> None null sql query </i>
	 * @param parameters
	 *            <i> Key value parameter pair </i>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getBySQL(String sql, Map<String, Object> parameters, int pageIndex, int pageSize, Class<? extends T> resultClass) {
		List<T> out;
		SessionFactory sf = hUtil.getSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		SQLQuery te = session.createSQLQuery(sql);
		buildParameter(resultClass, te, parameters);
		if(pageSize > 0){
			te.setFirstResult(pageIndex);
			te.setMaxResults(pageSize);
		}
		out = te.list();
		return out;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getEntityBySQL(Class<? extends T> clazz, String sql, Map<String, Object> parameters, int pageIndex, int pageSize) {
		List<T> out;
		SessionFactory sf = hUtil.getSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		SQLQuery te = session.createSQLQuery(sql);
		te.addEntity(clazz);
		buildParameter(clazz, te, parameters);
		if(pageSize > 0){
			te.setFirstResult(pageIndex);
			te.setMaxResults(pageSize);
		}
		out = te.list();
		return out;
	}

	public int executeUpdate(String hql, Map<String, Object> parameters){
		SessionFactory sf = hUtil.getSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		org.hibernate.Query query = session.createQuery(hql);
		buildParameter(Object.class, query, parameters);

		int o = query.executeUpdate();
		session.getTransaction().commit();
		return o;
	}

	public int executeUpdate(String hql, QueryParameter ... parameters){
		SessionFactory sf = hUtil.getSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		org.hibernate.Query query = session.createQuery(hql);
		buildParameter(Object.class, query, parameters);
		int o = query.executeUpdate();
		session.getTransaction().commit();
		return o;
	}
	
	public int executeUpdate(String hql){
		SessionFactory sf = hUtil.getSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		org.hibernate.Query query = session.createQuery(hql);
		int o = query.executeUpdate();
		session.getTransaction().commit();
		return o;
	}

	public void log(String msg) {
		logger.info(msg);
	}

	public void closeFactory(){
		if(hUtil.getSessionFactory() != null){
			hUtil.closeFactory();
		}
	}

	public static void main(String[] args) {
		RAudit ra = new RAudit();
		ra.setAuditData("Test Audit");
		ra.setActive(false);
		System.out.println(ra.toString());
		
		REntityManager dbService = REntityManager.getInstance();
		Person pa = dbService.getById(Person.class, 9L);
		System.out.println(pa);
		
		Person p = new Person();
		p.setFullName("OROWLAND");
		p.setAge(23);
		
		dbService.createOrUpdate(p);
		
		List<Person> listByHQL = dbService.getListByHQL(Person.class, "FROM Person");
		System.out.println(listByHQL);
	}

}
