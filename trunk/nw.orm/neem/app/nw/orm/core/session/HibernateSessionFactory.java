package nw.orm.core.session;

import java.util.Properties;

import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nw.commons.NeemClazz;

/**
 * Hibernate SessionFactory builder
 * @author kulgan
 *
 */
public class HibernateSessionFactory extends NeemClazz{
	
	private Properties hibernateProps;
	private SessionFactory sessionFactory;
	
	private String configFilename = "hibernate.cfg.xml";
	private Configuration activeConfiguration;
	
	private Interceptor interceptor;
	
	public void init(Properties props, String configFile) {
		init(props, configFile, null);
	}
	
	public void init(Properties props, String configFile, Interceptor interceptor) {
		this.hibernateProps = props;
		this.configFilename = configFile;
		this.interceptor = interceptor;
		sessionFactory = buildSessionFactory();
	}
	
	private SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from configFilename(default=hibernate.cfg.xml)
			activeConfiguration = new Configuration();
			if(interceptor != null){
				activeConfiguration.setInterceptor(interceptor);
			}
			activeConfiguration.configure(configFilename);
			if (hibernateProps != null) {
				hibernateProps.remove("config.name");
				activeConfiguration.addProperties(hibernateProps);
			}
			
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(activeConfiguration.getProperties())
					.buildServiceRegistry();
			return activeConfiguration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			logger.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public String closeFactory() {
		if (!sessionFactory.isClosed()) {
			sessionFactory.close();
		}
		if(hibernateProps != null){
			configFilename = configFilename + "_" + hibernateProps.getProperty("config.name");
		}
		return configFilename;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Configuration getActiveConfiguration() {
		return activeConfiguration;
	}
	
	public void rebuildConfiguration(){
		//TODO
	}

}
