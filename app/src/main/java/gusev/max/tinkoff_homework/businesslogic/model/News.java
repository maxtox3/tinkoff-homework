package gusev.max.tinkoff_homework.businesslogic.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import gusev.max.tinkoff_homework.utils.CalendarUtil;

/**
 * Created by v on 25/11/2017.
 */

@Entity(tableName = "News")
public class News {

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("text")
    private String text;

    @Ignore
    @SerializedName("publicationDate")
    private PublicationDate publicationDate;

    @ColumnInfo(name = "publication_date")
    private Long date;

    @SerializedName("bankInfoTypeId")
    @ColumnInfo(name = "bank_info_type_id")
    private int bankInfoTypeId;

    public News(){}

    @Ignore
    public News(@NonNull Long id, String name, String text, Long date, int bankInfoTypeId) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.date = date;
        this.bankInfoTypeId = bankInfoTypeId;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public PublicationDate getPublicationDate() {
        return publicationDate;
    }

    public String getPrettyPublicationDate(){
        if(getDate() != null) {
            return CalendarUtil.ConvertMilliSecondsToFormattedDate(getDate());
        } else {
            return CalendarUtil.ConvertMilliSecondsToFormattedDate(0L);
        }
    }

    public void setPublicationDate(PublicationDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getBankInfoTypeId() {
        return bankInfoTypeId;
    }

    public void setBankInfoTypeId(Integer bankInfoTypeId) {
        this.bankInfoTypeId = bankInfoTypeId;
    }
}
