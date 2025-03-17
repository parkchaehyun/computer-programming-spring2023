package snusearch.utility;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.URLDecoder;

public final class UtilityModule {
    public static boolean isValidPassword(String passwd) {
        // check if passwd is valid
        // & more than or equal to 4 characters
        // & must start with alphabet
        // & special characters are only allowed for "@", "#" and "%"
        // use regex
        if (passwd.matches("^[a-zA-Z][a-zA-Z0-9@#%]*$") && passwd.length() >= 4) {
            return true;
        } else {
            return false;
        }
    }
    public static String decodeURLToQuery(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
