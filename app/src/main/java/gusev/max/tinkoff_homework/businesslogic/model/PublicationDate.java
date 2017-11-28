package gusev.max.tinkoff_homework.businesslogic.model;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by v on 25/11/2017.
 */
@Entity
public class PublicationDate {



    @SerializedName("milliseconds")
    private long milliseconds;

    public PublicationDate(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }
}
