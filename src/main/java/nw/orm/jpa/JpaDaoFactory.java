package nw.orm.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nw.orm.dao.DaoFactory;
import nw.orm.dao.GenericQueryDao;

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
	 * @param isManagedTransaction True for externally managed transaction
	 * @param unitName persistent unit name
	 */
	public JpaDaoFactory(boolean isManagedTransaction, String unitName) {
		assert unitName != null;
		this.unitName = unitName;
		this.useManagedTransaction = isManagedTransaction;
	}
	
	
	/**
	 * Creates a factory using the provided EntityManagerFactory,
	 * best for cases where EntityManagerFactory is managed
	 * externally
	 * @param em externally provided factory
	 * @param isManagedTransaction True for externally managed transaction
	 */
	public JpaDaoFactory(EntityManagerFactory em, boolean isManagedTransaction) {
		this.em = em;
		this.useManagedTransaction = isManagedTransaction;
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
	public GenericQueryDao getGenericQueryDao() {
		// TODO Auto-generated method stub
		return null;
	}

}