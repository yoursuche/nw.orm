package nw.orm.examples.rm;

import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Test;

import nw.orm.examples.model.BasicSequencedIdModel;
import nw.orm.examples.model.BasicUUIDModel;
import nw.orm.manager.REntityManager;
import nw.orm.query.QueryParameter;

public class BasicExample {

	private REntityManager dbService;

	@Test
	public void getService(){
		dbService = REntityManager.getInstance(); // loads service using default configuration file
		assertNotNull("Could not get connection to the database", dbService);
	}

	@Test
	public void createSequenced(){
		dbService = REntityManager.getInstance();
		BasicSequencedIdModel bs = new BasicSequencedIdModel();
		bs.setBasicDate(new Date());
		bs.setBasicString("Sample String");

		Long pk = (Long) dbService.create(bs);
		assertNotNull(pk);
	}

	@Test
	public void createUUIDed(){
		dbService = REntityManager.getInstance();
		BasicUUIDModel bs = new BasicUUIDModel();
		bs.setBasicDate(new Date());
		bs.setBasicString("Sample String");
		bs.setGenField(new Date());
		String pk = (String) dbService.create(bs);
		assertNotNull(pk);
	}

	@Test
	public void findByHQL(){
		dbService = REntityManager.getInstance();
		BasicUUIDModel bh = dbService.getByHQL(BasicUUIDModel.class, "FROM BasicUUIDModel b where b.basicString = :bs", QueryParameter.create("bs", "Sample String"));
		// if items found
		if(bh != null)
			assertEquals("Sample String", bh.getBasicString());
		else
			fail("Item not found on database");
	}

	@Test
	public void update(){
		dbService = REntityManager.getInstance();
		BasicUUIDModel bh = dbService.getByHQL(BasicUUIDModel.class, "FROM BasicUUIDModel b where b.basicString = :bs", QueryParameter.create("bs", "Sample String"));
		bh.setBasicString("Hga");
		assertTrue(dbService.update(bh));

	}

	@Test
	public void delete(){
		dbService = REntityManager.getInstance();
		BasicUUIDModel bh = dbService.getByHQL(BasicUUIDModel.class, "FROM BasicUUIDModel b where b.basicString = :bs", QueryParameter.create("bs", "Hga"));
		dbService.remove(bh);
		assertTrue(true);

	}
}
