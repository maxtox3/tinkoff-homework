package gusev.max.tinkoff_homework.data;

import java.util.LinkedHashMap;

import gusev.max.tinkoff_homework.data.db.Storage;
import gusev.max.tinkoff_homework.data.model.Node;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by v on 14/11/2017.
 */

public class ModelImpl implements Model{

    private static ModelImpl INSTANCE;

    public static synchronized ModelImpl getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new ModelImpl();
        }
        return INSTANCE;
    }

    private Storage storage = Storage.getInstance();

    @Override
    public Single<LinkedHashMap<Node, Byte>> getFilteredNodes() {
        return storage.getFilteredNodes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable addNode(int value) {
        return storage.addNode(value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<LinkedHashMap<Node, Boolean>> getNodesWithChildRelations(long parentNodeId) {
        return storage.getNodesWithChildRelations(parentNodeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<LinkedHashMap<Node, Boolean>> getNodesWithParentRelations(long childNodeId) {
        return storage.getNodesWithParentRelations(childNodeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable addRelation(long nodeIdFirst, long nodeIdSecond) {
        return storage.addRelation(nodeIdFirst, nodeIdSecond)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable removeRelation(long nodeIdFirst, long nodeIdSecond) {
        return storage.removeRelation(nodeIdFirst, nodeIdSecond)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
