package nw.orm.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import nw.orm.entity.Person;
import nw.orm.entity.Sex;
import nw.orm.hibernate.HDao;
import nw.orm.hibernate.HibernateDaoFactory;

public class HibernateDaoTest {
	
	private static HibernateDaoFactory factory;
	private static HibernateDaoFactory sFactory;
	
	@BeforeClass
	public static void init() {
		
		factory = new HibernateDaoFactory();
		factory.init();
		
		sFactory = new HibernateDaoFactory("test_im.cfg.xml", false, false);
		sFactory.init();
		
		Person person = new Person();
		person.setAge(23);
		person.setFullName("Spooky Eyes");
		person.setSex(Sex.FEMALE);
		Dao<Person> dao = factory.getDao(Person.class);
		dao.save(person);
		System.out.println(person.getLastModified().getTime());
	}
	
	@Test
	public void testGenericDaoSave() {
		
		Person person = new Person();
		person.setAge(23);
		person.setFullName("Google Eyes");
		person.setSex(Sex.FEMALE);
		Dao<Person> dao = factory.getDao(Person.class);
		dao.save(person);
		System.out.println(person.getLastModified().getTime());
		assertNotNull(person.getPk());
		
	}
	
	@Test
	public void testGenericDaoUpdate() {
		
		HDao<Person> dao = factory.getDao(Person.class);
		Person person = dao.find(Restrictions.eq("fullName", "Spooky Eyes"));
		Date lm = person.getLastModified();
		assertNotNull(person.getPk());
		
		person.setAge(33);
		dao.update(person);
		assertTrue(person.getLastModified().after(lm));
	}
	
//	@Test
//	public void testGenericDaoUpdate() {
//		
//		Person person = new Person();
//		person.setAge(23);
//		person.setFullName("Google Eyes");
//		person.setSex(Sex.FEMALE);
//		Dao<Person> dao = factory.getDao(Person.class);
//		dao.save(person);
//		
//		assertNotNull(person.getPk());
//		
//	}
	
	@AfterClass
	public static void close() {
		factory.clean();
		sFactory.clean();
		
	}

}
