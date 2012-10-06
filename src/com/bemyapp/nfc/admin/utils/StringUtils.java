package com.bemyapp.nfc.admin.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StringUtils {

    public static boolean isEmpty(String str) {

        if (str != null && str.length() > 0) {
            return false;
        }
        return true;
    }
    
    public static String toStringFormat(int number, String pattern) {
		
		NumberFormat numberFormat = new DecimalFormat(pattern);
        return numberFormat.format(number);
	}
}
