package nw.orm.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nw.orm.entity.Person;
import nw.orm.entity.Sex;
import nw.orm.jpa.JDao;
import nw.orm.jpa.JpaDaoFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class JpaDaoTest {

	private static JpaDaoFactory jpaService;

	@BeforeClass
	public static void init(){
		jpaService = new JpaDaoFactory(false, "nworm");
		jpaService.init();
		
		Dao<Person> dao = jpaService.getDao(Person.class);
		List<Person> persons = Arrays.asList(new Person("Hello1", 10, Sex.FEMALE), 
				new Person("Hello Deals2", 20, Sex.FEMALE), new Person("PHello3", 14, Sex.FEMALE));
		
		dao.bulkSave(persons);
	}
	
	@Test
	public void testGenericSave() {
		
		Person person = new Person();
		person.setAge(23);
		person.setFullName("Google Eyes");
		person.setSex(Sex.FEMALE);
		Dao<Person> dao = jpaService.getDao(Person.class);
		dao.save(person);
		
		assertNotNull(person.getPk());
	}
	
	@Test
	public void testGet() {
		
		JDao<Person> dao = jpaService.getDao(Person.class);
		
		CriteriaBuilder cb = dao.getCriteriaBuilder();
		CriteriaQuery<Person> query = cb.createQuery(Person.class);
		Root<Person> person = query.from(Person.class);
		
//		Predicate.BooleanOperator.
////		query.where(cb.equal(Restr, y)
//		
//		dao.bulkSave(persons);
		
	}
	
	@Test
	public void testBulkSave() {
		
		Dao<Person> dao = jpaService.getDao(Person.class);
		List<Person> persons = Arrays.asList(new Person("Hello", 10, Sex.FEMALE), 
				new Person("Hello Deals", 20, Sex.FEMALE), new Person("PHello", 14, Sex.FEMALE));
		
		dao.bulkSave(persons);
		
	}
	
	@Test
	public void testGenericUpdate() {
		
		Dao<Person> dao = jpaService.getDao(Person.class);
		
		// create test user
		Person person = new Person();
		person.setAge(23);
		person.setFullName("Updated Google Eyes");
		person.setSex(Sex.MALE);
		dao.save(person);
		assertNotNull(person.getPk());
		
		// update
		person.setSex(Sex.FEMALE);
		person.setAge(33);
		dao.update(person);
		
		// find and validate
		Person np = dao.getById(person.getPk());
		assertNotNull(np);
		assertEquals(np.getSex(), Sex.FEMALE);
		assertEquals(np.getAge(), 33);
	}
	
	@Test
	public void testGenericDelete() {
		
		Dao<Person> dao = jpaService.getDao(Person.class);
		
		// create test user
		Person person = new Person();
		person.setAge(123);
		person.setFullName("Deletable Google Eyes");
		person.setSex(Sex.MALE);
		dao.save(person);
		assertNotNull(person.getPk());
		
		// find and validate
		Person np = dao.getById(person.getPk());
		assertEquals(np.getPk(), person.getPk());
		dao.delete(np);
		
		Person np2 = dao.getById(person.getPk());
		assertNull(np2);
	}
	
	@Test
	public void testGenericDeleteById() {
		
		Dao<Person> dao = jpaService.getDao(Person.class);
		
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
		Dao<Person> dao = jpaService.getDao(Person.class);
		dao.save(person);
		
		Person nPerson = dao.getById(person.getPk());
		assertEquals(person, nPerson);
	}

//	@Test
	public void list(){
		fail("Not Implemented");
	}
	
	@AfterClass
	public static void close(){
		jpaService.clean();;
	}

}
