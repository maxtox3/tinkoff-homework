package gusev.max.tinkoff_homework.fourth;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by v on 06/11/2017.
 */

public class DateExample {

    private Date date;

    public DateExample(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    static DateExample getTestDate(){
        return new DateExample(new Date(Calendar.getInstance().getTimeInMillis()));
    }

}
