package nw.orm.dao;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import nw.orm.dao.hibernate.HibernateDaoFactory;
import nw.orm.entity.Person;
import nw.orm.entity.Sex;

public class HibernateDaoTest {
	
	private static DaoFactory factory;
	private static DaoFactory sFactory;
	
	@BeforeClass
	public static void init() {
		
		factory = new HibernateDaoFactory();
		factory.init();
		
		sFactory = new HibernateDaoFactory("test_im.cfg.xml", false, false);
		sFactory.init();
	}
	
	@Test
	public void testGenericDaoSave() {
		
		Person person = new Person();
		person.setAge(23);
		person.setFullName("Google Eyes");
		person.setSex(Sex.FEMALE);
		Dao<Person> dao = factory.getGenericDao(Person.class);
		dao.save(person);
		
		assertNotNull(person.getPk());
		
	}
	
	@Test
	public void testGenericDaoUpdate() {
		
		Person person = new Person();
		person.setAge(23);
		person.setFullName("Google Eyes");
		person.setSex(Sex.FEMALE);
		Dao<Person> dao = factory.getGenericDao(Person.class);
		dao.save(person);
		
		assertNotNull(person.getPk());
		
	}
	
	@AfterClass
	public static void close() {
		factory.clean();
		sFactory.clean();
		
	}

}
