package nw.orm.dao;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static nw.orm.core.query.QueryParameter.*;

import java.util.Arrays;
import java.util.List;

import nw.orm.entity.Person;
import nw.orm.entity.Sex;
import nw.orm.filters.JpqlFilters;
import nw.orm.jpa.JDao;
import nw.orm.jpa.JpaDaoFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JpaDaoTest {

	private JpaDaoFactory jpaService;

	@Before
	public void init(){
		jpaService = new JpaDaoFactory("nworm", false);
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
	public void testOrFilter() {
		
		JDao<Person> dao = jpaService.getDao(Person.class);
		
		List<Person> persons = dao.filter(null, JpqlFilters.or(JpqlFilters.eq("sex", Sex.MALE), JpqlFilters.eq("age", 20)));
		System.out.println(persons);
		assertThat("must be 1", 1, equalTo(persons.size()));
	}
	
	@Test
	public void testAndFilter() {
		
		JDao<Person> dao = jpaService.getDao(Person.class);
		
		List<Person> persons = dao.filter(null, JpqlFilters.and(JpqlFilters.eq("sex", Sex.MALE), JpqlFilters.eq("age", 20)));
		System.out.println(persons);
		assertThat("must be greater than zero", 0, equalTo(persons.size()));
	}
	
	@Test
	public void testAndOrFilter() {
		
		JDao<Person> dao = jpaService.getDao(Person.class);
		List<Person> persons = dao.filter(null, JpqlFilters.and(JpqlFilters.eq("sex", Sex.MALE), JpqlFilters.eq("age", 20)), 
				JpqlFilters.eq("sex", Sex.MALE), JpqlFilters.eq("age", 20));
		System.out.println(persons);
		assertThat("must be greater than zero", 0, equalTo(persons.size()));
	}
	
	@Test
	public void testBulkSave() {
		
		JDao<Person> dao = jpaService.getDao(Person.class);
		List<Person> persons = Arrays.asList(new Person("Hello", 10, Sex.FEMALE), 
				new Person("Hello Deals", 21, Sex.MALE), new Person("PHello", 14, Sex.FEMALE));
		
		dao.bulkSave(persons);
		Person person = dao.find(param("age", 21));
		assertNotNull(person);
		assertEquals(person.getSex(), Sex.MALE);
		
	}
	
	@Test
	public void testGetAndUpdate() {
		
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
	
	@Test
	public void testSoftDelete() {
		
		Person person = new Person();
		person.setAge(23);
		person.setFullName("Deleted Google Eyes");
		person.setSex(Sex.FEMALE);
		Dao<Person> dao = jpaService.getDao(Person.class);
		dao.save(person);
		
		dao.softDelete(person.getPk());
		Person p2 = dao.getById(person.getPk());
		assertNull(p2);
	}

//	@Test
	public void list(){
		fail("Not Implemented");
	}
	
	@After
	public void close(){
		jpaService.clean();;
	}

}
