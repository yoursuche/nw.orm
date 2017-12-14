package nw.orm.core;

import org.junit.Test;

import nw.orm.entity.Item;
import nw.orm.entity.Person;

import static org.junit.Assert.*;

public class EntityTest {
	
	@Test
	public void testEqualEntities() {
		
		Item it = new Item();
		it.setPk(1L);
		
		Person pe = new Person();
		pe.setPk(1L);
		
		assertNotEquals(it,  pe);
		
		Item it2 = new Item();
		it2.setPk(1L);
		assertEquals(it2, it);
		
	}
	
	@Test
	public void testComparableEntities() {
		
		Item it = new Item();
		it.setPk(1L);
		
		Item it2 = new Item();
		it2.setPk(2L);
		
		assertTrue(it2.compareTo(it) == 1);
		
	}

}
