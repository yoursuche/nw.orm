package nw.orm.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import nw.orm.core.NwormEntity;
import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;
import nw.orm.core.session.HibernateSessionService;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

// TODO: Auto-generated Javadoc
/**
 * The Interface NwormService.
 */
public interface NwormService {

	/**
	 * Retrieves a database entry by its primary key with default lockoption of READ.
	 *
	 * @param <T> the generic type
	 * @param paramClass target entity class
	 * @param paramSerializable primary key
	 * @return target entity
	 */
	public <T> T getById(Class<T> paramClass, Serializable paramSerializable);

	/**
	 * Retrieves a database entry by its primary key while locking the table.
	 *
	 * @param <T> the generic type
	 * @param paramClass target entity class
	 * @param paramSerializable primary key
	 * @param paramBoolean enable lockoption upgrade
	 * @return target entity
	 */
	public <T> T getById(Class<T> paramClass, Serializable paramSerializable, boolean paramBoolean);

	/**
	 * Retrieves a none paginated list of tables represented by the entity class spscified.
	 * This method should be used cautiously, as loading the entire entries might be resource intensive.
	 *
	 * @param <T> the generic type
	 * @param paramClass target entity
	 * @return List containing all entries
	 */
	public <T> List<T> getAll(Class<T> paramClass);

	/**
	 * Retrieves a unique entry using the specified criteria.
	 *
	 * @param <T> the generic type
	 * @param paramClass the param class
	 * @param paramArrayOfCriterion the param array of criterion
	 * @return the by criteria
	 */
	public <T> T getByCriteria(Class<T> paramClass, Criterion ... paramArrayOfCriterion);

	/**
	 * Retrieves a list of entries based on the specified criteria.
	 *
	 * @param <T> the generic type
	 * @param paramClass the param class
	 * @param paramArrayOfCriterion the param array of criterion
	 * @return the list by criteria
	 */
	public <T> List<T> getListByCriteria(Class<T> paramClass, Criterion ... paramArrayOfCriterion);

	/**
	 * Retrieves an entry based specified HQL and parameters.
	 *
	 * @param <T> the generic type
	 * @param hql HQL for querying
	 * @param paramMap key value parameter map
	 * @param paramClass target entity class
	 * @return A unique entry representing the entity
	 */
	public <T> T getByHQL(String hql, Map<String, Object> paramMap, Class<T> paramClass);

	/**
	 * Gets the by hql.
	 *
	 * @param <T> the generic type
	 * @param paramClass the param class
	 * @param hql the hql
	 * @param paramArrayOfQueryParameter the param array of query parameter
	 * @return the by hql
	 * @see #getByHQL(String, Map, Class)
	 */
	public <T> T getByHQL(Class<T> paramClass, String hql, QueryParameter ... paramArrayOfQueryParameter);

	/**
	 * Retrieves a list based on the specified hql and parameter.
	 *
	 * @param <T> the generic type
	 * @param hql the hql
	 * @param paramMap the param map
	 * @param paramClass the param class
	 * @return the list by hql
	 */
	public <T> List<T> getListByHQL(String hql, Map<String, Object> paramMap, Class<T> paramClass);

	/**
	 * Gets the list by hql.
	 *
	 * @param <T> the generic type
	 * @param paramClass the param class
	 * @param paramString the param string
	 * @param paramArrayOfQueryParameter the param array of query parameter
	 * @return the list by hql
	 * @see getListByHQL
	 */
	public <T> List<T> getListByHQL(Class<T> paramClass, String paramString, QueryParameter ... paramArrayOfQueryParameter);

	/**
	 * Gets the by sql.
	 *
	 * @param <T> the generic type
	 * @param returnClazz return type for the entity
	 * @param sql the sql
	 * @param sqlMod see {@link SQLModifier}
	 * @param params see {@link QueryParameter}
	 * @return the by sql
	 */
	public <T> List<T> getBySQL(Class<T> returnClazz, String sql, SQLModifier sqlMod, QueryParameter ... params);

	/**
	 * Gets the by criteria.
	 *
	 * @param <T> the generic type
	 * @param returnClazz the return clazz
	 * @param qm the qm
	 * @param criterion the param array of criterion
	 * @return the by criteria
	 */
	public <T> T getByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion ... criterion);

	/**
	 * Gets the list by criteria.
	 *
	 * @param <T> the generic type
	 * @param returnClazz the return clazz
	 * @param qm the qm
	 * @param paramArrayOfCriterion the param array of criterion
	 * @return the list by criteria
	 */
	public <T> List<T> getListByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion ... paramArrayOfCriterion);

	/**
	 * Gets the by example.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param example the example
	 * @return the by example
	 */
	public <T> T getByExample(Class<T> clazz, Example example);

	/**
	 * Gets the list by example.
	 *
	 * @param <T> the generic type
	 * @param qm the qm
	 * @param example the example
	 * @return the list by example
	 */
	public <T> List<T> getListByExample(QueryModifier qm, Example example);

	/**
	 * Performs raw sql insert, update or delete (DML) operation .
	 *
	 * @param sql insert/update/delete
	 * @param params the params
	 * @return the int
	 */
	public int executeSQLUpdate(String sql, QueryParameter ... params);

	/**
	 * Execute hql update.
	 *
	 * @param hql the hql
	 * @param params the params
	 * @return the int
	 */
	public int executeHQLUpdate(String hql, QueryParameter ...params);

	/**
	 * sets the deleted field for this entity to true. All queries ignores this entry
	 *
	 * @param paramClass the param class
	 * @param paramSerializable the param serializable
	 * @return true, if successful
	 */
	public boolean softDelete(Class<? extends NwormEntity<?>> paramClass, Serializable paramSerializable);

	/**
	 * sets the deleted field for all entries in the list. All entries will be ignored in all queries
	 *
	 * @param paramClass the param class
	 * @param paramList the param list
	 * @return true, if successful
	 */
	public boolean bulkSoftDelete(Class<? extends NwormEntity<?>> paramClass, List<Serializable> paramList);

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
	public boolean toggleActive(Class<? extends NwormEntity<?>> paramClass, Serializable paramSerializable);

	/**
	 * Creates the or update.
	 *
	 * @param paramObject the param object
	 * @return true, if successful
	 */
	public boolean createOrUpdate(Object paramObject);

	/**
	 * Gets the session service.
	 *
	 * @return the session service
	 */
	public HibernateSessionService getSessionService();

}