package gusev.max.tinkoff_homework.businesslogic.api;

import gusev.max.tinkoff_homework.businesslogic.model.Response;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by v on 25/11/2017.
 */

public interface ApiInterface {

    @GET("news")
    Observable<Response> getNews();

}
