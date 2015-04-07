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

import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.session.HibernateSessionService;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

public interface NwormHibernateService extends NwormService {

	/**
	 * Retrieves a unique entry using the specified criteria.
	 *
	 * @param <T> the generic type
	 * @param paramClass the param class
	 * @param paramArrayOfCriterion the param array of criterion
	 * @return the by criteria
	 */
	public abstract <T> T getByCriteria(Class<T> paramClass,
			Criterion... paramArrayOfCriterion);

	/**
	 * Retrieves a list of entries based on the specified criteria.
	 *
	 * @param <T> the generic type
	 * @param paramClass the param class
	 * @param paramArrayOfCriterion the param array of criterion
	 * @return the list by criteria
	 */
	public abstract <T> List<T> getListByCriteria(Class<T> paramClass,
			Criterion... paramArrayOfCriterion);

	/**
	 * Retrieves an entry based specified HQL and parameters.
	 *
	 * @param <T> the generic type
	 * @param hql HQL for querying
	 * @param paramMap key value parameter map
	 * @param paramClass target entity class
	 * @return A unique entry representing the entity
	 */
	public abstract <T> T getByHQL(String hql, Map<String, Object> paramMap,
			Class<T> paramClass);

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
	public abstract <T> T getByHQL(Class<T> paramClass, String hql,
			QueryParameter... paramArrayOfQueryParameter);

	/**
	 * Retrieves a list based on the specified hql and parameter.
	 *
	 * @param <T> the generic type
	 * @param hql the hql
	 * @param paramMap the param map
	 * @param paramClass the param class
	 * @return the list by hql
	 */
	public abstract <T> List<T> getListByHQL(String hql,
			Map<String, Object> paramMap, Class<T> paramClass);

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
	public abstract <T> List<T> getListByHQL(Class<T> paramClass,
			String paramString, QueryParameter... paramArrayOfQueryParameter);

	/**
	 * Gets the by criteria.
	 *
	 * @param <T> the generic type
	 * @param returnClazz the return clazz
	 * @param qm the qm
	 * @param criterion the param array of criterion
	 * @return the by criteria
	 */
	public abstract <T> T getByCriteria(Class<T> returnClazz, QueryModifier qm,
			Criterion... criterion);

	/**
	 * Gets the list by criteria.
	 *
	 * @param <T> the generic type
	 * @param returnClazz the return clazz
	 * @param qm the qm
	 * @param paramArrayOfCriterion the param array of criterion
	 * @return the list by criteria
	 */
	public abstract <T> List<T> getListByCriteria(Class<T> returnClazz,
			QueryModifier qm, Criterion... paramArrayOfCriterion);

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