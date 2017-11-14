package gusev.max.tinkoff_homework.screen.nodes_list;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.LinkedHashMap;

import gusev.max.tinkoff_homework.data.Model;
import gusev.max.tinkoff_homework.data.ModelImpl;
import gusev.max.tinkoff_homework.data.model.Node;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by v on 13/11/2017.
 */

public class NodesListPresenter implements NodesListContract.Presenter, LifecycleObserver {

    private NodesListContract.View view;
    private CompositeDisposable disposeBag;
    private Model model = new ModelImpl();

    NodesListPresenter(NodesListContract.View view) {
        this.view = view;

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

        Disposable disposable = model.getFilteredNodes()
                .subscribe(this::handleLoadedNodes, this::handleError);
        disposeBag.add(disposable);
    }

    @Override
    public void getNode(long nodeId) {

    }

    @Override
    public void addNode(int value) {
        Disposable disposable = model.addNode(value)
                .doOnError(this::handleError)
                .doOnComplete(this::loadNodes)
                .subscribe();
        disposeBag.add(disposable);
    }

    @Override
    public void onItemClicked(long nodeId) {
        view.showNodeDetails(nodeId);
    }

    @Override
    public void search(String nodeValue) {

    }

    /**
     * Updates view after loading data is completed successfully.
     */
    private void handleLoadedNodes(LinkedHashMap<Node, Byte> list) {
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
        view.showErrorMessage(error.getLocalizedMessage());
    }
}
