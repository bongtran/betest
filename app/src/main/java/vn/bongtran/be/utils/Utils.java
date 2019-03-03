package vn.bongtran.be.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static Calendar convertStringToDate(String string){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date = dateFormat.parse(string);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(">>>", e.getMessage());
            return Calendar.getInstance();
        }
    }

    public static String convertDateToString(Calendar calendar){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf.format(calendar.getTime());
    }
}
