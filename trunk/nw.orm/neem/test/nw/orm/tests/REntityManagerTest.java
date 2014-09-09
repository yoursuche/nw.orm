package nw.orm.tests;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import nw.orm.examples.model.Item;
import nw.orm.examples.model.Person;
import nw.orm.examples.model.enums.Sex;
import nw.orm.examples.pojo.PersonPojo;
import nw.orm.examples.pojo.ResultPojo;
import nw.orm.manager.REntityManager;
import nw.orm.query.QueryAlias;
import nw.orm.query.QueryModifier;

public class REntityManagerTest {
	
	private REntityManager dbService;
	
	@Before
	public void initialize(){
		dbService = REntityManager.getInstance();
		assertNotNull("Entity Manager intialization failed", dbService);
	}
	
	@Test
	public void getByHQL() {
//		String query = "FROM Person where fullName = :name";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("name", "John Doe");
//		Person p = dbService.getByHQL(query, params, Person.class);
//		if(p != null){
//			assertEquals("John Doe", p.getFullName());
//		}else{
//			assertTrue(true);
//		}
//		
//		query = "SELECT p.age as age, p.sex as sex FROM Person p where fullName = :name";
//		PersonPojo pojo = dbService.getByHQL(query, params, PersonPojo.class);
//		if(pojo != null){
//			assertEquals(20, pojo.getAge());
//			System.out.println(pojo.getSex());
//		}else
			assertTrue(true);
		
	}
	
	@Test
	public void create(){
		Person p = new Person();
		p.setAge(30);
		p.setFullName("John Doe Testricy");
		p.setSex(Sex.MALE);
		Serializable idk = dbService.create(p);
		assertNotNull("Create failed", idk);
		
		Item it = new Item();
		it.setName("Watch");
		it.setOwner(p);
		assertNotNull("Create failed", dbService.create(it));
	}
	
	@Test
	public void update(){
		Person person = dbService.getById(Person.class, 8L, false);
		assertNotNull(person.getPk());
		person.setAge(2500);
		assertTrue(dbService.update(person));
	}
	
	@Test
	public void fndByExample(){
		dbService = REntityManager.getInstance();
		Person p = new Person();
		p.setAge(20);
		p.setActive(true);
		
		Example ex = Example.create(p)
				.enableLike()
				.ignoreCase()
				.excludeZeroes();
		
		List<Person> eg = dbService.getByExample(Person.class, ex);
		assertTrue(eg.size() == 4);
	}
	
	@Test
	public void getListByCriteria(){
		dbService = REntityManager.getInstance();
		QueryModifier qm = new QueryModifier(Person.class);
		qm.addProjection(Projections.groupProperty("age").as("age"));
		qm.addProjection(Projections.alias(Projections.rowCount(), "rowCount"));
		List<ResultPojo> c = dbService.getListByCriteria(ResultPojo.class, qm , true);
		System.out.println(c.get(1).getAge() + " " + c.get(1).getRowCount());
	}

	@Test
	public void getByCriteria(){
		QueryModifier qm = new QueryModifier(Item.class);
		QueryAlias qa = new QueryAlias("owner", "owner");
		qm.addAlias(qa);
		
		List<Item> i = dbService.getListByCriteria(Item.class, qm, false, Restrictions.eq("owner.age", 30));
		for(Item item: i){
			assertEquals(item.getOwner().getAge(), 30);
		}
		
	}
	
	@Test
	public void bulkUpdate(){
		List<Person> lbc = dbService.getListByCriteria(Person.class, Restrictions.eq("age", 32));
		System.out.println(lbc.size());
		List<Object> items = new ArrayList<Object>();
		for(Person p: lbc){
			p.setAge(322);
			items.add(p);
		}
		assertTrue(dbService.updateBulk(items));
	}
	
	@Test
	public void testGetById(){
		Person person = dbService.getById(Person.class, 2L);
		assertNotNull(person);
		
	}
	
}
