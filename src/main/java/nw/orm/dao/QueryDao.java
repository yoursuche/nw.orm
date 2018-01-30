package nw.orm.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;

/**
 * Generic DAO API
 * @author Rowland
 *
 */
public interface QueryDao {

	<T> T query(Class<T> resultClass, String queryString, QueryParameter ... parameters);

	<T> List<T> queryList(Class<T> resultClass, String hql, QueryParameter ... parameters);

	<T> T get(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria);

	<T> List<T> list(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria);
	

}
