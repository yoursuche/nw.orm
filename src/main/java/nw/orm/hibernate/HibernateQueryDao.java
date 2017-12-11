package nw.orm.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import nw.orm.core.exception.NwormQueryException;
import nw.orm.dao.GenericQueryDao;

public class HibernateQueryDao extends HibernateDaoBase implements GenericQueryDao {

	public HibernateQueryDao(SessionFactory sxnFactory, boolean jtaEnabled, boolean useCurrentSession) {
		super(sxnFactory, jtaEnabled, useCurrentSession);
	}

	@Override
	public Serializable save(Object entity) {
		
		isClassMapped(entity.getClass());
		
		Session session = getSession();
		try {
			Serializable id = session.save(entity);
			commit(session);
			closeSession(session);
			return id;
		} catch (HibernateException e) {
			rollback(session);
			closeSession(session);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	@Override
	public List<Serializable> bulkSave(List<?> entities) {
		
		List<Serializable> ids = new ArrayList<Serializable>();
		StatelessSession session = getStatelessSession();
		try {
			for (Object entity: entities) {
				ids.add(session.insert(entity));
			}
			if(!jtaEnabled){
				session.getTransaction().commit();
			}
			session.close();
			return ids;
		} catch (HibernateException e) {
			if(!jtaEnabled){
				session.getTransaction().rollback();
			}
			session.close();
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	@Override
	public boolean delete(Object entity) {
		
		isClassMapped(entity.getClass());
		Session session = getSession();
		try {
			session.delete(entity);
			commit(session);
			closeSession(session);
			return true;
		} catch (HibernateException e) {
			rollback(session);
			closeSession(session);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		
	}
	
	@Override
	public boolean bulkDelete(List<?> entities) {
		
		StatelessSession session = getStatelessSession();
		try {
			for (Object entity: entities) {
				session.delete(entity);
			}
			if(!jtaEnabled){
				session.getTransaction().commit();
			}
			session.close();
			return true;
		} catch (HibernateException e) {
			if(!jtaEnabled){
				session.getTransaction().rollback();
			}
			session.close();
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	@Override
	public boolean update(Object entity) {
		
		isClassMapped(entity.getClass());
		Session session = getSession();
		try {
			session.update(entity);
			commit(session);
			closeSession(session);
			return true;
		} catch (HibernateException e) {
			rollback(session);
			closeSession(session);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		
	}

	/**
	 * Update bulk.
	 *
	 * @param items the items
	 * @return true if update completed without errors,
	 * returns false with rollback if an error occurs
	 */
	@Override
	public boolean bulkUpdate(List<?> entities) {
		
		StatelessSession session = getStatelessSession();
		try {
			for (Object item: entities) {
				session.update(item);
			}
			if(!jtaEnabled){
				session.getTransaction().commit();
			}
			session.close();
			return true;
		} catch (HibernateException e) {
			if(!jtaEnabled){
				session.getTransaction().rollback();
			}
			session.close();
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	@Override
	public boolean saveOrUpdate(Object obj) {
		
		Session session = getSession();
		try {
			session.saveOrUpdate(obj);
			commit(session);
			closeSession(session);
			return true;
		} catch (Exception e) {
			rollback(session);
			closeSession(session);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	


}
