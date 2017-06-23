package me.labs.corobox.corobox.common.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static Date stringToDate(String string, final String format, final Locale locale) throws ParseException {
        ThreadLocal formater = new ThreadLocal() {
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat(format, locale);
            }
        };
        return ((SimpleDateFormat)formater.get()).parse(string);
    }

    public static long dateToTimestamp(Date date) {
        return date.getTime() / 1000;
    }
}
