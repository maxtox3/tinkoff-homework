package gusev.max.tinkoff_homework.businesslogic.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import gusev.max.tinkoff_homework.businesslogic.model.News;
import io.reactivex.Flowable;

/**
 * Created by v on 27/11/2017.
 */

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news ORDER BY publication_date DESC")
    Flowable<List<News>> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News news);

    @Query("DELETE FROM news")
    void deleteAll();
}
