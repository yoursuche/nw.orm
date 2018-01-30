package nw.orm.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import nw.orm.core.Entity;
import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryModifier;
import nw.orm.dao.Paging;

public class HibernateDao<T> extends HibernateDaoBase implements HDao<T> {
	
	protected Class<T> entityClass;
	
	public HibernateDao(SessionFactory sxnFactory, Class<T> clazz, boolean jtaEnabled, boolean useCurrentSession) {
		super(sxnFactory, jtaEnabled, useCurrentSession);
		
		isClassMapped(clazz);
		this.entityClass = clazz;
	}
	
	@Override
	public T getById(Serializable id) {
		T out = null;
		Session session = getSession();
		try {
			out = (T) session.get(entityClass, id, LockOptions.UPGRADE);
			commit(session);
		} catch (Exception e) {
			rollback(session);
			throw new NwormQueryException("Query Exception", e);
		}
		
		return out;
	}
	
	@Override
	public List<T> list(Criterion ... criteria) {
		return list(null, criteria);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> list(Paging paging, Criterion ... criteria) {
		
		List<T> out = new ArrayList<T>();
		Session session = getSession();
		try {
			Criteria te = session.createCriteria(entityClass);
			for (Criterion c : criteria) {
				te.add(c);
			}
			
			if(paging != null) {
				te.setFirstResult(paging.getPageOffset());
				te.setMaxResults(paging.getPageSize());
			}
			addSoftRestrictions(te, entityClass);
			out = te.list();
			commit(session);
			
		} catch (Exception e) {
			rollback(session);
			throw new NwormQueryException("Query Exception", e);
		}
		
		return out;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T get(Criterion ... criteria) {
		T out = null;
		Session session = getSession();
		try {
			Criteria te = session.createCriteria(entityClass);
			for (Criterion c : criteria) {
				te.add(c);
			}
			addSoftRestrictions(te, entityClass);
			out = (T) te.uniqueResult();
			commit(session);
		} catch (Exception e) {
			rollback(session);
			throw new NwormQueryException("Query Exception", e);
		}
		
		return out;
	}

	
	@Override
	public T save(T entity) {
		
		// get session
		Session session = getSession();
		try {
			session.save(entity);
			commit(session);
		} catch (HibernateException e) {
			rollback(session);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return entity;
	}
	
	@Override
	public boolean saveOrUpdate(T entity) {
		Session session = getSession();
		try {
			session.saveOrUpdate(entity);
			commit(session);
		} catch (Exception e) {
			rollback(session);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return true;
	}
	
	@Override
	public void bulkSave(List<T> entities) {
		
		// use stateless session
		StatelessSession session = getStatelessSession();
		try {
			for (T entity: entities) {
				session.insert(entity);
			}
			if(!jtaEnabled){
				session.getTransaction().commit();
			}
			session.close();
		} catch (HibernateException e) {
			if(!jtaEnabled){
				session.getTransaction().rollback();
			}
			session.close();
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	@Override
	public void delete(T entity) {
		
		Session session = getSession();
		try {
			session.delete(entity);
			commit(session);
		} catch (HibernateException e) {
			rollback(session);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	@Override
	public void deleteById(Serializable pk) {
		
		Session session = getSession();
		try {
			session.delete(session.get(entityClass, pk));
			commit(session);
		} catch (HibernateException e) {
			rollback(session);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	@Override
	public void bulkIdDelete(List<Serializable> pks) {
		
		StatelessSession session = getStatelessSession();
		try {
			for (Serializable pk : pks) {
				session.delete(session.get(entityClass, pk));
			}
			if(!jtaEnabled){
				session.getTransaction().commit();
			}
			session.close();
		} catch (HibernateException e) {
			if(!jtaEnabled){
				session.getTransaction().rollback();
			}
			session.close();
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	@Override
	public void bulkDelete(List<T> entities) {
		
		StatelessSession session = getStatelessSession();
		try {
			for (T entity: entities) {
				session.delete(entity);
			}
			if(!jtaEnabled){
				session.getTransaction().commit();
			}
			session.close();
		} catch (HibernateException e) {
			if(!jtaEnabled){
				session.getTransaction().rollback();
			}
			session.close();
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}

	@Override
	public T update(T entity) {
		
		Session session = getSession();
		try {
			session.update(entity);
			commit(session);
		} catch (HibernateException e) {
			rollback(session);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return entity;
	}
	
	@Override
	public void softDelete(Serializable id) {
		
		if (!Entity.class.isAssignableFrom(entityClass)) {
			throw new NwormQueryException();
		}
		T bc = get(Restrictions.idEq(id));
		if ((bc instanceof Entity)) {
			Entity e = (Entity) bc;
			e.setDeleted(true);
			update(bc);
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void bulkSoftDelete(List<Serializable> ids) {
		
		StatelessSession session = getStatelessSession();
		if (!Entity.class.isAssignableFrom(entityClass)) {
			throw new NwormQueryException();
		}

		try {
			for (Serializable s : ids) {
				T entity = (T) session.get(entityClass, s);
				Entity e = (Entity) entity;
				e.setDeleted(true);
				session.update(entity);
			}
			if(!jtaEnabled){
				session.getTransaction().commit();
			}
			session.close();
		} catch (HibernateException e) {
			if(!jtaEnabled){
				session.getTransaction().rollback();
			}
			session.close();
			throw new NwormQueryException("Nw.orm Exception", e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getListByExample(QueryModifier qm, Example example){
		List<T> items = new ArrayList<T>();
		Session sxn = getSession();
		Criteria te = sxn.createCriteria(qm.getQueryClazz()).add(example);
		try {
			modifyCriteria(te, qm);
			items = te.list();
			commit(sxn);
		} catch (HibernateException e) {
			rollback(sxn);
			throw new NwormQueryException("", e);
		}
		return items;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T getByExample(Class<T> clazz, Example example){
		T out = null;
		Session sxn = getSession();
		Criteria te = sxn.createCriteria(clazz).add(example);
		try {
			logger.debug(te.list() + "");
			out = (T) te.list().get(0);
			commit(sxn);
		} catch (HibernateException e) {
			rollback(sxn);
			throw new NwormQueryException("", e);
		}
		return out;
	}


}
