package by.eventcat.jpa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Time conversion class
 */
class TimeConverter {
    /**
     * Convert time in String format to timestamp (seconds)
     * @param timeInString time in format "yyyy-MM-dd HH:mm:ss"
     * @return time in timestamp format
     */
    static long convertTimeFromStringToSeconds(String timeInString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = simpleDateFormat.parse(timeInString);
        }catch (ParseException ex){
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis()/1000;
    }

    /**
     * Convert time in timestamp format (seconds) to String format
     *
     * @param timeInSeconds time in timestamp format
     * @return time in format "yyyy-MM-dd HH:mm:ss"
     */
    static String convertTimeFromSecondsToString (long timeInSeconds){
        Date date = new Date(timeInSeconds*1000);
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * Check if date in string format matches pattern "yyyy-MM-dd HH:mm:ss"
     *
     * @param dateString testing date string
     * @return if dateString matches pattern "yyyy-MM-dd HH:mm:ss"
     */
    static boolean isValidDateInStringFormat(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            df.parse(dateString);
            String [] arr = dateString.split(" ");
            String [] date = arr[0].split("-");
            String [] time = arr[1].split(":");
            return (
                    date[0].length() ==4
                    && date[1].length() ==2
                    && date[2].length() ==2
                    && time[0].length() ==2
                    && time[1].length() ==2
                    && time[2].length() ==2);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Check if string that matches pattern "yyyy-MM-dd HH:mm:ss" contains correct date and time
     *
     * @param dateString testing date string
     * @return if testing date string contains correct date and time
     */
    static boolean isValidDateInString(String dateString){
        if ( ! isValidDateInStringFormat(dateString)){
            return false;
        }
        String [] arr = dateString.split(" ");
        String [] date = arr[0].split("-");
        String [] time = arr[1].split(":");

        if ( ! isInteger(date[0])) return false;
        if (! isInteger(date[1]) ||
                Integer.parseInt(date[1]) < 1 || Integer.parseInt(date[1]) > 12) return false;
        if (! isInteger(date[2]) ||
                Integer.parseInt(date[2]) < 1 || Integer.parseInt(date[2]) > 31) return false;
        if (Integer.parseInt(date[1]) == 2 && Integer.parseInt(date[2]) > 29 ) return false;

        if (! isInteger(time[0]) ||
                Integer.parseInt(time[0]) < 0 || Integer.parseInt(time[0]) > 23) return false;
        if (! isInteger(time[1]) ||
                Integer.parseInt(time[1]) < 0 || Integer.parseInt(time[1]) > 59) return false;
        if (! isInteger(time[2]) ||
                Integer.parseInt(time[2]) < 0 || Integer.parseInt(time[2]) > 59) return false;
        return true;
    }

    /**
     * Check if argument string is integer number
     *
     * @param s testing string
     * @return if argument string is integer number
     */
    private static boolean isInteger(String s) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i), 10) < 0) return false;
        }
        return true;
    }

}
