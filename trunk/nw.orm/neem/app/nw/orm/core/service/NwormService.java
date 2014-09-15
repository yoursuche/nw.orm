package nw.orm.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import nw.orm.core.NwormEntity;
import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

public interface NwormService {
	
	/**
	 * Retrieves a database entry by its primary key with default lockoption of READ
	 * @param paramClass target entity class
	 * @param paramSerializable primary key
	 * @return target entity
	 */
	public <T> T getById(Class<T> paramClass, Serializable paramSerializable);

	/**
	 * Retrieves a database entry by its primary key while locking the table
	 * @param paramClass target entity class
	 * @param paramSerializable primary key
	 * @param paramBoolean enable lockoption upgrade
	 * @return target entity
	 */
	public <T> T getById(Class<T> paramClass, Serializable paramSerializable, boolean paramBoolean);
	
	/**
	 * Retrieves a none paginated list of tables represented by the entity class spscified.
	 * This method should be used cautiously, as loading the entire entries might be resource intensive.
	 * @param paramClass target entity
	 * @return List containing all entries
	 */
	public <T> List<T> getAll(Class<T> paramClass);

	/**
	 * Retrieves a unique entry using the specified criteria
	 * @param paramClass
	 * @param paramArrayOfCriterion
	 * @return
	 */
	public <T> T getByCriteria(Class<T> paramClass, Criterion ... paramArrayOfCriterion);

	/**
	 * Retrieves a list of entries based on the specified criteria
	 * @param paramClass
	 * @param paramArrayOfCriterion
	 * @return
	 */
	public <T> List<T> getListByCriteria(Class<T> paramClass, Criterion ... paramArrayOfCriterion);

	/**
	 * Retrieves an entry based specified HQL and parameters
	 * @param hql HQL for querying
	 * @param paramMap key value parameter map
	 * @param paramClass target entity class
	 * @return A unique entry representing the entity
	 */
	public <T> T getByHQL(String hql, Map<String, Object> paramMap, Class<T> paramClass);

	/**
	 * @see getByHQL(String, Map, Class)
	 * @param hql
	 * @param paramString
	 * @param paramArrayOfQueryParameter
	 * @return
	 */
	public <T> T getByHQL(Class<T> paramClass, String hql, QueryParameter ... paramArrayOfQueryParameter);

	/**
	 * Retrieves a list based on the specified hql and parameter
	 * @param hql
	 * @param paramMap
	 * @param paramClass
	 * @return
	 */
	public <T> List<T> getListByHQL(String hql, Map<String, Object> paramMap, Class<T> paramClass);

	/**
	 * @see getListByHQL
	 * @param paramClass
	 * @param paramString
	 * @param paramArrayOfQueryParameter
	 * @return
	 */
	public <T> List<T> getListByHQL(Class<T> paramClass, String paramString, QueryParameter ... paramArrayOfQueryParameter);

	/**
	 * @param returnClazz return type for the entity
	 * @param sql
	 * @param sqlMod @see {@link SQLModifier}
	 * @param params @see {@link QueryParameter}
	 * @return
	 */
	public <T> List<T> getBySQL(Class<T> returnClazz, String sql, SQLModifier sqlMod, QueryParameter ... params);
	
	/**
	 * @see {@link QueryModifier}
	 * @param qm
	 * @param paramArrayOfCriterion
	 * @return
	 */
	public <T> T getByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion ... paramArrayOfCriterion);
	
	/**
	 * 
	 * @param qm
	 * @param paramArrayOfCriterion
	 * @return
	 */
	public <T> List<T> getListByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion ... paramArrayOfCriterion);
	
	public <T> T getByExample(Class<T> clazz, Example example);
	
	public <T> List<T> getListByExample(QueryModifier qm, Example example);
	
	/**
	 * Performs raw sql insert, update or delete (DML) operation 
	 * @param sql insert/update/delete
	 * @param params
	 * @return
	 */
	public int executeSQLUpdate(String sql, QueryParameter ... params);
	
	/**
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public int executeHQLUpdate(String hql, QueryParameter ...params);
	
	/**
	 * sets the deleted field for this entity to true. All queries ignores this entry
	 * @param paramClass
	 * @param paramSerializable
	 * @return
	 */
	public boolean softDelete(Class<? extends NwormEntity<?>> paramClass, Serializable paramSerializable);

	/**
	 * sets the deleted field for all entries in the list. All entries will be ignored in all queries
	 * @param paramClass
	 * @param paramList
	 * @return
	 */
	public boolean bulkSoftDelete(Class<? extends NwormEntity<?>> paramClass, List<Serializable> paramList);

	/**
	 * Deletes an entry from the database
	 * @param paramObject
	 * @return
	 */
	public boolean remove(Object paramObject);

	/**
	 * Deletes an entry using its primary key
	 * @param paramClass
	 * @param paramSerializable
	 * @return
	 */
	public boolean remove(Class<?> paramClass, Serializable paramSerializable);

	/**
	 * Deletes all entries with primary keys specified in the list
	 * @param paramClass
	 * @param paramList
	 * @return
	 */
	public boolean bulkRemove(Class<?> paramClass, List<Serializable> paramList);

	/**
	 * Inserts an entry to the database
	 * @param paramObject
	 * @return primary key
	 */
	public Serializable create(Object paramObject);

	/**
	 * Creates the specified list of items
	 * @param paramList
	 * @return
	 */
	public List<Serializable> createBulk(List<?> paramList);

	/**
	 * Updates an entry
	 * @param paramObject
	 * @return
	 */
	public boolean update(Object paramObject);

	/**
	 * Performs bulk update
	 * @param paramList
	 * @return
	 */
	public boolean updateBulk(List<?> paramList);

	public boolean toggleActive(Class<? extends NwormEntity<?>> paramClass, Serializable paramSerializable);

	public boolean createOrUpdate(Object paramObject);

}