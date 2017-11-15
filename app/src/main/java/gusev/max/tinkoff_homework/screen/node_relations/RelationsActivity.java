package gusev.max.tinkoff_homework.screen.node_relations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import gusev.max.tinkoff_homework.R;
import gusev.max.tinkoff_homework.data.model.Node;
import gusev.max.tinkoff_homework.screen.base.BaseActivity;

/**
 * Created by v on 14/11/2017.
 */

public class RelationsActivity extends BaseActivity implements RelationsContract.View {

    @BindView(R.id.nodes_relations_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    private static final String NODE_ID_KEY = "node_id_key";
    private RelationsAdapter adapter;
    private RelationsPresenter presenter;

    private long nodeId;

    public static void start(@NonNull Activity activity, long noteId) {
        Intent intent = new Intent(activity, RelationsActivity.class);
        intent.putExtra(NODE_ID_KEY, noteId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_relations);
        ButterKnife.bind(this);
        setupWidgets();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.children_button:
                    //todo при нажатии кнопки открывать обновлять ресайклер под определенную вкладку
                    break;

                case R.id.parent_button:
                    break;

                default:
                    break;
            }
            return true;
        });
    }

    private void setupWidgets() {
        nodeId = getIntent().getLongExtra(NODE_ID_KEY, 0);
        // Setup recycler view
        adapter = new RelationsAdapter(nodeId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter = new RelationsPresenter(this, nodeId);
        //todo dialog
        // adapter.setOnItemClickListener( (view, position) -> presenter.onItemClicked(adapter.getItem(position).getId()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showRelations(LinkedHashMap<Node, Boolean> nodes) {
        adapter.replaceData(nodes);
    }

    @Override
    public void clearRelations() {
        adapter.clearData();
    }

    @Override
    public void showNoDataMessage() {

    }

    @Override
    public void showErrorMessage(String error) {

    }
}
