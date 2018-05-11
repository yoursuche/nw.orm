package nw.orm.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryParameter;
import nw.orm.dao.Paging;
import nw.orm.query.WormQuery;

public class JpaWormQuery extends JpaDaoBase implements WormQuery {
	
	private Paging paging;
	private String query;
	private List<QueryParameter> parameters = new ArrayList<QueryParameter>();
	
	public JpaWormQuery(EntityManagerFactory em, boolean managedTransaction, String sql) {
		super(em, managedTransaction);
		this.query = sql;
	}

	@Override
	public <T> T get(Class<T> resultClass) {
		EntityManager mgr = getEntityManager();
		T res = null;
		try {
			TypedQuery<T> typedQuery = mgr.createQuery(query, resultClass);
			setParameters(false, typedQuery, parameters.toArray(new QueryParameter[0]));
			res = typedQuery.getSingleResult();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return res;
	}

	@Override
	public <T> List<T> list(Class<T> resultClass) {
		EntityManager mgr = getEntityManager();
		List<T> res = new ArrayList<T>();
		try {
			TypedQuery<T> typedQuery = mgr.createQuery(query, resultClass);
			setParameters(false, typedQuery, parameters.toArray(new QueryParameter[0]));
			
			if(paging != null) {
				typedQuery.setFirstResult(paging.getPageOffset());
				typedQuery.setMaxResults(paging.getPageSize());
			}
			res = typedQuery.getResultList();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return res;
	}

	@Override
	public WormQuery bind(QueryParameter... parameters) {
		for (QueryParameter queryParameter : parameters) {
			this.parameters.add(queryParameter);
		}
		return this;
	}

	@Override
	public WormQuery paginate(int offset, int pageSize) {
		this.paging = Paging.paginate(offset, pageSize);
		return this;
	}

}
