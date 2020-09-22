package com.lush.givex.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFunctions {
    private static final String NO_EXPIRATION_DATE_VALUE = "None";

    public static Date parseDate(String value, String responseName) {
        if (NO_EXPIRATION_DATE_VALUE.equalsIgnoreCase(value)) {
            return null;
        }

        try {
            return (new SimpleDateFormat("yyyy-MM-dd", Locale.US)).parse(value);
        } catch (ParseException e) {
            Log.e(DateFunctions.class.getName(), "Unexpected date value in " + responseName + " Givex response: '" + value + "'", e);
            return null;
        }
    }

    private DateFunctions() {}
}
