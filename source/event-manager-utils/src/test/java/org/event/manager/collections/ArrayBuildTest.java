package org.event.manager.collections;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

@Test(groups = { "unit" })
public class ArrayBuildTest {

	@Test
	public void create_simple_array() {
		assertNotNull(ArrayBuild.of());
	}
}
