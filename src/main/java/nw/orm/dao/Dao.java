package nw.orm.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;

public interface Dao<T> {
	
	/**
	 * Retrieves Entity instance by the primary key
	 * @param id primary key
	 * @return T entity instance
	 */
	T getById(Serializable id);
	
	/**
	 * Use of this method cautiously as it loads everything in the database for this
	 * entity. There are not where clauses, it just loads the entire table
	 * @return list of entity instances
	 */
	List<T> getAll();
	
	List<T> getListByCriteria(Criterion ... criteria);

	List<T> getListByCriteria(QueryModifier qm, Criterion ... criteria);
	
	T getByCriteria(Criterion ... criteria);

	T getByCriteria(QueryModifier qm, Criterion ... criteria);

	boolean softDelete(Serializable id);

	boolean bulkSoftDelete(List<Serializable> ids);
	
	T save(T item);
	
	void delete(T item);
	
	void deleteById(Serializable pk);

	T update(T item);
	
	T getByQuery(String query, QueryParameter ... parameters);

	boolean bulkDelete(List<Serializable> pks);

}
