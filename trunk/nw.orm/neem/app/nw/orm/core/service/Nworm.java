package nw.orm.core.service;

import java.util.List;
import java.util.Properties;

import javax.naming.OperationNotSupportedException;

import nw.orm.core.RAudit;
import nw.orm.core.session.HibernateSessionFactory;
import nw.orm.core.session.HibernateSessionService;
import nw.orm.examples.model.Person;

/**
 * Neemworks Limited Database.
 * Transaction processing using HQL, and criteria.
 *
 * @author Ogwara O. Rowland (r.ogwara@nimworks.com)
 * @version 0.6
 * @since 6th Nov, 2013
 *
 *
 */
public class Nworm extends NwormImpl {
	
	/**
	 * Creates and Entity Manager using default configuration file name hibernate.cfg.xml
	 * @return a single database service instance
	 */

	public static Nworm getInstance() {
		String configFile = "hibernate.cfg.xml";
		return getInstance(configFile);
	}
	
	/**
	 *@param configFile
	 *		Hibernate configuration file name to be used for this connection. The file name
	 *		is case sensitive only for case sensitive file system
	 * @return a single database service instance
	 */

	public static Nworm getInstance(String configFile) {
		Nworm service = null;
		try {
			service = getInstance(configFile, null);
		} catch (OperationNotSupportedException e) {
			se(Nworm.class, "Exception ", e);
		}
		return service;
	}
	
	/**
	 *@param configFile
	 *		Hibernate configuration file name to be used for this connection. The file name
	 *		is case sensitive only for case sensitive file system
	 *@param props
	 *		Extra configuration parameters. Useful in cases where modification of some properties from an exisiting config is needed
	 *		It must contain a property named config.name
	 * @return a single database service instance
	 * @throws OperationNotSupportedException
	 */

	public static Nworm getInstance(String configFile, Properties props) throws OperationNotSupportedException {
		Nworm service = null;
		if(props != null){
			String cname = props.getProperty("config.name");
			if(cname == null || (cname != null && cname.isEmpty())){
				throw new OperationNotSupportedException("A Property named config.name must be specified in this property object");
			}
			service = (Nworm) getManager(configFile + "_" + cname);
		}else{
			service = (Nworm) getManager(configFile);
		}
		if (service == null) {
			synchronized (Nworm.class) {
				service = new Nworm();
				service.init(configFile, props);
				if(!service.isInitializedSuccessfully()){
					throw new OperationNotSupportedException("Initialization of the configuration was unsuccessful.");
				}
			}
		}
		return service;
	}

	private Nworm() {
		// do nothing
	}
	
	private void init(String configFile, Properties props){
		conf = new HibernateSessionFactory();
		try {
			conf.init(props, configFile);
			sxnManager = new HibernateSessionService(conf);
			setInitializedSuccessfully(true);
		} catch (Exception e) {
			logger.error("Exception ", e);
			setInitializedSuccessfully(false);
		}
		if(props == null){
			putManager(configFile, this);
		}else{
			putManager(configFile + "_" + props.getProperty("config.name"), this);
		}
	}

	public void log(String msg) {
		logger.info(msg);
	}

	public void closeFactory(){
		if(sxnManager.getFactory() != null){
			sxnManager.getFactory().close();
		}
	}

	public static void main(String[] args) {
		RAudit ra = new RAudit();
		ra.setAuditData("Test Audit");
		ra.setActive(false);
		System.out.println(ra.toString());
		
		Nworm dbService = Nworm.getInstance();
		Person pa = dbService.getById(Person.class, 9L);
		System.out.println(pa);
		
		Person p = new Person();
		p.setFullName("OROWLAND");
		p.setAge(23);
		
		dbService.createOrUpdate(p);
		
		List<Person> listByHQL = dbService.getListByHQL(Person.class, "FROM Person");
		System.out.println(listByHQL);
	}

}
