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

}
