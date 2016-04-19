/*
 * Copyright 2013 - 2015, Neemworks Nigeria <nw.orm@nimworks.com>
 Permission to use, copy, modify, and distribute this software for any
 purpose with or without fee is hereby granted, provided that the above
 copyright notice and this permission notice appear in all copies.

 THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package nw.orm.core.service;

import java.io.Serializable;
import java.util.List;

import nw.orm.core.NwormEntity;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;

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

}