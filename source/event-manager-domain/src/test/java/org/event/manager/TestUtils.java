package org.event.manager;


import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Ignore;
import org.junit.Test;

public class TestUtils {

	@Ignore
	public static <T,D> void equalsTest(T first,T second,T third, D different){
		assertFalse(first.equals(different));
		assertTrue(first.equals(second));
		assertTrue(second.equals(third));
		assertTrue(first.equals(third));
		assertTrue(first.equals(first));
		assertFalse(first.equals(null));
	}
	
	@Ignore
	public static <T> void hashCodeTest(T first,T equals, T different){
		assertEquals(first, equals);
		assertEquals(first.hashCode(),equals.hashCode());		
		assertFalse(first.equals(different));
		assertFalse(first.hashCode() == different.hashCode());
	}
	
	@Ignore @SuppressWarnings("unchecked")
	public static <T extends Comparable> void test_comparable(T object, T equals , T smaller){
		assertTrue(object.compareTo(equals)==0);
		assertTrue(smaller.compareTo(object)<0);
		assertTrue(object.compareTo(smaller)>0);
	}
	
	@Ignore
	public static <T> void test_comparable(T object, T equals , T smaller,Comparator<T> comparator){
		assertTrue(comparator.compare(object, equals)==0);
		assertTrue(comparator.compare(smaller, object)<0);
		assertTrue(comparator.compare(object, smaller)>0);
	}
	
	@Test
	public void dummyTest(){}
}
