/*
 * Copyright 2019 Neemworks Nigeria.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
     * Returns a usable session object for database operations. The outcome
     * depends on whether use of current session is enabled
     *
     * @return currentSession if enable, else returns a new session using the
     * openSession statement
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
