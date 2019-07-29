package nw.orm.dao;

import java.util.List;

import nw.orm.filters.Filter;

/**
 * Provides basic a generic set of operations applicable to 
 * any given entity
 * 
 * @author Ogwara O. Rowland
 *
 * @param <T> Entity class type
 */
public interface Dao<T> {
	
	T getById(Long id);
	
	T save(T entity);
	
	T update(T entity);
	
	boolean saveOrUpdate(T entity);
	
	void delete(T entity);
	
	void deleteById(Long pk);
	
	void bulkSave(List<T> entities);
	
	void bulkDelete(List<T> entities);
	
	void bulkIdDelete(List<Long> pks);
	
	void softDelete(Long id);

	void bulkSoftDelete(List<Long> ids);
	
	/**
	 * Lists all soft deleted entities
	 * @param paging pagination object
	 * @return list opf entities
	 */
	List<T> deleted(Paging paging);
	
	T select(Filter ... filters);
	
	List<T> filter(Paging paging, Filter ... filters);

}
