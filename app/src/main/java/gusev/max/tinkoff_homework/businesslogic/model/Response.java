package gusev.max.tinkoff_homework.businesslogic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by v on 25/11/2017.
 */

public class Response {

    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("payload")
    @Expose
    private ArrayList<News> payload = null;
    @SerializedName("trackingId")
    @Expose
    private String trackingId;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public ArrayList<News> getNews() {
        return payload;
    }

    public void setNews(ArrayList<News> news) {
        this.payload = news;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

}
