/* 
 * Copyright 2013 - 2015, Neemworks Nigeria <nw.orm@nimworks.com>
 Permission to use, copy, modify, and distribute this software for any
 purpose with or without fee is hereby granted, provided that the above
 copyright notice and this permission notice appear in all copies.

 THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package nw.orm.core.session;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

// TODO: Auto-generated Javadoc
/**
 * The Interface IHibernateSessionService.
 *
 * @author Ogwara O. Rowland
 */
public interface IHibernateSessionService {
	
	/**
	 * Returns a usable session object for database operations. The outcome depends on whether use of current session is enabled
	 * @return currentSession if enable, else returns a new session using the openSession statement
	 */
	public Session getManagedSession();
	
	/**
	 * Retrieves a new database session.
	 *
	 * @return the raw session
	 */
	public Session getRawSession();
	
	/**
	 * Retrieves the current session specifically.
	 *
	 * @return the current session
	 */
	public Session getCurrentSession();
	
	/**
	 * Controls closing of database sessions when required.
	 *
	 * @param sxn the sxn
	 * @throws HibernateException the hibernate exception
	 */
	public void closeSession(Session sxn) throws HibernateException;
	
	/**
	 * Commit.
	 *
	 * @param sxn the sxn
	 * @throws HibernateException the hibernate exception
	 */
	public void commit(Session sxn) throws HibernateException;
	
	/**
	 * Rollback.
	 *
	 * @param sxn the sxn
	 */
	public void rollback(Session sxn);
	
	/**
	 * Gets the stateless session.
	 *
	 * @return the stateless session
	 */
	public StatelessSession getStatelessSession();
	
	/**
	 * Gets the factory.
	 *
	 * @return the factory
	 */
	public SessionFactory getFactory();

}
