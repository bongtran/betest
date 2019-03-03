package vn.bongtran.be.utils;

import java.util.Random;

public class BindingUtils {
    public static String getFullName(String firstName, String lastName){
        return firstName + " " + lastName;
    }

    public static String getCardPosition(){
        String[] position = {"Developer", "Technical Lead", "Team Leader", "PM", "COO", "CFO", "CEO"};
        int i = new Random().nextInt(7);
        return position[i];
    }

    public static String getCardCompany(int id){
        return getCardPosition();
    }

    public static String getFormattedDate(String JsonDate){
        String result = "";

        return result;
    }
}
