package org.event.manager.collections;

import static org.junit.Assert.*;

import org.junit.Test;


public class ArrayBuildTest {

	@Test
	public void create_simple_array() {
		assertNotNull(ArrayBuild.of());
	}

	@Test
	public <T> void test_creation_with_different_data() {
		String[] array = ArrayBuild.of("1", "2", "3");
		assertNotNull(array);
		String[] expected = {"1","2","3"};
		assertArrayEquals(expected,array);
	}

}
