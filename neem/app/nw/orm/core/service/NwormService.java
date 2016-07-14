package nw.orm.core.service;

import java.io.Serializable;
import java.util.List;

import nw.orm.core.Entity;
import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;

// TODO: Auto-generated Javadoc
/**
 * The Interface NwormService.
 */
public interface NwormService {

	/**
	 * Retrieves an entity by its primary key with default lock option of READ.
	 *
	 * @param <T> The target entity type
	 * @param entityClass target entity class
	 * @param primaryKey primary key
	 * @return Entity instance with the specified primary key or null
	 * @throws NwormQueryException when an error occurs
	 */
	public <T> T getById(Class<T> entityClass, Serializable primaryKey);

	/**
	 * Retrieves a database entry by its primary key while locking the table.
	 *
	 * @param <T> The target entity type
	 * @param entityClass target entity class
	 * @param primaryKey primary key
	 * @param lockOption enable lock option upgrade
	 * @return Entity instance with the specified primary key or null
	 * @throws NwormQueryException when an error occurs
	 */
	public <T> T getById(Class<T> entityClass, Serializable primaryKey, boolean lockOption);

	/**
	 * Retrieves a none paginated list of tables represented by the entity class spscified.
	 * This method should be used cautiously, as loading the entire entries might be resource intensive.
	 *
	 * @param <T> The target entity type
	 * @param entityClass target entity class
	 * @return List containing all entries
	 * @throws NwormQueryException when an error occurs
	 */
	public <T> List<T> getAll(Class<T> entityClass);

	/**
	 * Gets the by sql.
	 *
	 * @param <T> The target entity type
	 * @param returnClazz return type for the entity
	 * @param sql the sql to be executed
	 * @param sqlMod see {@link SQLModifier}
	 * @param params see {@link QueryParameter}
	 * @return the output of the sql statement
	 * @throws NwormQueryException when an error occurs
	 */
	public <T> List<T> getBySQL(Class<T> returnClazz, String sql, SQLModifier sqlMod, QueryParameter ... params);

	/**
	 * Performs raw sql insert, update or delete (DML) operation .
	 *
	 * @param sql insert/update/delete query to be executed
	 * @param params parameter list
	 * @return an int representing the state of the execution
	 * @throws NwormQueryException when an error occurs
	 */
	public int executeSQLUpdate(String sql, QueryParameter ... params);

	/**
	 * Execute hql update.
	 *
	 * @param hql the hql for the crud operation
	 * @param params parameter list
	 * @return an int representing the state of the execution
	 * @throws NwormQueryException when an error occurs
	 */
	public int executeHQLUpdate(String hql, QueryParameter ...params);

	/**
	 * sets the deleted field for this entity to true. All queries ignores this entry
	 *
	 * @param entityClass the entity class
	 * @param id primary key
	 * @return true, if successful
	 * @throws NwormQueryException when an error occurs
	 */
	public boolean softDelete(Class<? extends Entity> entityClass, Serializable id);

	/**
	 * sets the deleted field for all entries in the list. All entries will be ignored in all queries
	 *
	 * @param paramClass the entity class
	 * @param paramList the param list
	 * @return true, if successful
	 */
	public boolean bulkSoftDelete(Class<? extends Entity> paramClass, List<Serializable> paramList);

	/**
	 * Deletes an entry from the database.
	 *
	 * @param paramObject the param object
	 * @return true, if successful
	 */
	public boolean remove(Object paramObject);

	/**
	 * Deletes an entry using its primary key.
	 *
	 * @param paramClass the param class
	 * @param paramSerializable the param serializable
	 * @return true, if successful
	 */
	public boolean remove(Class<?> paramClass, Serializable paramSerializable);

	/**
	 * Deletes all entries with primary keys specified in the list.
	 *
	 * @param paramClass the param class
	 * @param paramList the param list
	 * @return true, if successful
	 */
	public boolean bulkRemove(Class<?> paramClass, List<Serializable> paramList);

	/**
	 * Inserts an entry to the database.
	 *
	 * @param paramObject the param object
	 * @return primary key
	 */
	public Serializable create(Object paramObject);

	/**
	 * Creates the specified list of items.
	 *
	 * @param paramList the param list
	 * @return the list
	 */
	public List<Serializable> createBulk(List<?> paramList);

	/**
	 * Updates an entry.
	 *
	 * @param paramObject the param object
	 * @return true, if successful
	 */
	public boolean update(Object paramObject);

	/**
	 * Performs bulk update.
	 *
	 * @param paramList the param list
	 * @return true, if successful
	 */
	public boolean updateBulk(List<?> paramList);

	/**
	 * Toggle active.
	 *
	 * @param paramClass the param class
	 * @param paramSerializable the param serializable
	 * @return true, if successful
	 */
	public boolean toggleActive(Class<? extends Entity> paramClass, Serializable paramSerializable);

	/**
	 * Creates the or update.
	 *
	 * @param paramObject the param object
	 * @return true, if successful
	 */
	public boolean createOrUpdate(Object paramObject);

}