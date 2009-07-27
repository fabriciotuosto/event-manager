package org.event.manager.utils;

import java.util.regex.Pattern;

/**
 * Initially created to work with slf4j and provide a workaround to varargs
 * missing in the logging framework
 * 
 * @author tuosto
 * 
 */
public final class Utils {

	private Utils() {}

	
	private static final Pattern EMAIL_PATTERN = Pattern.compile(".+@.+\\.[a-z]+");
	private static final Pattern IMAGE_URI_PATTERN = Pattern.compile("^http:\\/\\/.+\\/.+\\.(png|jpg|gif|jpeg)$");
	/**
	 * Builds an array containing the parameters added to the method call
	 * 
	 * @param <T>
	 *            class of the array
	 * @param t
	 *            elements to be added to the array
	 * @return an array containing the elements received as parameters
	 */
	public static <T> T[] arrayOf(T... t) {
		return t;
	}

	/**
	 * 
	 * @param adress
	 * @return
	 */
    public static boolean isEmailAdressValid(String adress){
    	return validateWithPattern(EMAIL_PATTERN, adress);
    }

    /**
     * 
     * @param adress
     * @return
     */
    public static boolean isImageLinkValid(String adress){
        return validateWithPattern(IMAGE_URI_PATTERN, adress);
    }
    
    private static boolean validateWithPattern(Pattern pattern,String string){
    	return string == null ? false : pattern.matcher(string).matches();
    }
}
