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

        //todo remove
        Storage storage = Storage.getInstance();

        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(getContext());

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(getContext())
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(getContext())
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }

    public static Context getContext(){
        return context;
    }
}
