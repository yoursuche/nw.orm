package nw.orm.session.core;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nw.commons.NeemClazz;
import nw.orm.base.BaseInterceptor;

/**
 * Hibernate configuration helper
 * @author kulgan
 *
 */
public class HibernateConfiguration extends NeemClazz{
	
	private Properties hibernateProps;
	private SessionFactory sessionFactory;
	
	private String configFilename = "hibernate.cfg.xml";
	private Configuration activeConfiguration;
	
	public void init(Properties props, String configFile) {
		this.hibernateProps = props;
		this.configFilename = configFile;
		sessionFactory = buildSessionFactory();
	}
	
	private SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from configFilename(default=hibernate.cfg.xml)
			activeConfiguration = new Configuration().setInterceptor(new BaseInterceptor());
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
			Session currentSession = sessionFactory.getCurrentSession();
			if (currentSession != null) {
				currentSession.cancelQuery();
				currentSession.close();
			}
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
