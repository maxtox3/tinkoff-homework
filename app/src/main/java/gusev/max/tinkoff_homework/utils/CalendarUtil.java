package gusev.max.tinkoff_homework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by v on 25/11/2017.
 */

public class CalendarUtil {

    private static String dateFormat = "dd-MM-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    public static String ConvertMilliSecondsToFormattedDate(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }
}
