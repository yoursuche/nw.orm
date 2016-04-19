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

import java.util.List;
import java.util.Map;

import nw.orm.core.exception.NwormArgumentException;
import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.session.HibernateSessionService;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

/**
 *
 * @author Ogwara O. Rowland
 *
 */
public interface NwormHibernateService extends NwormService {

	/**
	 * Retrieves a unique entity entry using the specified criteria.
	 *
	 * @param <T> Target Entity type
	 * @param entityClass Target Entity class reference
	 * @param queryCriterions list of hibernate criterion used to filter result
	 * @return an instance of the target entity or null if not found
	 * @throws NwormArgumentException if a exception occurs during operation
	 */
	public abstract <T> T getByCriteria(Class<T> entityClass, Criterion... queryCriterions);

	/**
	 * Retrieves a list of entity entries based on the specified criteria.
	 *
	 * @param <T> Target Entity type
	 * @param entityClass Entity class declaration
	 * @param queryCriterions list of hibernate criterion used to filter result
	 * @return list of instance(s) or the target entity or an empty list if not found
	 * @throws NwormArgumentException if a exception occurs during operation
	 */
	public abstract <T> List<T> getListByCriteria(Class<T> entityClass, Criterion... queryCriterions);

	/**
	 * Retrieves an entity entry based specified HQL and parameters.
	 *
	 * @param <T> Target Entity Type
	 * @param hql HQL for querying
	 * @param paramMap key value parameter map
	 * @param paramClass target entity class
	 * @return A unique entry for the target entity
	 * @throws NwormArgumentException if a exception occurs during operation
	 */
	public abstract <T> T getByHQL(String hql, Map<String, Object> paramMap, Class<T> paramClass);

	/**
	 * Retrieves an entity entry based specified HQL and parameters.
	 *
	 * @param <T> Target Entity Type
	 * @param hql HQL for querying
	 * @param paramMap key value parameter map
	 * @param paramClass target entity class
	 * @param skipDeleted whether to ignore softly deleted entries or not
	 * @return A unique entry for the target entity
	 * @throws NwormArgumentException if a exception occurs during operation
	 */
	public abstract <T> T getByHQL(String hql, Map<String, Object> paramMap, Class<T> paramClass, boolean skipDeleted);

	/**
	 * Gets an entity entry based specified HQL and parameters.
	 *
	 * @param <T> Target Entity Type
	 * @param paramClass target entity class
	 * @param hql the hql for querying
	 * @param paramArrayOfQueryParameter parameter list used by the hql
	 * @return A unique entry representing the entity
	 * @see #getByHQL(String, Map, Class)
	 * @throws NwormArgumentException if a exception occurs during operation
	 */
	public abstract <T> T getByHQL(Class<T> paramClass, String hql, QueryParameter... paramArrayOfQueryParameter);

	/**
	 * Gets an entity entry based specified HQL and parameters.
	 *
	 * @param <T> Target Entity Type
	 * @param paramClass target entity class
	 * @param hql the hql for querying
	 * @param skipDeleted whether to ignore softly deleted entries or not
	 * @param paramArrayOfQueryParameter parameter list used by the hql
	 * @return A unique entry representing the entity
	 * @see #getByHQL(String, Map, Class)
	 * @throws NwormArgumentException if a exception occurs during operation
	 */
	public abstract <T> T getByHQL(Class<T> paramClass, String hql, boolean skipDeleted, QueryParameter... paramArrayOfQueryParameter);

	/**
	 * Retrieves a list based on the specified hql and parameter.
	 *
	 * @param <T> Target Entity Type
	 * @param hql the hql for querying
	 * @param paramMap key value parameter map
	 * @param paramClass target entity class
	 * @return the list with entries from the hql
	 * @throws NwormArgumentException if a exception occurs during operation
	 */
	public abstract <T> List<T> getListByHQL(String hql, Map<String, Object> paramMap, Class<T> paramClass);

	/**
	 * Retrieves a list based on the specified hql and parameter.
	 *
	 * @param <T> Target Entity Type
	 * @param hql the hql for querying
	 * @param paramMap key value parameter map
	 * @param paramClass target entity class
	 * @param skipDeleted  whether to ignore softly deleted entries or not
	 * @return the list with entries from the hql
	 * @throws NwormArgumentException if a exception occurs during operation
	 */
	public abstract <T> List<T> getListByHQL(String hql, Map<String, Object> paramMap, Class<T> paramClass, boolean skipDeleted);

	/**
	 * Gets a list of entries by the specified criteria
	 *
	 * @param <T> Target Entity Type
	 * @param paramClass the param class
	 * @param paramString the param string
	 * @param paramArrayOfQueryParameter the param array of query parameter
	 * @return the list with entries from the hql
	 * @see getListByHQL
	 */
	public abstract <T> List<T> getListByHQL(Class<T> paramClass, String paramString, QueryParameter... paramArrayOfQueryParameter);

	/**
	 * Gets a list of entries by the specified criteria
	 *
	 * @param <T> Target Entity Type
	 * @param paramClass the param class
	 * @param paramString the param string
	 * @param skipDeleted  whether to ignore softly deleted entries or not
	 * @param paramArrayOfQueryParameter the param array of query parameter
	 * @return the list with entries from the hql
	 * @see getListByHQL
	 */
	public abstract <T> List<T> getListByHQL(Class<T> paramClass, String paramString, boolean skipDeleted, QueryParameter... paramArrayOfQueryParameter);

	/**
	 * Gets the by criteria.
	 *
	 * @param <T> Target Entity Type
	 * @param returnClazz target entity class
	 * @param qm modifiers used on the generated criteria
	 * @param criterion the list of criterion
	 * @return an instance of the target entity
	 * @throws NwormArgumentException if a exception occurs during operation
	 */
	public abstract <T> T getByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion... criterion);

	/**
	 * Gets the list by criteria.
	 *
	 * @param <T> the generic type
	 * @param returnClazz the return clazz
	 * @param qm the qm
	 * @param paramArrayOfCriterion the param array of criterion
	 * @return the list by criteria
	 */
	public abstract <T> List<T> getListByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion... paramArrayOfCriterion);

	/**
	 * Gets the by example.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param example the example
	 * @return the by example
	 */
	public abstract <T> T getByExample(Class<T> clazz, Example example);

	/**
	 * Gets the list by example.
	 *
	 * @param <T> the generic type
	 * @param qm the qm
	 * @param example the example
	 * @return the list by example
	 */
	public abstract <T> List<T> getListByExample(QueryModifier qm,
			Example example);

	/**
	 * Gets the session service.
	 *
	 * @return the session service
	 */
	public abstract HibernateSessionService getSessionService();

}