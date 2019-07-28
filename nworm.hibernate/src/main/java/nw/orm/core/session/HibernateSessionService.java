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

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An entry point for manipulating hibernate sessions and session factory.
 *
 * The default configuration disables use of container managed sessions (current
 * session) while enabling user managed transactions. Nw.orm still has the
 * ability to manage opening and closing of sessions implicitly.
 *
 * @author Ogwara O. Rowland
 */
public class HibernateSessionService implements IHibernateSessionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The Hibernate Session Factory reference
     */
    private SessionFactory conf;

    /**
     * Default flush mode.
     */
    private FlushMode flushMode = FlushMode.COMMIT;

    /**
     * Configures the system use currentSession instead of opening a new session
     * each time. true uses currentSession bound to context
     *
     */
    private boolean useCurrentSession = false;

    /**
     * Whether to use JTA or Local transactions true for Local transactions,
     * false for JTA based transactions
     */
    private boolean useTransactions = true;

    /**
     * Instantiates a new hibernate session service.
     *
     * @param conf Hibernate Session Factory
     */
    public HibernateSessionService(SessionFactory conf) {
        this.conf = conf;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#getManagedSession()
     */
    @Override
    public Session getManagedSession() {
        if (useCurrentSession) {
            return getCurrentSession();
        }
        return getRawSession();
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#getRawSession()
     */
    @Override
    public Session getRawSession() {
        SessionFactory sf = conf;
        Session sxn = sf.openSession();
        sxn.setFlushMode(flushMode);
        beginTransaction(sxn);
        return sxn;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#getCurrentSession()
     */
    @Override
    public Session getCurrentSession() {
        SessionFactory sf = conf;
        Session sxn = sf.getCurrentSession();
        sxn.setFlushMode(flushMode);
        beginTransaction(sxn);
        return sxn;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#closeSession(org.hibernate.Session)
     */
    @Override
    public void closeSession(Session sxn) {
        if ((sxn != null) && (!this.useCurrentSession)) {
            sxn.close();
        }
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#commit(org.hibernate.Session)
     */
    @Override
    public void commit(Session sxn) throws HibernateException {
        logger.trace("Commit in progress ");
        if (useTransactions()) {
            sxn.getTransaction().commit();
        }
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#rollback(org.hibernate.Session)
     */
    @Override
    public void rollback(Session sxn) throws HibernateException {
        logger.trace("Rollback in progress ");
        if (useTransactions()) {
            sxn.getTransaction().rollback();
        }
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#getStatelessSession()
     */
    @Override
    public StatelessSession getStatelessSession() {
        SessionFactory sf = conf;
        StatelessSession ss = sf.openStatelessSession();
        if (useTransactions()) {
            ss.beginTransaction();
        }
        return ss;
    }

    /* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#getFactory()
     */
    @Override
    public SessionFactory getFactory() {
        return conf;
    }

    /**
     * Begin transaction.
     *
     * @param sxn the sxn
     */
    private void beginTransaction(Session sxn) {
        if (useTransactions()) {
            sxn.beginTransaction();
        }
    }

    /**
     * Enable current session.
     */
    public void enableCurrentSession() {
        this.useCurrentSession = true;
    }

    /**
     * Enable transactions.
     */
    public void enableTransactions() {
        this.useTransactions = true;
    }

    /**
     * Disable current session.
     */
    public void disableCurrentSession() {
        this.useCurrentSession = false;
    }

    /**
     * Disable transactions.
     */
    public void disableTransactions() {
        this.useTransactions = false;
    }

    /**
     * Use transactions.
     *
     * @return true, if successful
     */
    public boolean useTransactions() {
        return useTransactions;
    }

}
