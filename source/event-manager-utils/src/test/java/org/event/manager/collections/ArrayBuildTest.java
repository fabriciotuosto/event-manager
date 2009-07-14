package org.event.manager.collections;

import static org.testng.Assert.assertNotNull;

import org.event.manager.test.TestGroup;
import org.testng.annotations.Test;

@Test(groups = { TestGroup.UNIT })
public class ArrayBuildTest {

	@Test
	public void create_simple_array() {
		assertNotNull(ArrayBuild.of());
	}
}
