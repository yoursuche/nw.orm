package nw.orm.core.session;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import nw.commons.NeemClazz;

/**
 * 
 * @author Ogwara O. Rowland
 *
 */
public class HibernateSessionService extends NeemClazz implements IHibernateSessionService{
	
	private HibernateSessionFactory conf;
	
	/**
	 * Default flush mode
	 */
	private FlushMode flushMode = FlushMode.COMMIT;
	
	/**
	 * Configures the system use currentSession instead of opening a new session each time
	 */
	private boolean useCurrentSession = false;
	
	/**
	 * Enables the use of transaction manager
	 */
	private boolean useTransactions = true;
	
	public HibernateSessionService(HibernateSessionFactory conf) {
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
	
	@Override
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
		logger.trace("Commit in progress ");
		if(useTransactions()){
			sxn.getTransaction().commit();
		}
	}
	
	@Override
	public void rollback(Session sxn) throws HibernateException{
		logger.trace("Rollback in progress ");
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
	
	@Override
	public SessionFactory getFactory() {
		return conf.getSessionFactory();
	}
	
	private void beginTransaction(Session sxn){
		if(useTransactions()){
			sxn.beginTransaction();
		}
	}
	
	public void enableCurrentSession(){
		this.useCurrentSession = true;
	}
	
	public void enableTransactions(){
		this.useTransactions = true;
	}
	
	public void disableCurrentSession(){
		this.useCurrentSession = false;
	}
	
	public void disableTransactions(){
		this.useTransactions = false;
	}

	public boolean useTransactions() {
		return useTransactions;
	}

}
