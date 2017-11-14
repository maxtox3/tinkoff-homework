package gusev.max.tinkoff_homework.screen.nodes_relations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import gusev.max.tinkoff_homework.R;
import gusev.max.tinkoff_homework.screen.base.BaseActivity;

/**
 * Created by v on 14/11/2017.
 */

public class NodeRelationsActivity extends BaseActivity {

    private static final String NODE_ID_KEY = "repo_name_key";
    private static String TAG = "NodeRelationsActivity";
    private static String CHILDREN = "children";
    private static String PARENTS = "parents";

    private FragmentManager fragmentManager;

    private long nodeId;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    public static void start(@NonNull Activity activity, long noteId) {
        Intent intent = new Intent(activity, NodeRelationsActivity.class);
        intent.putExtra(NODE_ID_KEY, noteId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_relations);
        ButterKnife.bind(this);

        nodeId = getIntent().getLongExtra(NODE_ID_KEY, 0);

        fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putLong(NODE_ID_KEY, nodeId);

        setFragment(CHILDREN, bundle);

        bottomNavigationView.setOnNavigationItemSelectedListener( item -> {

            switch (item.getItemId()) {
                case R.id.children_button :
                    setFragment(CHILDREN, bundle);
                    break;

                case R.id.parent_button :
                    setFragment(PARENTS, bundle);
                    break;

                default:
                    break;
            }
            return true;
        });
    }

    private Fragment createFragment(String fragmentName) {
        Fragment fragment = null;

        switch (fragmentName) {
            case "children":
                fragment = new ChildrenFragment();
                break;

            case "parents":
                fragment = new ParentsFragment();
                break;

            default:
                break;
        }
        return fragment;
    }

    public void setFragment(String fragmentName, Bundle args) {

        if (fragmentManager.findFragmentById(R.id.container) == null) {
            Fragment fragment = createFragment(fragmentName);
            if (args != null) {
                fragment.setArguments(args);
            }
            Log.i(TAG, "Add fragment");
            if (!fragment.isAdded()) {
                replaceFragment(fragment, fragmentName);
            }
        } else {
            Fragment fragment = fragmentManager.findFragmentByTag(fragmentName);
            if (fragment == null) {
                Log.i(TAG, "Create fragment");
                fragment = createFragment(fragmentName);
                if (args != null) {
                    fragment.setArguments(args);
                }
                replaceFragment(fragment, fragmentName);
            } else {
                Log.i(TAG, "Reuse fragment");
                if (args != null) {
                    fragment.setArguments(args);
                }
                if (!fragment.isAdded()) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.container, fragment, fragmentName)
                            .commit();
                }
            }
        }
    }

    private void replaceFragment(Fragment fragment, String fragmentName){
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, fragmentName)
                .addToBackStack(fragmentName)
                .commit();
    }
}
