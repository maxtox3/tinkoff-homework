package gusev.max.tinkoff_homework.screen.node_relations;

import java.util.LinkedHashMap;

import gusev.max.tinkoff_homework.data.model.Node;
import gusev.max.tinkoff_homework.screen.base.BasePresenter;

/**
 * Created by v on 15/11/2017.
 */

public class RelationsContract {

    interface View {

        void showRelations(LinkedHashMap<Node, Boolean> nodes);

        void clearRelations();

        void showNoDataMessage();

        void showErrorMessage(String error);

    }

    interface Presenter extends BasePresenter<RelationsContract.View> {

        void loadChildRelations(long nodeId);

        void addRelation(long nodeIdFirst, long nodeIdSecond);

        void onItemClicked(long nodeId);

    }
}
