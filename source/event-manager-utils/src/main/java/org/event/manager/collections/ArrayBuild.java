package org.event.manager.collections;

/**
 * Initially created to work with slf4j 
 * and provide a workaround to varargs missing
 * in the logging framework
 * 
 * @author tuosto
 *
 */
public class ArrayBuild {

	/**
	 * Builds an array containing the parameters
	 * added to the method call
	 * 
	 * @param <T> class of the array
	 * @param t elements to be added to the array
	 * @return an array containing the elements received as
	 * parameters
	 */
	public static <T> T[] of(T... t){
		return t;
	}
}
