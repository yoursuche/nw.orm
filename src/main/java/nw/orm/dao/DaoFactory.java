package nw.orm.dao;

public interface DaoFactory {
	
	/**
	 * Initializes factory
	 */
	void init();
	
	/**
	 * Cleans factory before closing
	 */
	void clean();
	
	/**
	 * Generic DAO based on clazz
	 * @param clazz target class for DAO
	 * @return a generic DAO associated with provided class
	 */
	<T> Dao<T> getGenericDao(Class<T> clazz);

}
