package org.event.manager.collections;

import java.util.Arrays;
import static org.testng.Assert.assertNotNull;

import org.event.manager.test.TestGroup;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = { TestGroup.UNIT })
public class ArrayBuildTest {

	@Test
	public void create_simple_array() {
		assertNotNull(ArrayBuild.of());
	}

	@DataProvider(name = "array")
	public Object[][] dataPriver() {
		return new Object[][] {
                    new Object[] {new Object[]{ "1", "2", "3" }},
		    new Object[] {}, new Object[] {new Object[]{ null, "not null", }},
                    new Object[] {}, new Object[] {new Object[]{ null, 1L,"1" }}
                };
	}

	@Test(dataProvider = "array")
	public <T> void test_creation_with_different_data(T... data) {
            System.out.println(Arrays.toString(data));
            assertNotNull(ArrayBuild.of(data));
	}

}
