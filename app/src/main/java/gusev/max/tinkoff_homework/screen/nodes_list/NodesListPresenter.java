package gusev.max.tinkoff_homework.screen.nodes_list;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import java.util.LinkedHashMap;

import gusev.max.tinkoff_homework.data.db.Storage;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by v on 13/11/2017.
 */

public class NodesListPresenter implements NodesListContract.Presenter, LifecycleObserver {

    private NodesListContract.View view;
    private Storage storage;
    private CompositeDisposable disposeBag;

    public NodesListPresenter(NodesListContract.View view, Context context) {
        this.view = view;
        this.storage = Storage.getInstance(context);

        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }

        disposeBag = new CompositeDisposable();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        loadNodes();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    @Override
    public void loadNodes() {
        view.clearNodes();

        Disposable disposable = storage.getFilteredNodes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleReturnedData, this::handleError,
                        () -> view.stopLoadingIndicator());
        disposeBag.add(disposable);
    }

    @Override
    public void getNode(long questionId) {

    }

    @Override
    public void addNode(int value) {
        Disposable disposable = storage.addNode(value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(this::handleError)
                .doOnComplete(this::loadNodes)
                .subscribe();
        disposeBag.add(disposable);
    }

    @Override
    public void search(String questionTitle) {

    }

    /**
     * Updates view after loading data is completed successfully.
     */
    private void handleReturnedData(LinkedHashMap<Integer, Byte> list) {
        view.stopLoadingIndicator();
        if (list != null && !list.isEmpty()) {
            view.showNodes(list);
        } else {
            view.showNoDataMessage();
        }
    }

    /**
     * Updates view if there is an error after loading data from repository.
     */
    private void handleError(Throwable error) {
        view.stopLoadingIndicator();
        view.showErrorMessage(error.getLocalizedMessage());
    }
}
