package gusev.max.tinkoff_homework.screen.nodes_list;

import java.util.LinkedHashMap;

import gusev.max.tinkoff_homework.data.model.Node;
import gusev.max.tinkoff_homework.screen.base.BasePresenter;

/**
 * Created by v on 13/11/2017.
 */

class NodesListContract {

    interface View {

        /**
         * Show nodes after completed loading
         */
        void showNodes(LinkedHashMap<Node, Byte> nodes);

        /**
         * Clear data in view
         */
        void clearNodes();

        /**
         * Show error if there was problem at loading moment
         */
        void showErrorMessage(String error);

        /**
         * Start RelationsActivity for certain node
         */
        void showNodeRelations(Node node);

    }

    interface Presenter extends BasePresenter<NodesListContract.View> {

        /**
         * Load nodes list
         */
        void loadNodes();

        /**
         * Add new node
         */
        void addNode(int value);

        /**
         * Click on item(node) in recyclerView
         */
        void onItemClicked(Node node);

    }

}
