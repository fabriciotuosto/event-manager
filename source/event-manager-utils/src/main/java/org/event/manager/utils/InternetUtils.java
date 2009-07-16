/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.event.manager.utils;

import java.util.regex.Pattern;

/**
 *
 * @author tuosto
 */
public final class InternetUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(".+@.+\\.[a-z]+");
    private static final Pattern IMAGE_URI_PATTERN = Pattern.compile("^http:\\/\\/.+\\/.+\\.(png|jpg|gif|jpeg)$");
    
    private InternetUtils(){}

    public static boolean isEmailAdressValid(String adress){
        return EMAIL_PATTERN.matcher(adress).matches();
    }

    public static boolean isImageLinkValid(String adress){
        return IMAGE_URI_PATTERN.matcher(adress).matches();
    }
}
