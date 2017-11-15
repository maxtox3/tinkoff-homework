package gusev.max.tinkoff_homework.screen.node_relations;

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
 * Created by v on 15/11/2017.
 */

public class RelationsPresenter implements RelationsContract.Presenter, LifecycleObserver {

    private RelationsContract.View view;
    private CompositeDisposable disposeBag;
    private Model model = ModelImpl.getInstance();
    private long node;

    RelationsPresenter(RelationsContract.View view, long nodeId) {
        this.view = view;
        this.node = nodeId;

        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }

        disposeBag = new CompositeDisposable();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        loadChildRelations(node);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    @Override
    public void loadChildRelations(long nodeId) {
        view.clearRelations();

        Disposable disposable = model.getNodesWithChildRelations(nodeId)
                .subscribe(this::handleLoadedRelations, this::handleError);
        disposeBag.add(disposable);
    }

    @Override
    public void addRelation(long nodeIdFirst, long nodeIdSecond) {

    }

    @Override
    public void onItemClicked(long nodeId) {

    }

    /**
     * Updates view after loading data is completed successfully.
     */
    private void handleLoadedRelations(LinkedHashMap<Node, Boolean> list) {
        if (list != null && !list.isEmpty()) {
            view.showRelations(list);
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
