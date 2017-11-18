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
    private int typeOfRelation;
    private Node node;

    RelationsPresenter(RelationsContract.View view, Node node) {
        this.view = view;
        this.node = node;
        this.typeOfRelation = 0;

        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }

        disposeBag = new CompositeDisposable();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        loadRelations();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    @Override
    public void onItemClicked(long nodeId, boolean type, int typeOfRelation) {
        if (typeOfRelation == 0) {
            if (type) {
                removeRelation(node.getId(), nodeId);
            } else {
                addRelation(node.getId(), nodeId);
            }
        } else {
            if (type) {
                removeRelation(nodeId, node.getId());
            } else {
                addRelation(nodeId, node.getId());
            }
        }
    }

    @Override
    public void onChangeTypeOfRelation(int relationType) {
        this.typeOfRelation = relationType;
        loadRelations();
    }

    //Private methods

    /**
     * Load relations for certain node depending on the typeOfRelation
     */
    private void loadRelations() {
        view.clearRelations();

        if (typeOfRelation == 0) {
            Disposable disposable = model.getNodesWithChildRelations(node.getId())
                    .subscribe(this::handleLoadedRelations, this::handleError);
            disposeBag.add(disposable);
        } else {
            Disposable disposable = model.getNodesWithParentRelations(node.getId())
                    .subscribe(this::handleLoadedRelations, this::handleError);
            disposeBag.add(disposable);
        }
    }

    /**
     * add new relation between two nodes
     */
    private void addRelation(long nodeIdFirst, long nodeIdSecond) {
        view.clearRelations();

        Disposable disposable = model.addRelation(nodeIdFirst, nodeIdSecond)
                .doOnError(this::handleError)
                .doOnComplete(this::loadRelations)
                .subscribe();
        disposeBag.add(disposable);
    }

    /**
     * remove existing relation between two nodes
     */
    private void removeRelation(long nodeIdFirst, long nodeIdSecond) {
        view.clearRelations();

        Disposable disposable = model.removeRelation(nodeIdFirst, nodeIdSecond)
                .doOnError(this::handleError)
                .doOnComplete(this::loadRelations)
                .subscribe();
        disposeBag.add(disposable);
    }

    /**
     * Updates view after loading data is completed successfully.
     */
    private void handleLoadedRelations(LinkedHashMap<Node, Boolean> list) {
        if (list != null && !list.isEmpty()) {
            view.showRelations(list);
        }
    }

    /**
     * Updates view if there is an error after loading data from repository.
     */
    private void handleError(Throwable error) {
        view.showErrorMessage(error.getLocalizedMessage());
    }
}
