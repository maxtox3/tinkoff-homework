package gusev.max.tinkoff_homework.view.news;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.Observable;

/**
 * Created by v on 25/11/2017.
 */

public interface NewsView extends MvpView {

    /**
     * The intent to load news
     *
     * @return The emitted item boolean can be ignored because it is always true
     */
    Observable<Boolean> newsIntent();

    Observable<Boolean> pullToRefreshIntent();

    /**
     * Render the state on the view
     */
    void render(NewsViewState state);

}
