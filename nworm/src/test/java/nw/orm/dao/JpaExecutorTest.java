package nw.orm.dao;

import static org.junit.Assert.*;
import static nw.orm.core.query.QueryParameter.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import nw.orm.entity.Person;
import nw.orm.entity.Sex;
import nw.orm.jpa.JpaDaoFactory;

public class JpaExecutorTest {
	
	private JpaDaoFactory jpaService;

	@Before
	public void init(){
		jpaService = new JpaDaoFactory("nworm", false);
		jpaService.init();
		
		Dao<Person> dao = jpaService.getDao(Person.class);
		List<Person> persons = Arrays.asList(new Person("Samson", 10, Sex.MALE), 
				new Person("Hello Deals2", 20, Sex.FEMALE), new Person("PHello3", 14, Sex.FEMALE));
		
		dao.bulkSave(persons);
	}

	@Test
	public void testQueryTransform() {
		
		QueryExecutor e = jpaService.getExecutor();
		Person person = e.query("FROM Person p WHERE p.sex = :sex").bind(param("sex", Sex.MALE)).get(Person.class);
		assertEquals(10, person.getAge());
	}
	
	@Test
	public void testQueryScalar() {
		
		QueryExecutor e = jpaService.getExecutor();
		Long ages = e.query("SELECT SUM(age) FROM Person p WHERE p.sex = :sex").bind(param("sex", Sex.FEMALE)).get(Long.class);
		assertEquals(new Long(34), ages);
	}

	@Test
	public void testQueryListTransform() {
		
		QueryExecutor e = jpaService.getExecutor();
		List<Person> persons = e.query("FROM Person p WHERE p.sex = :sex").bind(param("sex", Sex.FEMALE)).list(Person.class);
		assertEquals(2, persons.size());
	}
	
	@Test
	public void testQueryListTransformScalar() {
		
		QueryExecutor e = jpaService.getExecutor();
		List<Integer> persons = e.query("SELECT age FROM Person p WHERE p.sex = :sex").bind(param("sex", Sex.FEMALE)).list(Integer.class);
		assertEquals(2, persons.size());
	}
	
	@Test
	public void testQueryNative() {
		QueryExecutor e = jpaService.getExecutor();
		Person person = e.nativeQuery("SELECT * From PERSON p WHERE p.sex = ?1").bind(param("1", Sex.MALE.toString())).get(Person.class);
		assertEquals(10, person.getAge());
	}
	
	@Test
	public void testQueryNativeScalar() {
		QueryExecutor e = jpaService.getExecutor();
		Integer age = e.nativeQuery("SELECT age From Person p WHERE p.sex = ?").bind(param("1", Sex.MALE.toString())).get(Integer.class);
		assertEquals(new Integer(10), age);
	}
	
	@Test
	public void testQueryListNativeScalar() {
		QueryExecutor e = jpaService.getExecutor();
		List<String> age = e.nativeQuery("SELECT full_name From Person").list(String.class);
		System.out.println(age);
		assertEquals(3, age.size());
	}

}
