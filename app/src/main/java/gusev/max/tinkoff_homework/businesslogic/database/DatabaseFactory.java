package gusev.max.tinkoff_homework.businesslogic.database;

import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import gusev.max.tinkoff_homework.NewsApp;

/**
 * Created by v on 27/11/2017.
 */

public class DatabaseFactory {

    private static final String DB_NAME = "news_db";

    private static volatile NewsDao sNewsService;

    private static NewsDb INSTANCE;

    public static void recreate() {
        INSTANCE = null;
        INSTANCE = getInstance();
        sNewsService = getNewsDao();
    }

    public static synchronized NewsDb getInstance() {

        if (INSTANCE == null) {
            INSTANCE = getDatabase();
        }
        return INSTANCE;
    }

    private static NewsDb getDatabase() {
        return Room.databaseBuilder(NewsApp.getContext(), NewsDb.class, DB_NAME).build();
    }

    @NonNull
    public static NewsDao getNewsDao() {
        NewsDao service = sNewsService;
        if (service == null) {
            synchronized (NewsDao.class) {
                service = sNewsService;
                if (service == null) {
                    service = sNewsService = getInstance().newsDao();
                }
            }
        }
        return service;
    }
    
}
