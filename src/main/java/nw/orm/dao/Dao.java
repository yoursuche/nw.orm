package nw.orm.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;

/**
 * 
 * @author Ogwara O. Rowland
 *
 * @param <T>
 */
public interface Dao<T> {
	
	/**
	 * Persists the provided entity
	 * @param entity
	 * @return the entity with an updated primary key entry
	 * @throws NwormQueryException
	 */
	T save(T entity);
	
	/**
	 * Saves the list of items in using a stateless session
	 * @param entities
	 * @throws NwormQueryException
	 */
	void bulkSave(List<T> entities);
	
	/**
	 * Deletes the provided entity
	 * @param entity entity to be deleted
	 * @throws NwormQueryException
	 */
	void delete(T entity);
	
	/**
	 * Deletes the entity with the provided 
	 * primary key
	 * @param pk primary key
	 * @throws NwormQueryException
	 */
	void deleteById(Serializable pk);
	
	/**
	 * Deletes all entities in the list using a stateless
	 * session
	 * @param entities
	 * @throws NwormQueryException
	 */
	void bulkDelete(List<T> entities);
	
	/**
	 * Delete all entities with the primary keys in
	 * the list
	 * @param pks
	 * @throws NwormQueryException
	 */
	void bulkIdDelete(List<Serializable> pks);
	
	/**
	 * Updates the provided entity
	 * @param entity
	 * @return
	 * @throws NwormQueryException
	 */
	T update(T entity);
	
	/**
	 * Marks the entity as deleted but does not actually
	 * carry out a delete
	 * @param id
	 * @throws NwormQueryException
	 */
	void softDelete(Serializable id);

	/**
	 * Marks entities as deleted using stateless session
	 * @param ids
	 */
	void bulkSoftDelete(List<Serializable> ids);

	
	/**
	 * Retrieves Entity instance by the primary key
	 * @param id primary key
	 * @return T entity instance
	 * @throws NwormQueryException
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
	
//	T getByQuery(String query, QueryParameter ... parameters);

}
