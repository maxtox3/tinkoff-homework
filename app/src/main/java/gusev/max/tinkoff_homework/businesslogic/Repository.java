package gusev.max.tinkoff_homework.businesslogic;

import java.util.List;

import gusev.max.tinkoff_homework.businesslogic.model.News;
import io.reactivex.Observable;

/**
 * Created by v on 25/11/2017.
 */

public interface Repository {

    Observable<List<News>> getNews(Boolean forceRemote);

}
