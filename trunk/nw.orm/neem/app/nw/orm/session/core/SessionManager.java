package nw.orm.session.core;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import nw.commons.NeemClazz;

public class SessionManager extends NeemClazz implements ISessionManager{
	
	private HibernateConfiguration conf;
	
	/**
	 * Default flush mode
	 */
	private FlushMode flushMode = FlushMode.AUTO;
	
	/**
	 * Configures the system use currentSession instead of opening a new session each time
	 */
	private boolean useCurrentSession = false;
	
	/**
	 * Enables the use of transaction manager
	 */
	private boolean useTransactions = true;
	
	public SessionManager(HibernateConfiguration conf) {
		this.conf = conf;
	}

	@Override
	public Session getManagedSession() {
		if(useCurrentSession){
			return getCurrentSession();
		}
		return getRawSession();
	}

	@Override
	public Session getRawSession() {
		SessionFactory sf = conf.getSessionFactory();
		Session sxn = sf.openSession();
		sxn.setFlushMode(flushMode);
		beginTransaction(sxn);
		return sxn;
	}
	
	public Session getCurrentSession() {
		SessionFactory sf = conf.getSessionFactory();
		Session sxn = sf.getCurrentSession();
		sxn.setFlushMode(flushMode);
		beginTransaction(sxn);
		return sxn;
	}

	@Override
	public void closeSession(Session sxn) {
		if ((sxn != null) && (!this.useCurrentSession)){
			sxn.close();
		}
	}

	@Override
	public void commit(Session sxn) throws HibernateException{
		logger.info("Commit in progress ");
		if(useTransactions()){
			sxn.getTransaction().commit();
		}
	}
	
	public void rollback(Session sxn) throws HibernateException{
		logger.info("Rollback in progress ");
		if(useTransactions()){
			sxn.getTransaction().rollback();
		}
	}
	
	@Override
	public StatelessSession getStatelessSession() {
		SessionFactory sf = conf.getSessionFactory();
		StatelessSession ss = sf.openStatelessSession();
		if(useTransactions()){
			ss.beginTransaction();
		}
		return ss;
	}
	
	public SessionFactory getFactory() {
		return conf.getSessionFactory();
	}
	
	private void beginTransaction(Session sxn){
		if(useTransactions()){
			sxn.beginTransaction();
		}
	}

	public void useCurrentSession(boolean useCurrentSession) {
		this.useCurrentSession = useCurrentSession;
	}

	public boolean useTransactions() {
		return useTransactions;
	}

	public void setUseTransactions(boolean useTransactions) {
		this.useTransactions = useTransactions;
	}

}
