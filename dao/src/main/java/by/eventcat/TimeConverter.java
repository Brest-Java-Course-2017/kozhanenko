package by.eventcat;

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
     * Convert time in String format to timestamp (milliseconds)
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

    static String convertTimeFromSecondsToString (long timeInSeconds){
        Date date = new Date(timeInSeconds*1000);
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    static boolean isValidDateInString(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            df.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
