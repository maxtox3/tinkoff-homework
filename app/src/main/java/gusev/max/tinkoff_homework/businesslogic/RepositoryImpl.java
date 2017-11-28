package gusev.max.tinkoff_homework.businesslogic;

import java.util.List;

import gusev.max.tinkoff_homework.businesslogic.api.ApiFactory;
import gusev.max.tinkoff_homework.businesslogic.api.ApiInterface;
import gusev.max.tinkoff_homework.businesslogic.database.DatabaseFactory;
import gusev.max.tinkoff_homework.businesslogic.database.NewsDao;
import gusev.max.tinkoff_homework.businesslogic.model.News;
import gusev.max.tinkoff_homework.businesslogic.model.Response;
import io.reactivex.Observable;

/**
 * Created by v on 25/11/2017.
 */

public class RepositoryImpl implements Repository {

    private ApiInterface apiInterface = ApiFactory.getApiInterface();
    private NewsDao newsDao = DatabaseFactory.getNewsDao();

    @Override
    public Observable<List<News>> getNews(Boolean forceRemote) {
        if (forceRemote) {
            return refreshData();
        } else {
            return newsDao.getAllNews()
                    .toObservable()
                    .switchIfEmpty(refreshData());
        }
    }

    private Observable<List<News>> refreshData() {
        return apiInterface.getNews()
                .map(Response::getNews)
                .doOnNext(news -> newsDao.deleteAll())
                .flatMap(Observable::fromIterable)
                .doOnNext(news ->
                        newsDao.insert(
                                new News(news.getId(),
                                        news.getName(),
                                        news.getText(),
                                        news.getPublicationDate().getMilliseconds(),
                                        news.getBankInfoTypeId())))
                .toList()
                .toObservable();
    }
}
