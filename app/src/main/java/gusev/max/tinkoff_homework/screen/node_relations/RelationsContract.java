package gusev.max.tinkoff_homework.screen.node_relations;

import java.util.LinkedHashMap;

import gusev.max.tinkoff_homework.data.model.Node;
import gusev.max.tinkoff_homework.screen.base.BasePresenter;

/**
 * Created by v on 15/11/2017.
 */

public class RelationsContract {

    interface View {

        /**
         * Show relations after completed loading
         */
        void showRelations(LinkedHashMap<Node, Boolean> nodes);

        /**
         * Clear data in view
         */
        void clearRelations();

        /**
         * Show error if there was problem at loading moment
         */
        void showErrorMessage(String error);

    }

    interface Presenter extends BasePresenter<RelationsContract.View> {


        /**
         * Click on item(relation between two nodes) in recyclerView
         */
        void onItemClicked(long nodeId, boolean type, int typeOfRelation);

        /**
         * Chenga typeOdRelation between 2 nodes
         */
        void onChangeTypeOfRelation(int relationType);

    }
}
