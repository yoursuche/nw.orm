package nw.orm.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nw.orm.dao.Dao;
import nw.orm.dao.DaoFactory;
import nw.orm.dao.GenericQueryDao;

public class HibernateDaoFactory implements DaoFactory {
	
	private String resourceName;
	private SessionFactory factory;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private boolean enableJta;
	private boolean useCurrentSession;
	
	/**
	 * Uses hibernate.cfg.xml as resource
	 */
	public HibernateDaoFactory() {
		this("hibernate.cfg.xml", false, false);
	}
	
	/**
	 * Uses hibernate.cfg.xml as resource
	 */
	public HibernateDaoFactory(boolean useCurrentSession) {
		this("hibernate.cfg.xml", false, useCurrentSession);
	}
	
	/**
	 * 
	 * @param resourceName Hibernate configuration file
	 */
	public HibernateDaoFactory(String resourceName, boolean enableJta, boolean useCurrentSession) {
		
		this.enableJta = enableJta;
		this.resourceName = resourceName;
		this.useCurrentSession = useCurrentSession;
	}

	@Override
	public void init() {
		logger.debug("Initializing Hibernate DAO Factory");
		try {
			setUp(this.resourceName);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		
	}

	@Override
	public void clean() {
		logger.debug("Closing Hibernate DAO Factory");
		if(factory != null) {
			factory.close();
		}
		
	}

	@Override
	public <T> Dao<T> getGenericDao(Class<T> clazz) {
		return new HibernateDao<T>(factory, clazz, this.enableJta, this.useCurrentSession);
	}
	
	/**
	 * @see 
	 * <a href='http://docs.jboss.org/hibernate/orm/5.1/quickstart/html_single/#obtaining'>
	 * 		http://docs.jboss.org/hibernate/orm/5.1/quickstart/html_single/#obtaining
	 * </a>
	 * @throws Exception
	 */
	protected void setUp(String resourceName) throws Exception {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure(resourceName) // configures settings from hibernate.cfg.xml
				.build();
		try {
			factory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}

	@Override
	public GenericQueryDao getGenericQueryDao() {
		return new HibernateQueryDao(factory, enableJta, useCurrentSession);
	}

}
