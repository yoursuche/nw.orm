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
	public void testGenericUpdate() {
		
		Dao<Person> dao = jpaService.getGenericDao(Person.class);
		
		// create test user
		Person person = new Person();
		person.setAge(23);
		person.setFullName("Updated Google Eyes");
		person.setSex(Sex.MALE);
		dao.save(person);
		assertNotNull(person.getPk());
		
		// update
		person.setSex(Sex.FEMALE);
		dao.update(person);
		
		// find and validate
		Person np = dao.getById(person.getPk());
		assertNotNull(np);
		assertEquals(np.getSex(), Sex.FEMALE);
	}
	
	@Test
	public void testGenericDelete() {
		
		Dao<Person> dao = jpaService.getGenericDao(Person.class);
		
		// create test user
		Person person = new Person();
		person.setAge(123);
		person.setFullName("Deletable Google Eyes");
		person.setSex(Sex.MALE);
		dao.save(person);
		assertNotNull(person.getPk());
		
		// find and validate
		Person np = dao.getById(person.getPk());
		dao.delete(np);
		
		Person np2 = dao.getById(person.getPk());
		assertNull(np2);
	}
	
	@Test
	public void testGenericDeleteById() {
		
		Dao<Person> dao = jpaService.getGenericDao(Person.class);
		
		// create test user
		Person person = new Person();
		person.setAge(1233);
		person.setFullName("ID Deletable Google Eyes");
		person.setSex(Sex.MALE);
		dao.save(person);
		assertNotNull(person.getPk());
		
		// delete by id
		dao.deleteById(person.getPk());
		
		Person np2 = dao.getById(person.getPk());
		assertNull(np2);
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
	public void list(){
		fail("Not Implemented");
	}
	
	@AfterClass
	public static void close(){
		jpaService.clean();;
	}

}
