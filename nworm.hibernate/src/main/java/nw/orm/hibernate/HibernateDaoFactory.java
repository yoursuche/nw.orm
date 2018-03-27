package nw.orm.hibernate;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nw.orm.dao.DaoFactory;

public class HibernateDaoFactory implements DaoFactory {
	
	private String resourceName;
	private SessionFactory factory;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private boolean enableJta;
	private boolean useCurrentSession;
	private Properties extraProps;
	
	/**
	 * Uses hibernate.cfg.xml as resource
	 */
	public HibernateDaoFactory() {
		this("hibernate.cfg.xml", new Properties(), false, false);
	}
	
	/**
	 * Uses hibernate.cfg.xml as resource
	 * @param useCurrentSession use hibernate current session flag
	 */
	public HibernateDaoFactory(boolean useCurrentSession) {
		this("hibernate.cfg.xml", new Properties(), false, useCurrentSession);
	}
	
	/**
	 * 
	 * @param resourceName Hibernate configuration file
	 * @param enableJta true for jta use
	 * @param useCurrentSession use hibernate current session flag
	 */
	public HibernateDaoFactory(String resourceName, boolean enableJta, boolean useCurrentSession) {
		this(resourceName, new Properties(), enableJta, useCurrentSession);
	}

	public HibernateDaoFactory(String resourceName, Properties props, boolean enableJta, boolean useCurrentSession) {
		this.extraProps = props;
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
		if(factory != null && !factory.isClosed()) {
			factory.close();
		}
		
	}

	@Override
	public <T> HDao<T> getDao(Class<T> clazz) {
		return new HibernateDao<T>(factory, clazz, this.enableJta, this.useCurrentSession);
	}
	
	/**
	 * @see 
	 * <a href='http://docs.jboss.org/hibernate/orm/5.1/quickstart/html_single/#obtaining'>
	 * 		http://docs.jboss.org/hibernate/orm/5.1/quickstart/html_single/#obtaining
	 * </a>
	 * @param resourceName hibernate config file
	 * @throws Exception throw exception 
	 */
	protected void setUp(String resourceName) throws Exception {
		
		
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.configure(resourceName);
		
		if(extraProps != null)
			builder.applySettings(extraProps); // configures settings from hibernate.cfg.xml
		
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = builder.build();
		try {
			factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
			logger.error("Hibernate Init Exception: ", e);
		}
	}

	@Override
	public HQueryDao getQueryDao() {
		return new HibernateQueryDao(factory, enableJta, useCurrentSession);
	}
	
	public SessionFactory getSessionFactory() {
		return this.factory;
	}
	
	public void setJtaEnabled(boolean enable) {
		this.enableJta = enable;
	}
	
	public void setCurrentSessionEnabled(boolean enable) {
		this.useCurrentSession = enable;
	}

}
