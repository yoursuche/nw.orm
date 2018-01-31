package nw.orm.core.service;

import java.util.List;
import java.util.Map;

import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.session.HibernateSessionService;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

public interface NwormHibernateService extends NwormService {

	/**
	 * Retrieves a unique entry using the specified criteria.
	 *
	 * @param <T> The target entity type
	 * @param entityClass The target entity class reference
	 * @param criteria query filter criteria
	 * @return the entity instance matching the provided criteria or null if no match is found
	 * @throws NwormQueryException when an error occurs
	 */
	public abstract <T> T getByCriteria(Class<T> entityClass, Criterion... criteria);

	/**
	 * Retrieves a list of entries based on the specified criteria.
	 *
	 * @param <T> The target entity type
	 * @param entityClass The target entity class reference
	 * @param criteria query filter criteria
	 * @return the list of entity instances that match the criteria or an empty list if nothing matches
	 * @throws NwormQueryException when an error occurs
	 */
	public abstract <T> List<T> getListByCriteria(Class<T> entityClass, Criterion... criteria);

	/**
	 * Retrieves an entry based specified HQL and parameters.
	 *
	 * @param <T> The target entity type
	 * @param hql target hql with specified restraictions to retrieve data set
	 * @param paramaeterMap key value parameter map
	 * @param entityClass target entity class
	 * @return A unique entry representing the filtered entity
	 * @throws NwormQueryException when an error occurs
	 */
	public abstract <T> T getByHQL(String hql, Map<String, Object> paramaeterMap, Class<T> entityClass);

	/**
	 * Gets an item using the specified hql string
	 *
	 * @param <T> The target entity type
	 * @param entityClass The target entity class reference
	 * @param hql target hql with specified restraictions to retrieve data set
	 * @param queryParameters array of query parameters defined in the hql
	 * @return A unique entry representing the filtered entity
	 * @throws NwormQueryException when an error occurs
	 * @see #getByHQL(String, Map, Class)
	 */
	public abstract <T> T getByHQL(Class<T> entityClass, String hql, QueryParameter... queryParameters);

	/**
	 * Retrieves a list based on the specified hql and parameter.
	 *
	 * @param <T> The target entity type
	 * @param hql the hql
	 * @param parameterMap the param map
	 * @param entityClass The target entity class reference
	 * @return list of entities matching hql restrictions
	 * @throws NwormQueryException when an error occurs
	 */
	public abstract <T> List<T> getListByHQL(String hql, Map<String, Object> parameterMap, Class<T> entityClass);

	/**
	 * Retrieves a list based on the specified hql and parameter.
	 *
	 * @param <T> The target entity type
	 * @param entityClass The target entity class reference
	 * @param hql target hql with specified restraictions to retrieve data set
	 * @param queryParameters array of query parameters defined in the hql
	 * @return the list by hql
	 * @throws NwormQueryException when an error occurs
	 * @see getListByHQL
	 */
	public abstract <T> List<T> getListByHQL(Class<T> entityClass, String hql, QueryParameter... queryParameters);

	/**
	 * Retrieves a unique entry using the specified criteria.
	 *
	 * @param <T> The target entity type
	 * @param returnClazz the return class reference type
	 * @param qm the query modifier used to garnish the search
	 * @param criterion query filter criteria
	 * @return a unique entry matching the criteria
	 * @throws NwormQueryException when an error occurs
	 */
	public abstract <T> T getByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion... criterion);

	/**
	 * Gets a list using the specified criteria.
	 *
	 *
	 * @param <T> The target entity type
	 * @param returnClazz the return class reference
	 * @param qm the query modifier used to garnish the search
	 * @param criteria query filter criteria
	 * @return the list of entities matching the criteria
	 * @throws NwormQueryException when an error occurs
	 */
	public abstract <T> List<T> getListByCriteria(Class<T> returnClazz, QueryModifier qm, Criterion... criteria);

	/**
	 * Gets the by example.
	 *
	 * @param <T> The target entity type
	 * @param clazz the clazz
	 * @param example the example
	 * @return the by example
	 */
	public abstract <T> T getByExample(Class<T> clazz, Example example);

	/**
	 * Gets the list by example.
	 *
	 * @param <T> The target entity type
	 * @param qm the qm
	 * @param example the example
	 * @return the list by example
	 */
	public abstract <T> List<T> getListByExample(QueryModifier qm,
			Example example);

}