package com.ok.atm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationChecks {

    public static boolean isValidEmailAddress(String email) {
        final String EMAIL_REGEX =
                "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidIdentityNumber(long identityNumber) {
        long length = 0;
        long temp = 1;
        while (temp <= identityNumber) {
            length++;
            temp *= 10;
        }
        if (length == 11) {
            return true;
        }
        return false;
    }

}
