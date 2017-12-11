package nw.orm.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import nw.orm.dao.Dao;
import nw.orm.dao.DaoFactory;

public class JpaDaoFactory implements DaoFactory {
	
	private String unitName;
	private EntityManagerFactory em;
	
	public JpaDaoFactory(String unitName) {
		this.unitName = unitName;
	}

	@Override
	public void init() {
		em = Persistence.createEntityManagerFactory(this.unitName);
		
	}

	@Override
	public <T> Dao<T> getGenericDao(Class<T> clazz) {
		return new JpaDao<T>(em, clazz);
	}
	
	@Override
	public void clean() {
		em.close();
	}

}
