package nw.orm.test.core.jpa;

import static org.junit.Assert.*;

import java.util.List;

import nw.orm.core.jpa.NwormJpa;
import nw.orm.entity.geo.City;

import org.junit.BeforeClass;
import org.junit.Test;

public class NwormJpaTest {

	private static NwormJpa jpaService;

	@BeforeClass
	public static void init(){
		jpaService = NwormJpa.getInstance("nworm");
	}

	@Test
	public void testFindById() {
		City city = jpaService.getById(City.class, 1L);
		assertNull(city);
	}

	@Test
	public void testGetAll(){
		List<City> all = jpaService.getAll(City.class);
		assertEquals(1, all.size());
	}

}
