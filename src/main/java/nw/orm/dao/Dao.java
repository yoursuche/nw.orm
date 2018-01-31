package nw.orm.dao;

import java.io.Serializable;
import java.util.List;

import nw.orm.core.exception.NwormQueryException;

/**
 * 
 * @author Ogwara O. Rowland
 *
 * @param <T> Entity class type
 */
public interface Dao<T> {
	
	/**
	 * Persists the provided entity
	 * @param entity the entity to save
	 * @return the entity with an updated primary key entry
	 * @throws NwormQueryException Query Exception
	 */
	T save(T entity);
	
	boolean saveOrUpdate(T entity);
	
	/**
	 * Saves the list of items in using a stateless session
	 * @param entities list of entities to save
	 * @throws NwormQueryException Query Exception
	 */
	void bulkSave(List<T> entities);
	
	/**
	 * Deletes the provided entity
	 * @param entity entity to be deleted
	 * @throws NwormQueryException Query Exception
	 */
	void delete(T entity);
	
	/**
	 * Deletes the entity with the provided 
	 * primary key
	 * @param pk primary key
	 * @throws NwormQueryException Query Exception
	 */
	void deleteById(Serializable pk);
	
	/**
	 * Deletes all entities in the list using a stateless
	 * session
	 * @param entities entities to save
	 * @throws NwormQueryException Query Exception
	 */
	void bulkDelete(List<T> entities);
	
	/**
	 * Delete all entities with the primary keys in
	 * the list
	 * @param pks list of primary keys
	 * @throws NwormQueryException Query Exception
	 */
	void bulkIdDelete(List<Serializable> pks);
	
	/**
	 * Updates the provided entity
	 * @param entity to update
	 * @return updated entity
	 * @throws NwormQueryException Query Exception
	 */
	T update(T entity);
	
	/**
	 * Marks the entity as deleted but does not actually
	 * carry out a delete
	 * @param id primary key
	 * @throws NwormQueryException Query Exception
	 */
	void softDelete(Serializable id);

	/**
	 * Marks entities as deleted using stateless session
	 * @param ids primary keys
	 */
	void bulkSoftDelete(List<Serializable> ids);

	
	/**
	 * Retrieves Entity instance by the primary key
	 * @param id primary key
	 * @return T entity instance
	 * @throws NwormQueryException Query Exception
	 */
	T getById(Serializable id);
	

}
