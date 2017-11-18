package gusev.max.tinkoff_homework.screen.node_relations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class RelationsActivity extends BaseActivity implements RelationsContract.View, RelationDialogFragment.RelationsActivityCallback {

    @BindView(R.id.nodes_relations_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    private static final String NODE_ID_KEY = "node_id_key";
    private RelationsAdapter adapter;
    private RelationsPresenter presenter;

    private static Node node;

    public static void start(@NonNull Activity activity, Node node) {
        Intent intent = new Intent(activity, RelationsActivity.class);
        intent.putExtra(NODE_ID_KEY, node);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_relations);
        ButterKnife.bind(this);
        setupWidgets();

    }

    private void setupWidgets() {

        node = (Node) getIntent().getSerializableExtra(NODE_ID_KEY);

        // Setup recycler view
        adapter = new RelationsAdapter(node);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //init presenter
        presenter = new RelationsPresenter(this, node);

        //set bottom nav
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.children_button:
                    presenter.onChangeTypeOfRelation(0);
                    adapter.changeTypeOfRelation(0);
                    break;

                case R.id.parent_button:
                    presenter.onChangeTypeOfRelation(1);
                    adapter.changeTypeOfRelation(1);
                    break;

                default:
                    break;
            }
            return true;
        });

        //set adapter
        adapter.setOnItemClickListener(
                (view, position) -> showDialog(
                        adapter.getItem(position).getId(),
                        adapter.getType(position)
                )
        );


    }

    void showDialog(long node, boolean type) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("relation_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = RelationDialogFragment.newInstance(node, type);
        newFragment.show(ft, "dialog");
    }

    //Presenter

    @Override
    public void showRelations(LinkedHashMap<Node, Boolean> nodes) {
        adapter.replaceData(nodes);
    }

    @Override
    public void clearRelations() {
        adapter.clearData();
    }

    @Override
    public void showErrorMessage(String error) {

    }

    //Dialog fragment

    @Override
    public void onClickOkButton(long node, boolean type) {
        presenter.onItemClicked(node, type, adapter.getTypeOfRelation());
    }
}
