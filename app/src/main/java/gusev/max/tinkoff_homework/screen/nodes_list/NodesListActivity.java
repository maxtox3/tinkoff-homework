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
import gusev.max.tinkoff_homework.screen.base.BaseActivity;

public class NodesListActivity extends BaseActivity implements NodesListContract.View, ActivityCallbacks {

    @BindView(R.id.nodes_list_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.fab_add_node)
    FloatingActionButton fab;

    private NodesListAdapter adapter;
    private NodesListPresenter presenter;
    private MyDialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes_list);
        ButterKnife.bind(this);
        setupWidgets();
    }

    private void setupWidgets() {
        //
        dialog = new MyDialogFragment();
        //fab
        fab.setOnClickListener(view -> dialog.show(getFragmentManager(), "dlg"));

        // Setup recycler view
        adapter = new NodesListAdapter(new LinkedHashMap<>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter = new NodesListPresenter(this, this);
        //adapter.setOnItemClickListener(

                //(view, position) -> presenter.getQuestion(adapter.getItem(position).getId()));
        //get all nodes
    }

    private void getNodes() {

    }

    @Override
    public void showNodes(LinkedHashMap<Integer, Byte> nodes) {
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
    public void showNodeDetails(Integer nodeValue) {

    }

    @Override
    public void stopLoadingIndicator() {

    }

    //ActivityCallbacks

    @Override
    public void addNode(int value) {
        presenter.addNode(value);
    }
}