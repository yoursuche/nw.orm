package nw.orm.test.manager;

import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;

import nw.orm.core.query.QueryParameter;
import nw.orm.core.service.Nworm;
import nw.orm.entity.UuidModel;
import nw.orm.entity.BasicUUIDModel;

public class BasicExample {

	private Nworm dbService;

	@Test
	public void getService(){
		dbService = Nworm.getInstance(); // loads service using default configuration file
		assertNotNull("Could not get connection to the database", dbService);
	}

	@Test
	public void createSequenced(){
		dbService = Nworm.getInstance();
		UuidModel bs = new UuidModel();
		bs.setBasicDate(new Date());
		bs.setBasicString("Sample String");

		Long pk = (Long) dbService.create(bs);
		assertNotNull(pk);
	}

	@Test
	public void createUUIDed(){
		dbService = Nworm.getInstance();
		BasicUUIDModel bs = new BasicUUIDModel();
		bs.setBasicDate(new Date());
		bs.setBasicString("Sample String");
		bs.setGenField(new Date());
		String pk = (String) dbService.create(bs);
		assertNotNull(pk);
	}

	@Test
	public void findByHQL(){
		dbService = Nworm.getInstance();
		BasicUUIDModel bh = dbService.getByHQL(BasicUUIDModel.class, "FROM BasicUUIDModel b where b.basicString = :bs", QueryParameter.create("bs", "Sample String"));
		// if items found
		if(bh != null)
			assertEquals("Sample String", bh.getBasicString());
		else
			fail("Item not found on database");
	}

	@Test
	public void update(){
		dbService = Nworm.getInstance();
		BasicUUIDModel bh = dbService.getByHQL(BasicUUIDModel.class, "FROM BasicUUIDModel b where b.basicString = :bs", QueryParameter.create("bs", "Sample String"));
		bh.setBasicString("Hga");
		assertTrue(dbService.update(bh));

	}

	@Test
	public void delete(){
		dbService = Nworm.getInstance();
		BasicUUIDModel bh = dbService.getByHQL(BasicUUIDModel.class, "FROM BasicUUIDModel b where b.basicString = :bs", QueryParameter.create("bs", "Hga"));
		dbService.remove(bh);
		assertTrue(true);

	}
}
