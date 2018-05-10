package nw.orm.query;

import java.util.List;

import nw.orm.core.query.QueryParameter;

public interface WormQuery {
	
	<T> T get(Class<T> resultClass);
	
	<T> List<T> list(Class<T> resultClass);
	
	WormQuery paginate(int offset, int pageSize);
	
	/**
	 * Binds parameters in query to values
	 * @param parameters key value pairs
	 * @return current WormQuery instance
	 */
	WormQuery bind(QueryParameter ...parameters);

}
