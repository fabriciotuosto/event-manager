package org.event.manager.entities;

import static org.junit.Assert.*;

import org.event.manager.TestUtils;
import org.junit.Test;

public class LocationTest {
	private static final Long ID = 10L;
	private static final Long INVALID_ID = -10L;
	@Test
	@SuppressWarnings("deprecation")
	public void should_create_with_argument_id(){
		assertEquals(ID,new Location(ID).getId());
		assertNotNull(new Location());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void should_thrown_exception_invalid_id(){
		createWithID(INVALID_ID);
	}

	@Test(expected=IllegalArgumentException.class)
	public void should_thrown_exception_invalid_null_id(){
		createWithID(null);
	}
	
	@SuppressWarnings("deprecation")
	private void createWithID(Long id) {
		new Location(id);
	}
	
	@Test
	public void should_create_location_with_builder(){
		String name = "OpenGallo";
		String address = "Gallo 314";
		Location location = Location.newLocation(name, address).build();
		assertSame(name,location.getName());
		assertSame(address,location.getAddress());
	}
	
	@Test
	public void should_create_location_with_builder_map_uri(){
		String name = "OpenGallo";
		String address = "Gallo 314";
		Location location = Location.newLocation(name, address)
									.mapLocation("http://www.kkaour.kk.net")
									.build();
		assertSame(name,location.getName());
		assertSame(address,location.getAddress());
	}	
	
	@Test
	@SuppressWarnings("deprecation")
	public void should_set_and_return_uri(){
		String uri = "http://www.gmail.com";
		Location location = new Location();
		location.setMapUri(uri);
		assertEquals(uri,location.getMapUri());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void location_hashCode(){
		Location first = Location.newLocation("Same", "same address").build();
		Location second = Location.newLocation("Same", "same address").build();
		Location different = Location.newLocation("ame", "same address").build();
		Location _different = Location.newLocation("ame", "same adres").build();
		TestUtils.hashCodeTest(first, second, different);
		TestUtils.hashCodeTest(first, second, _different);
		Location nullID = new Location();
		assertEquals(31*31,nullID.hashCode());
	}
	
	@Test
	public void location_equals(){
		Location first = Location.newLocation("Same", "same address").build();
		Location second = Location.newLocation("Same", "same address").build();
		Location third = Location.newLocation("Same", "same address").build();
		Object object = new Object();
		TestUtils.equalsTest(first, second, third, object);
	}
}
