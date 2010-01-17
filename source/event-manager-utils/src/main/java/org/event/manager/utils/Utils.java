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
	 * @param adress to be validate
	 * @return true if adress is an email adress
	 */
    public static boolean isEmailAdressValid(String adress){
    	return validateWithPattern(getEmailPatter(), adress);
    }

    /**
     * 
     * @param adress to be validate
     * @return true if address is an image http link
     */
    public static boolean isImageLinkValid(String adress){
        return validateWithPattern(getImageLinkPattern(), adress);
    }
    
    private static boolean validateWithPattern(Pattern pattern,String string){
    	return string != null && pattern.matcher(string).matches();
    }
    
    // Idiom to lazy load patterns matching so that there is not unnecessary creation
    private static Pattern getEmailPatter(){
    	return EmailPatternHolder.EMAIL_PATTERN;
    }
    
    private static Pattern getImageLinkPattern(){
    	return ImageLinkPatternHolder.LINK_PATTERN;
    }

    private static class ImageLinkPatternHolder {
    	private static final Pattern LINK_PATTERN = Pattern.compile("^http:\\//.+\\/.+\\.(png|jpg|gif|jpeg)$");
	}
    
    private static class EmailPatternHolder {
    	private static final Pattern EMAIL_PATTERN = Pattern.compile("^.+@.+\\.[a-z]+$");
	}    
}
