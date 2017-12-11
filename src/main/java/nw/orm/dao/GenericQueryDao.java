package nw.orm.dao;

import java.io.Serializable;
import java.util.List;

import nw.orm.core.exception.NwormQueryException;

public interface GenericQueryDao {
	
	/**
	 * 
	 * @param item
	 * @return primary key
	 * 
	 * @throws NwormQueryException
	 */
	Serializable save(Object item);

	List<Serializable> bulkSave(List<?> items);

	boolean delete(Object entity);

	boolean bulkDelete(List<?> entities);

	boolean update(Object entity);

	boolean bulkUpdate(List<?> entities);

	boolean saveOrUpdate(Object entity);

}
