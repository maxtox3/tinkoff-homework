package gusev.max.tinkoff_homework.view.news;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import gusev.max.tinkoff_homework.businesslogic.interactors.NewsInteractor;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by v on 25/11/2017.
 */

public class NewsPresenter extends MviBasePresenter<NewsView, NewsViewState> {

    private final NewsInteractor newsInteractor = new NewsInteractor();

    @Override
    protected void bindIntents() {
        Observable<NewsViewState> getNews = intent(NewsView::newsIntent)
                .doOnNext(s -> Log.d("bindIntents: ", "yo"))
                .switchMap(s -> newsInteractor.load().subscribeOn(Schedulers.io()));

        Observable<NewsViewState> pullToRefresh = intent(NewsView::pullToRefreshIntent)
                .doOnNext(s -> Log.d("bindIntents: ", "yo"))
                .switchMap(s -> newsInteractor.loadLatest().subscribeOn(Schedulers.io()));

        Observable<NewsViewState> allIntents =
                Observable.merge(getNews, pullToRefresh)
                .observeOn(AndroidSchedulers.mainThread());

        subscribeViewState(allIntents, NewsView::render);
    }

//    private NewsViewStateOld viewStateReducer(NewsViewStateOld previousState, NewsViewState stateChanges) {
//        if (stateChanges instanceof NewsViewState.NewsLoading) {
//            return previousState.builder().loadingNews(true).loadingNewsError(null).build();
//        }
//
//        if (stateChanges instanceof NewsViewState.NewsLoadingError) {
//            return previousState.builder()
//                    .loadingNews(false)
//                    .loadingNewsError(((NewsViewState.NewsLoadingError) stateChanges).getError())
//                    .build();
//        }
//
//        if (stateChanges instanceof NewsViewState.NewsLoaded) {
//            return previousState.builder()
//                    .loadingNews(false)
//                    .loadingNewsError(null)
//                    .data(((NewsViewState.NewsLoaded) stateChanges).getData())
//                    .build();
//        }
//
//        if (stateChanges instanceof NewsViewState.PullToRefreshLoading) {
//            return previousState.builder().pullToRefreshLoading(true).pullToRefreshError(null).build();
//        }
//
//        if (stateChanges instanceof NewsViewState.PullToRefeshLoadingError) {
//            return previousState.builder()
//                    .pullToRefreshLoading(false)
//                    .pullToRefreshError(
//                            ((NewsViewState.PullToRefeshLoadingError) stateChanges).getError())
//                    .build();
//        }
//
//        if (stateChanges instanceof NewsViewState.PullToRefreshLoaded) {
//            return previousState.builder()
//                    .pullToRefreshLoading(false)
//                    .pullToRefreshError(null)
//                    .data(((NewsViewState.PullToRefreshLoaded) stateChanges).getData())
//                    .build();
//        }
//        throw new IllegalStateException("Don't know how to reduce the partial state " + stateChanges);
//    }
}
