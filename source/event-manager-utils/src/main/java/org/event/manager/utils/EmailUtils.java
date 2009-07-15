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
public final class EmailUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(".+@.+\\.[a-z]+");
    
    private EmailUtils(){}

    public static boolean isEmailAdressValid(String adress){
        return EMAIL_PATTERN.matcher(adress).matches();
    }

}
