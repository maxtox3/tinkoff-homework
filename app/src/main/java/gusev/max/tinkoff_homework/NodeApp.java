package gusev.max.tinkoff_homework;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import gusev.max.tinkoff_homework.data.db.Storage;

/**
 * Created by v on 13/11/2017.
 */

public class NodeApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Storage.getInstance();

        Stetho.initializeWithDefaults(this);
    }

    public static Context getContext(){
        return context;
    }
}
