package nw.orm.core.service;

import java.util.Properties;

import javax.naming.OperationNotSupportedException;

import org.hibernate.SessionFactory;

import nw.orm.hibernate.HibernateDaoFactory;

/**
 * This represents a single interface for querying the database using hibernate.
 * It is just a wrapper around a few of hibernate's query methods. Each database
 * configuration file maps to a single instance of this class. For Example:
 * calling <code>
 * Nworm.getInstance()
 * </code> twice or more will return the same object
 *
 * @author Ogwara O. Rowland
 * @version 0.7
 *
 *
 */
public class Nworm extends NwormImpl {

    /**
     * Target hibernate configuration used to connect to the database
     */
    private String configFile;

    /**
     * Creates or return existing Entity Manager using default configuration
     * file name hibernate.cfg.xml
     *
     * @return a single database service instance
     */
    public static Nworm getInstance() {
        String configFile = "hibernate.cfg.xml";
        return getInstance(configFile);
    }

    /**
     * Gets the single instance of Nworm.
     *
     * @param configFile Hibernate configuration file name to be used for this
     * connection. The file name is case sensitive only for case sensitive file
     * system
     * @return a single database service instance
     */
    public static Nworm getInstance(String configFile) {
        Nworm service = null;
        try {
            service = getInstance(configFile, null);
            return service;
        } catch (OperationNotSupportedException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Gets the single instance of Nworm.
     *
     * @param configFile Hibernate configuration file name to be used for this
     * connection. The file name is case sensitive only for case sensitive file
     * system
     * @param props Extra configuration parameters. Useful in cases where
     * modification of some properties from an exisiting config is needed It
     * must contain a property named config.name
     * @return a single database service instance
     * @throws OperationNotSupportedException the operation not supported
     * exception
     */
    public static Nworm getInstance(String configFile, Properties props) throws OperationNotSupportedException {
        return getInstance(configFile, props, false, false);
    }

    public static Nworm getInstance(String configFile, Properties props, boolean useJta, boolean useCureentSession) throws OperationNotSupportedException {
        Nworm service = null;
        if (props != null) {
            String cname = props.getProperty("config.name");
            if (cname == null || (cname != null && cname.isEmpty())) {
                throw new OperationNotSupportedException(
                        "A Property named config.name must be specified in this property object");
            }
            service = (Nworm) getManager(configFile + "_" + cname);
        } else {
            service = (Nworm) getManager(configFile);
        }

        if (service == null) {
            synchronized (Nworm.class) {
                service = new Nworm();
                service.setConfigFile(configFile);
                service.init(configFile, props, useJta, useCureentSession);
            }
        }
        
        return service;
    }

    /**
     * Instantiates a new nworm.
     */
    private Nworm() {
        // do nothing
    }

    /**
     * Inits the.
     *
     * @param configFile the config file
     * @param props the props
     * @throws OperationNotSupportedException
     */
    private void init(String configFile, Properties props, boolean useJta, boolean useCureentSession) throws OperationNotSupportedException {

        HibernateDaoFactory daoFactory = new HibernateDaoFactory(configFile, props, useJta, useCureentSession);
        daoFactory.init();

        this.factory = daoFactory;
        if (props == null) {
            putManager(configFile, this);
        } else {
            putManager(configFile + "_" + props.getProperty("config.name"), this);
        }
    }

    /**
     * Log.
     *
     * @param msg the msg
     */
    public void log(String msg) {
        logger.info(msg);
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getConfigFile() {
        return configFile;
    }

    /**
     * Close factory.
     */
    public void closeFactory() {
        this.factory.clean();
    }

    /**
     * Used to reinitialize the session factory
     *
     * @param props config pros
     * @throws OperationNotSupportedException when something goes wrong
     */
    public void reInitialize(Properties props) throws OperationNotSupportedException {
        closeFactory();
        init(getConfigFile(), props, false, false);
    }

    public void reInitialize(Properties props, boolean useJta, boolean useCureentSession) throws OperationNotSupportedException {
        closeFactory();
        init(getConfigFile(), props, useJta, useCureentSession);
    }

    /**
     * Enables jta by disabling all references to transactions. Its expected
     * that starting and controlling the transaction will be controlleed by the
     * user
     */
    public void enableJTA() {
        this.factory.setJtaEnabled(true);
    }

    /**
     * Disables jta by enabling all references to transactions. Its expected
     * that starting and controlling the transaction will be controlleed by
     * nworm
     */
    public void disableJTA() {
        this.factory.setJtaEnabled(false);
    }

    /**
     * Enables the use of current session from session actory
     */
    public void enableSessionByContext() {
        this.factory.setCurrentSessionEnabled(true);
    }

    /**
     * Disables the use of current session, all new session will call
     * openSession
     */
    public void disableSessionByContext() {
        this.factory.setCurrentSessionEnabled(false);
    }

    public SessionFactory getHibernateSessionFactory() {
        return this.factory.getSessionFactory();
    }

}
