package main.java.edu.gatech;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * the Date utility.
 * @author Ruobin Ling.
 *
 */
public class Utils {
	/**
	 * gets the date.
	 * @param month of the transaction.
	 * @param year of the transaction.
	 * @param day of the transaction.
	 * @return the date.
	 */
    public static String getDate(int month, int year, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Date date = (Date) c.getTime();
        return dateFormat.format(date);
    }
}
