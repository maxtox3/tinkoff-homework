package gusev.max.tinkoff_homework.screen.nodes_list;

import java.util.LinkedHashMap;

import gusev.max.tinkoff_homework.screen.base.BasePresenter;

/**
 * Created by v on 13/11/2017.
 */

class NodesListContract {

    interface View {

        void showNodes(LinkedHashMap<Integer, Byte> nodes);

        void clearNodes();

        void showNoDataMessage();

        void showErrorMessage(String error);

        void showNodeDetails(Integer nodeValue);

        void stopLoadingIndicator();

    }

    interface Presenter extends BasePresenter<NodesListContract.View> {

        void loadNodes();

        void getNode(long questionId);

        void addNode(int value);

        void search(String questionTitle);

    }

}
