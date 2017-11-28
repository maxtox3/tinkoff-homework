package gusev.max.tinkoff_homework.businesslogic.interactors;

import java.util.concurrent.TimeUnit;

import gusev.max.tinkoff_homework.businesslogic.Repository;
import gusev.max.tinkoff_homework.businesslogic.RepositoryProvider;
import gusev.max.tinkoff_homework.view.news.NewsViewState;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by v on 26/11/2017.
 */

public class NewsInteractor {

    private Repository dataRepository = RepositoryProvider.provideRepository();

    public Observable<NewsViewState> load() {
        return dataRepository.getNews(false)
                .subscribeOn(Schedulers.io())
                .map(news -> (NewsViewState) new NewsViewState.NewsLoaded(news))
                .startWith(new NewsViewState.NewsLoading())
                .onErrorReturn(NewsViewState.NewsLoadingError::new)
                .delay(1, TimeUnit.SECONDS);// for the download is visible
    }

    public Observable<NewsViewState> loadLatest() {
        return dataRepository.getNews(true)
                .subscribeOn(Schedulers.io())
                .map(news -> (NewsViewState) new NewsViewState.PullToRefreshLoaded(news))
                .startWith(new NewsViewState.PullToRefreshLoading())
                .onErrorReturn(NewsViewState.PullToRefreshLoadingError::new)
                .delay(1, TimeUnit.SECONDS);// for the download is visible
    }

}
