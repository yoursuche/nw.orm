package nw.orm.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryParameter;
import nw.orm.dao.Paging;
import nw.orm.query.WormQuery;

public class NativeWormQuery extends  JpaDaoBase implements WormQuery {


	Paging paging;
	String query;
	List<Object> parameters = new ArrayList<Object>();
	
	public NativeWormQuery(EntityManagerFactory em, boolean managedTransaction, String sql) {
		super(em, managedTransaction);
		this.query = sql;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> resultClass) {
		EntityManager mgr = getEntityManager();
		T res = null;
		try {
			
			Query typedQuery;
			boolean isEntity = isMappedEntity(resultClass);
						
			if(isEntity)
				typedQuery = mgr.createNativeQuery(query, resultClass);
			else {
				typedQuery = mgr.createNativeQuery(query);
			}
			setParameters(typedQuery);
			res = (T) typedQuery.getSingleResult();
			commit(mgr);
		} catch (Exception e) {
			rollback(mgr);
			throw new NwormQueryException("Nw.orm Exception", e);
		}
		return res;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> list(Class<T> resultClass) {
		EntityManager mgr = getEntityManager();
		List<T> res = new ArrayList<T>();
		try {
			
			Query typedQuery;
			boolean isEntity = isMappedEntity(resultClass);
						
			if(isEntity)
				typedQuery = mgr.createNativeQuery(query, resultClass);
			else {
				typedQuery = mgr.createNativeQuery(query);
			}
			setParameters(typedQuery);
			
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
		for (QueryParameter qp : parameters) {
			this.parameters.add(qp.getValue());
		}
		return this;
	}

	@Override
	public WormQuery paginate(int offset, int pageSize) {
		this.paging = Paging.paginate(offset, pageSize);
		return this;
	}
	
	protected void setParameters(Query query) {
		int index = 1;
		for (Object param : parameters) {
			query.setParameter(index, param);
			
			index += 1;
		}
		
	}


}
