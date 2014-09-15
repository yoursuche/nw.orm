package nw.orm.core.session;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

/**
 * 
 * @author Ogwara O. Rowland
 *
 */
public interface IHibernateSessionService {
	
	/**
	 * Returns a usable session object for database operations. The outcome depends on whether use of current session is enabled
	 * @return currentSession if enable, else returns a new session using the openSession statement
	 */
	public Session getManagedSession();
	
	/**
	 * Retrieves a new database session.
	 * @return
	 */
	public Session getRawSession();
	
	/**
	 * Retrieves the current session specifically
	 * @return
	 */
	public Session getCurrentSession();
	
	/**
	 * Controls closing of database sessions when required
	 */
	public void closeSession(Session sxn) throws HibernateException;
	
	public void commit(Session sxn) throws HibernateException;
	
	public void rollback(Session sxn);
	
	public StatelessSession getStatelessSession();
	
	public SessionFactory getFactory();

}
