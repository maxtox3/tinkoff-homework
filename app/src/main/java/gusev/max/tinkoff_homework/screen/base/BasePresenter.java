package gusev.max.tinkoff_homework.screen.base;

/**
 * Created by v on 13/11/2017.
 */

public interface BasePresenter<V>{

    /**
     * When presenter attaches to activity (for lifecycle)
     */
    void onAttach();

    /**
     * When presenter detaches to activity (for lifecycle)
     */
    void onDetach();
}