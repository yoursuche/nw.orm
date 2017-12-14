package nw.orm.dao;

import static org.junit.Assert.*;

import nw.orm.dao.jpa.JpaDaoFactory;
import nw.orm.entity.Person;
import nw.orm.entity.Sex;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class JpaDaoTest {

	private static DaoFactory jpaService;

	@BeforeClass
	public static void init(){
		jpaService = new JpaDaoFactory("nworm");
		jpaService.init();
	}
	
	@Test
	public void testGenericSave() {
		
		Person person = new Person();
		person.setAge(23);
		person.setFullName("Google Eyes");
		person.setSex(Sex.FEMALE);
		Dao<Person> dao = jpaService.getGenericDao(Person.class);
		dao.save(person);
		
		assertNotNull(person.getPk());
	}

	@Test
	public void testFindById() {
		
		Person person = new Person();
		person.setAge(43);
		person.setFullName("Google Eyes");
		person.setSex(Sex.FEMALE);
		Dao<Person> dao = jpaService.getGenericDao(Person.class);
		dao.save(person);
		
		Person nPerson = dao.getById(person.getPk());
		assertEquals(person, nPerson);
	}

	@Test
	public void testGetAll(){
		fail("Not Implemented");
	}
	
	@AfterClass
	public static void close(){
		jpaService.clean();;
	}

}
