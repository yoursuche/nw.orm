package nw.orm.dao;

/**
 * Factory for creating Data Access Objects
 * @author Ogwara O. Rowland
 *
 */
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
	 * Generic DAO for entity clazz
	 * @param clazz target class for DAO
	 * @param <T> gen class
	 * @return a generic DAO associated with provided class
	 */
	<T> Dao<T> getDao(Class<T> clazz);
	
	/**
	 * Retrieves a Data Access Object not associated to 
	 * a specific class
	 * @return An appropriate {@link QueryExecutor} implementation
	 */
	QueryExecutor getExecutor();

}
