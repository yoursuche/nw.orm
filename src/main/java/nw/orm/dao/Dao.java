package nw.orm.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;

public interface Dao<T> {
	
	T getById(Serializable id);
	
	T save(T item);
	
	void delete(T item);

	T update(T item);
	
	T getByQuery(String query, QueryParameter ... parameters);
	
	List<T> getAll();
	
	List<T> getListByCriteria(Criterion ... criteria);

	List<T> getListByCriteria(QueryModifier qm, Criterion ... criteria);
	
	T getByCriteria(Criterion ... criteria);

	T getByCriteria(QueryModifier qm, Criterion ... criteria);

	boolean softDelete(Serializable id);

	boolean bulkSoftDelete(List<Serializable> ids);

}
