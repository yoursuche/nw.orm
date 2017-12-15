package nw.orm.dao.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nw.orm.dao.Dao;
import nw.orm.dao.DaoFactory;
import nw.orm.dao.GenericQueryDao;

public class JpaDaoFactory implements DaoFactory {
	
	private String unitName;
	private EntityManagerFactory em;
	
	public JpaDaoFactory(String unitName) {
		assert unitName != null;
		this.unitName = unitName;
	}
	
	public JpaDaoFactory(EntityManagerFactory em) {
		this.em = em;
	}

	@Override
	public void init() {
		if(em == null) {
			em = Persistence.createEntityManagerFactory(this.unitName);
		}
	}

	@Override
	public <T> Dao<T> getGenericDao(Class<T> clazz) {
		return new JpaDao<T>(em, clazz);
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
