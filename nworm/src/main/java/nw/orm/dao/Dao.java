package nw.orm.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Provides basic a generic set of operations applicable to 
 * any given entity
 * 
 * @author Ogwara O. Rowland
 *
 * @param <T> Entity class type
 */
public interface Dao<T> {
	
	T getById(Serializable id);
	
	T save(T entity);
	
	T update(T entity);
	
	boolean saveOrUpdate(T entity);
	
	void delete(T entity);
	
	void deleteById(Serializable pk);
	
	void bulkSave(List<T> entities);
	
	void bulkDelete(List<T> entities);
	
	void bulkIdDelete(List<Serializable> pks);
	
	void softDelete(Serializable id);

	void bulkSoftDelete(List<Serializable> ids);
	
	/**
	 * Lists all soft deleted entities
	 * @param paging pagination object
	 * @return list opf entities
	 */
	List<T> deleted(Paging paging);

}
