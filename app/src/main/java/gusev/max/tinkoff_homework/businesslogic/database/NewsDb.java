package gusev.max.tinkoff_homework.businesslogic.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import gusev.max.tinkoff_homework.businesslogic.model.News;

/**
 * Created by v on 27/11/2017.
 */

@Database(entities = News.class, version = 1)
public abstract class NewsDb extends RoomDatabase{

    public abstract NewsDao newsDao();
}
