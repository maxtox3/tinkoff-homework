package gusev.max.tinkoff_homework.data;

import java.util.LinkedHashMap;

import gusev.max.tinkoff_homework.data.model.Node;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by v on 14/11/2017.
 */

public interface Model {

    Single<LinkedHashMap<Node, Byte>> getFilteredNodes();

    Completable addNode(int value);

    Single<LinkedHashMap<Node, Boolean>> getNodesWithChildRelations(long parentNodeId);

    Single<LinkedHashMap<Node, Boolean>> getNodesWithParentRelations(long childNodeId);

}
