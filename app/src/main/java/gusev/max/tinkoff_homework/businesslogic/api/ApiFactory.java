package gusev.max.tinkoff_homework.businesslogic.api;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by v on 25/11/2017.
 */

public class ApiFactory {

    private static final String BASE_URL = " https://api.tinkoff.ru/v1/";
    private static OkHttpClient sClient;

    private static volatile ApiInterface sService;

    private ApiFactory() {
    }

    @NonNull
    public static ApiInterface getApiInterface() {
        ApiInterface service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(ApiInterface.class);
                }
            }
        }
        return service;
    }

    public static void recreate() {
        sClient = null;
        sClient = getClient();
        sService = buildRetrofit().create(ApiInterface.class);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.create())
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();
    }
}
