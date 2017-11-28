package gusev.max.tinkoff_homework.view.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hannesdorfmann.mosby3.mvi.MviActivity;
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gusev.max.tinkoff_homework.R;
import gusev.max.tinkoff_homework.businesslogic.model.News;
import io.reactivex.Observable;

/**
 * Created by v on 25/11/2017.
 */

public class NewsActivity extends MviActivity<NewsView, NewsPresenter> implements NewsView {

    @BindView(R.id.loading_view)
    View loadingView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        setupWidgets();
    }

    private void setupWidgets() {
        adapter = new NewsAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @NonNull
    @Override
    public NewsPresenter createPresenter() {
        return new NewsPresenter();
    }

    @Override
    public Observable<Boolean> newsIntent() {
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> pullToRefreshIntent() {
        return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout).map(remote -> true);
    }

    @Override
    public void render(NewsViewState state) {
        //news
        if (state instanceof NewsViewState.NewsLoading){
            renderLoading();
        } else if (state instanceof NewsViewState.NewsLoaded) {
            renderData(((NewsViewState.NewsLoaded) state).getData());
        }
        if (state instanceof NewsViewState.NewsLoadingError) {
            renderError((((NewsViewState.NewsLoadingError) state).getError()));
        }

        //pull to refresh
        if (state instanceof NewsViewState.PullToRefreshLoading) {
            renderSwipeLoading();
        } else if (state instanceof NewsViewState.PullToRefreshLoaded) {
            renderSwipeData(((NewsViewState.PullToRefreshLoaded) state).getData());
        }
        if (state instanceof NewsViewState.PullToRefreshLoadingError) {
            renderSwipeError(((NewsViewState.PullToRefreshLoadingError) state).getError());
        }
    }

    private void renderError(Throwable error) {
        loadingView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        Snackbar.make(getWindow().getDecorView().getRootView(), error.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    private void renderLoading(){
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    private void renderData(List<News> news){
        loadingView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        adapter.setNews(news);
    }

    private void renderSwipeError(Throwable error) {
        swipeRefreshLayout.setRefreshing(false);
        loadingView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        Snackbar.make(getWindow().getDecorView().getRootView(), error.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    private void renderSwipeLoading() {
        swipeRefreshLayout.setRefreshing(true);
        loadingView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    private void renderSwipeData(List<News> news) {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        loadingView.setVisibility(View.GONE);
        adapter.setNews(news);
    }
}
