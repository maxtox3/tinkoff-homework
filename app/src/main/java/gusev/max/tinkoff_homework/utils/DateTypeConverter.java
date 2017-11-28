package gusev.max.tinkoff_homework.utils;

import android.arch.persistence.room.TypeConverter;

import gusev.max.tinkoff_homework.businesslogic.model.PublicationDate;

/**
 * Created by v on 27/11/2017.
 */

public class DateTypeConverter {

    @TypeConverter
    public static long publicationDateToLong(PublicationDate date){
        return date == null ? null : date.getMilliseconds();
    }

    @TypeConverter
    public static PublicationDate longToPublicationDate(Long milliseconds){

        return milliseconds == null ? null : new PublicationDate(milliseconds);
    }
}
