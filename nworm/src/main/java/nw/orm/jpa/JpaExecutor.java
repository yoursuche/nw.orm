package nw.orm.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;
import nw.orm.dao.QueryExecutor;
import nw.orm.query.WormQuery;

public class JpaExecutor extends JpaDaoBase implements QueryExecutor {

	public JpaExecutor(EntityManagerFactory em, boolean managedTransaction) {
		super(em, managedTransaction);
	}
	
	public WormQuery query(String query) {
		return new JpaWormQuery(em, managedTransaction, query);
	}
	
	public WormQuery nativeQuery(String query) {
		return new NativeWormQuery(em, managedTransaction, query);
	}

	@Override
	public <T> T query(Class<T> resultClass, String jpql, QueryParameter... parameters) {
		EntityManager mgr = getEntityManager();
		T res = null;
		try {
			TypedQuery<T> query = mgr.createQuery(jpql, resultClass);
			setParameters(false, query, parameters);
			res = query.getSingleResult();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return res;
	}

	@Override
	public <T> List<T> queryList(Class<T> resultClass, String jpql, QueryParameter... parameters) {
		EntityManager mgr = getEntityManager();
		List<T> res = new ArrayList<T>();
		try {
			TypedQuery<T> query = mgr.createQuery(jpql, resultClass);
			setParameters(false, query, parameters);
			res = query.getResultList();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return res;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getBySQL(Class<T> returnClazz, String sql, SQLModifier sqlMod, QueryParameter... params) {
		EntityManager mgr = getEntityManager();
		List<T> res = new ArrayList<T>();
		try {
			Query query = mgr.createNativeQuery(sql, returnClazz);
			setParameters(false, query, params);
			
			if(sqlMod.isPaginated()) {
				query.setFirstResult(sqlMod.getPageIndex());
				query.setMaxResults(sqlMod.getMaxResult());
			}
			res = query.getResultList();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return res;
	}

	@Override
	public int execute(String shql, QueryParameter... params) {
		EntityManager mgr = getEntityManager();
		int res = -1;
		try {
			Query query = mgr.createNativeQuery(shql);
			setParameters(false, query, params);
			
			res = query.executeUpdate();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return res;
	}

}
