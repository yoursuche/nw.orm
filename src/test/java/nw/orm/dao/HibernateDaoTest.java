package nw.orm.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import nw.orm.entity.Person;
import nw.orm.entity.Sex;
import nw.orm.hibernate.HibernateDaoFactory;

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
		
		try {
			
			Files.delete(Paths.get(new URI("~/test")));
			Files.delete(Paths.get(new URI("~/test_mn")));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
