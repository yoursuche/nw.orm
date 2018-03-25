package nw.orm.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nw.orm.dao.DaoFactory;
import nw.orm.dao.QueryDao;

/**
 * Concrete {@link DaoFactory} implementation based
 * JPA 2.1. Each factory instance maps to a persistent unit, create 
 * more instance if connecting to multiple persistent unit is needed
 * 
 * @author Ogwara O. Rowland
 *
 */
public class JpaDaoFactory implements DaoFactory {
	
	private String unitName;
	private EntityManagerFactory em;
	private boolean useManagedTransaction;
	
	/**
	 * Creates a factory using the provided unit name
	 * @param unitName persistent unit name
	 */
	public JpaDaoFactory(String unitName, boolean useManagedTransaction) {
		assert unitName != null;
		this.unitName = unitName;
		this.useManagedTransaction = useManagedTransaction;
	}
	
	
	/**
	 * Creates a factory using the provided EntityManagerFactory,
	 * best for cases where EntityManagerFactory is managed
	 * externally
	 * @param em EntityManagerFactory
	 */
	public JpaDaoFactory(EntityManagerFactory em, boolean useManagedTransaction) {
		this.em = em;
		this.useManagedTransaction = useManagedTransaction;
	}

	@Override
	public void init() {
		if(em == null) {
			em = Persistence.createEntityManagerFactory(this.unitName);
		}
	}

	@Override
	public <T> JDao<T> getDao(Class<T> clazz) {
		return new JpaDao<T>(em, clazz, useManagedTransaction);
	}
	
	@Override
	public void clean() {
		if(em != null && em.isOpen()){
			em.close();
		}
	}

	@Override
	public QueryDao getQueryDao() {
		return new JpaQueryDao(em, useManagedTransaction);
	}

}
