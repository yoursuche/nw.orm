package nw.orm.core.session;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import nw.commons.NeemClazz;

/**
 * An entry point for manipulating hibernate sessions and session factory.
 *
 * The default configuration disables use of container managed sessions (current session) while enabling user managed transactions.
 * Nw.orm still has the ability to manage opening and closing of sessions implicitly.
 *
 * @author Ogwara O. Rowland
 */
public class HibernateSessionService extends NeemClazz implements IHibernateSessionService{

	/** The Hibernate Session Factory reference */
	private HibernateSessionFactory conf;

	/** Default flush mode. */
	private FlushMode flushMode = FlushMode.COMMIT;

	/** Configures the system use currentSession instead of opening a new session each time.
	 * true uses currentSession  bound to context
	 *
	 */
	private boolean useCurrentSession = false;

	/** Whether to use JTA or Local transactions
	 * true for Local transactions, false for JTA based transactions
	 */
	private boolean useTransactions = true;

	/**
	 * Instantiates a new hibernate session service.
	 *
	 * @param conf Hibernate Session Factory
	 */
	public HibernateSessionService(HibernateSessionFactory conf) {
		this.conf = conf;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#getManagedSession()
	 */
	@Override
	public Session getManagedSession() {
		if(useCurrentSession){
			return getCurrentSession();
		}
		return getRawSession();
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#getRawSession()
	 */
	@Override
	public Session getRawSession() {
		SessionFactory sf = conf.getSessionFactory();
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
		SessionFactory sf = conf.getSessionFactory();
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
		if ((sxn != null) && (!this.useCurrentSession)){
			sxn.close();
		}
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#commit(org.hibernate.Session)
	 */
	@Override
	public void commit(Session sxn) throws HibernateException{
		logger.trace("Commit in progress ");
		if(useTransactions()){
			sxn.getTransaction().commit();
		}
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#rollback(org.hibernate.Session)
	 */
	@Override
	public void rollback(Session sxn) throws HibernateException{
		logger.trace("Rollback in progress ");
		if(useTransactions()){
			sxn.getTransaction().rollback();
		}
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#getStatelessSession()
	 */
	@Override
	public StatelessSession getStatelessSession() {
		SessionFactory sf = conf.getSessionFactory();
		StatelessSession ss = sf.openStatelessSession();
		if(useTransactions()){
			ss.beginTransaction();
		}
		return ss;
	}

	/* (non-Javadoc)
	 * @see nw.orm.core.session.IHibernateSessionService#getFactory()
	 */
	@Override
	public SessionFactory getFactory() {
		return conf.getSessionFactory();
	}

	/**
	 * Begin transaction.
	 *
	 * @param sxn the sxn
	 */
	private void beginTransaction(Session sxn){
		if(useTransactions()){
			sxn.beginTransaction();
		}
	}

	/**
	 * Enable current session.
	 */
	public void enableCurrentSession(){
		this.useCurrentSession = true;
	}

	/**
	 * Enable transactions.
	 */
	public void enableTransactions(){
		this.useTransactions = true;
	}

	/**
	 * Disable current session.
	 */
	public void disableCurrentSession(){
		this.useCurrentSession = false;
	}

	/**
	 * Disable transactions.
	 */
	public void disableTransactions(){
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
