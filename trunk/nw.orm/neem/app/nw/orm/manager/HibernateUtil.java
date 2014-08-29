package nw.orm.manager;

import java.util.Properties;


import nw.commons.NeemClazz;
import nw.orm.base.BaseInterceptor;
import nw.orm.base.contract.IHibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author Ogwara O. Rowland (r.ogwara@nimworks.com)
 * @version 0.2
 * @since 11th May, 2013
 *
 *
 */

class HibernateUtil extends NeemClazz implements IHibernateUtil{

	private Configuration activeConfiguration;
	private Properties hibernateProps;
	private String extraPackages;
	private SessionFactory sessionFactory;

	private String configFilename = "hibernte.cfg.xml";
	
	public HibernateUtil() {
		// do nothing
	}

	public void init(String configFile) {
		this.configFilename = configFile;
		sessionFactory = buildSessionFactory();
	}

	public void init(Properties props, String configFile) {
		hibernateProps = props;
		this.configFilename = configFile;
		sessionFactory = buildSessionFactory();
	}

	public void init(Properties props, String packageName, String configFile) {
		this.hibernateProps = props;
		this.extraPackages = packageName;
		this.configFilename = configFile;
		sessionFactory = buildSessionFactory();
	}

	public SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from configFilename(default=hibernate.cfg.xml)
			activeConfiguration = new Configuration().setInterceptor(new BaseInterceptor());
			activeConfiguration.configure(configFilename);
			if (hibernateProps != null) {
				hibernateProps.remove("config.name");
				activeConfiguration.addProperties(hibernateProps);
			}
			if (extraPackages != null) {
				activeConfiguration.addPackage(extraPackages);
			}
			
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(activeConfiguration.getProperties())
					.buildServiceRegistry();
			return activeConfiguration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
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

	public void setActiveConfiguration(Configuration activeConfiguration) {
		this.activeConfiguration = activeConfiguration;
	}
}
