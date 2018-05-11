package nw.orm.dao;

import java.util.List;

import nw.orm.core.query.QueryParameter;
import nw.orm.core.query.SQLModifier;
import nw.orm.query.WormQuery;

/**
 * Generic DAO API
 * @author Rowland
 *
 */
public interface QueryExecutor {

	<T> T query(Class<T> resultClass, String jpql, QueryParameter ... parameters);

	<T> List<T> queryList(Class<T> resultClass, String jpql, QueryParameter ... parameters);

//	<T> T get(Class<T> returnClazz, QueryModifier qm, QueryParameter ... parameters);
//
//	<T> List<T> list(Class<T> returnClazz, QueryModifier qm, QueryParameter ... parameters);
	
	<T> List<T> getBySQL(Class<T> returnClazz, String sql, SQLModifier sqlMod, QueryParameter ... params);
	
	int execute(String shql, QueryParameter ... params);
	
	WormQuery query(String query);

}
