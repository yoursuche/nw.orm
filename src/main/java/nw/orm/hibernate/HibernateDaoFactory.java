package nw.orm.hibernate;

import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nw.orm.dao.DaoFactory;
import nw.orm.dao.QueryDao;

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

	public HibernateDaoFactory(String configFile, Properties props) {
		this.extraProps = props;
		this.resourceName = configFile;
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
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	protected void setUp(String resourceName) throws Exception {
		
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.configure(resourceName)
				.applySettings(extraProps); // configures settings from hibernate.cfg.xml
				
		Map<String, String> props = builder.getSettings();
		
		String s = props.get("hibernate.current_session_context_class");
		String t = props.get("hibernate.connection.datasource");
		if(s != null) {
			this.useCurrentSession = true;
		}
		
		if(t != null) {
			this.enableJta = true;
		}
		
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = builder.build();
		
		try {
			factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}

	@Override
	public QueryDao getQueryDao() {
		return new HibernateQueryDao(factory, enableJta, useCurrentSession);
	}

}
