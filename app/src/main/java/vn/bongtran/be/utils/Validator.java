package vn.bongtran.be.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Validator {
    public static final SimpleDateFormat BIRTHDAY_FORMAT_PARSER = new SimpleDateFormat("dd/MM/yyyy");
    public static final String DASH_STRING = "-";

    public static Calendar parseDateString(String date) {
        Calendar calendar = Calendar.getInstance();
        BIRTHDAY_FORMAT_PARSER.setLenient(false);
        try {
            calendar.setTime(BIRTHDAY_FORMAT_PARSER.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static boolean isValidBirthday(String birthday) {
        Calendar calendar = parseDateString(birthday);
        int year = calendar.get(Calendar.YEAR);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        return year >= 1900 && year < thisYear;
    }
}
