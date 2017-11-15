package gusev.max.tinkoff_homework.screen.nodes_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import gusev.max.tinkoff_homework.R;
import gusev.max.tinkoff_homework.data.model.Node;
import gusev.max.tinkoff_homework.screen.base.BaseActivity;
import gusev.max.tinkoff_homework.screen.node_relations.RelationsActivity;

public class NodesListActivity extends BaseActivity implements NodesListContract.View, NodeListActivityCallback {

    @BindView(R.id.nodes_list_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.fab_add_node)
    FloatingActionButton fab;

    private NodesListAdapter adapter;
    private NodesListPresenter presenter;
    private AddNodeDialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes_list);
        ButterKnife.bind(this);
        setupWidgets();
    }

    private void setupWidgets() {
        //
        dialog = new AddNodeDialogFragment();
        //fab
        fab.setOnClickListener(view -> dialog.show(getFragmentManager(), "dlg"));

        // Setup recycler view
        adapter = new NodesListAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter = new NodesListPresenter(this);
        adapter.setOnItemClickListener(
                (view, position) -> presenter.onItemClicked(adapter.getItem(position).getId()));
    }

    @Override
    public void showNodes(LinkedHashMap<Node, Byte> nodes) {
        adapter.replaceData(nodes);
    }

    @Override
    public void clearNodes() {
        adapter.clearData();
    }

    @Override
    public void showNoDataMessage() {

    }

    @Override
    public void showErrorMessage(String error) {

    }

    @Override
    public void showNodeDetails(long nodeId) {
        RelationsActivity.start(this, nodeId);
    }

    //ActivityCallbacks

    @Override
    public void addNode(int value) {
        presenter.addNode(value);
    }
}