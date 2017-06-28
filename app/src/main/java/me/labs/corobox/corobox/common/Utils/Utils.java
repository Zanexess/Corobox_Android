package me.labs.corobox.corobox.common.Utils;

import java.text.DateFormat;
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

    public static String getDate(long timeStamp){

        try{
            DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    public static String getStatus(String status) {
        switch (status) {
            case "pending":
                return "Обрабатывается";
            case "done":
                return "Выполнен";
            case "cancelled":
                return "Отменен";
            case "packaging":
                return "Упаковывается";
            case "delivering":
                return "Курьер в пути";
        }
        return null;
    }
}
