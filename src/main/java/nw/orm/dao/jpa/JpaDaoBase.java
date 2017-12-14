package nw.orm.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class JpaDaoBase {
	
	protected EntityManagerFactory em;
	
	public JpaDaoBase(EntityManagerFactory em) {
		this.em = em;
	}
	
	/**
	 * Creates an EntityManager instance and begins a transaction
	 * @return EntityManager instance with an active transaction
	 */
	protected EntityManager getEntityManager() {
		EntityManager mgr = em.createEntityManager();
		mgr.getTransaction().begin();
		return mgr;
	}
	
	/***
	 * Commits and closes a transaction
	 * @param mgr
	 */
	public void close(EntityManager mgr) {
		mgr.getTransaction().commit();
		mgr.clear();
	}

}
