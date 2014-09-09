package nw.orm.test.manager;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;

import javax.naming.OperationNotSupportedException;

import nw.orm.examples.model.Person;
import nw.orm.examples.model.enums.Sex;
import nw.orm.examples.pojo.PersonPojo;
import nw.orm.examples.pojo.TestPox;
import nw.orm.manager.REntityManager;
import nw.orm.query.QueryModifier;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.Test;

public class REntityManagerTest {
	
	private static REntityManager rem;
	private static REntityManager rem2;
	
	private static Long personPk;
	
	@BeforeClass
	public static void init() throws OperationNotSupportedException{
		String cfg = "hibernate.cfg.xml";
		Properties props = new Properties();
		props.put("config.name", "xtra");
		props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/test_im");
		rem = REntityManager.getInstance();
		rem2 = REntityManager.getInstance(cfg, props);
		
		Person p = new Person();
		p.setAge(20);
		p.setFullName("Tekaso Pillasso");
		p.setSex(Sex.FEMALE);
		
		personPk = (Long) rem.create(p);
		
	}

	@Test
	public void testGetInstance() {
		assertNotNull(rem);
		assertNotNull(rem2);
		assertNotEquals(rem, rem2);
	}

	@Test
	public void testCloseFactory() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsClassMapped() {
		assertTrue(rem.isClassMapped(Person.class));
		assertFalse(rem.isClassMapped(PersonPojo.class));
	}

	@Test
	public void testGetById() {
		Person bid = rem.getById(Person.class, personPk);
		assertEquals(bid.getPk(), personPk);
		
		Person bid2 = rem2.getById(Person.class, personPk);
		assertNull(bid2);
	}

	@Test
	public void testGetByIdWithLock() {
		Person bid = rem.getById(Person.class, personPk, true);
		assertEquals(bid.getPk(), personPk);
		
		Person bid2 = rem2.getById(Person.class, personPk, true);
		assertNull(bid2);
	}

	@Test
	public void testGetAll() {
		List<Person> all = rem.getAll(Person.class);
		assertTrue(!all.isEmpty());
	}

	@Test
	public void testGetByCriteria() {
		Person bc = rem.getByCriteria(Person.class, Restrictions.idEq(personPk));
		assertEquals(bc.getPk(), personPk);
		
		List<Person> lc = rem.getListByCriteria(Person.class, Restrictions.eq("age", 20));
		assertTrue(!lc.isEmpty());
		
		QueryModifier qm = new QueryModifier(Person.class);
		qm.addProjection(Projections.property("age").as("age"));
		qm.addProjection(Projections.count("age").as("size"));
		qm.addProjection(Projections.groupProperty("age"));
		qm.transformResult(true);
		
		List<TestPox> lbc = rem.getListByCriteria(TestPox.class, qm, Restrictions.ge("age", 20));
		System.out.println(lbc);
		assertTrue(!lbc.isEmpty());
	}

	@Test
	public void testGetByHQL() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByHQLClassOfTStringQueryParameterArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListByHQLStringMapOfStringObjectClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListByHQLClassOfTStringQueryParameterArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBySQL() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByCriteriaQueryModifierOfTCriterionArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListByCriteriaQueryModifierOfTCriterionArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecuteSQLUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecuteHQLUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSoftDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testBulkSoftDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveClassOfQSerializable() {
		fail("Not yet implemented");
	}

	@Test
	public void testBulkRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateBulk() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateBulk() {
		fail("Not yet implemented");
	}

	@Test
	public void testToggleActive() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateOrUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnableJTABasedSession() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisableJTABasedSession() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifyHQL() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifyCriteria() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsInitializedSuccessfully() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetInitializedSuccessfully() {
		fail("Not yet implemented");
	}

}
