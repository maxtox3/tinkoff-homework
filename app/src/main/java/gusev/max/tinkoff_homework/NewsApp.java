package gusev.max.tinkoff_homework;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;

import gusev.max.tinkoff_homework.businesslogic.RepositoryProvider;
import gusev.max.tinkoff_homework.businesslogic.api.ApiFactory;
import gusev.max.tinkoff_homework.businesslogic.database.DatabaseFactory;

/**
 * Created by v on 25/11/2017.
 */

public class NewsApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        ApiFactory.recreate();
        DatabaseFactory.recreate();
        RepositoryProvider.init();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    @NonNull
    public static Context getContext(){
        return context;
    }
}
