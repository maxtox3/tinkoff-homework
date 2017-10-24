package gusev.max.tinkoff_homework.first;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import gusev.max.tinkoff_homework.R;

public class FirstTaskActivity extends AppCompatActivity implements FirstTaskFragment.TaskCallbacks {


    private RecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private Button btnSearch;
    private FirstTaskFragment firstTaskFragment;
    private boolean progressBarIsShowing;

    private static final String TAG_TASK_FRAGMENT = "task_fragment";

    //Activity methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_task);

        btnSearch = (Button) findViewById(R.id.search);
        progressBar = (ProgressBar) findViewById(R.id.pbSearch);

        prepareProgress(savedInstanceState);
        prepareRecyclerView();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                firstTaskFragment = (FirstTaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
                if (firstTaskFragment == null) {
                    firstTaskFragment = new FirstTaskFragment();
                    fm.beginTransaction().add(firstTaskFragment, TAG_TASK_FRAGMENT).commit();
                } else {
                    firstTaskFragment.startTask();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopProgress();
        if(firstTaskFragment != null) {
            firstTaskFragment.cancelTask();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (progressBarIsShowing) {
            outState.putBoolean("progressBarIsShowing", true);
        }
    }

    //FirstTaskActivity private methods

    private void prepareProgress(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("progressBarIsShowing")) {
            startProgress();
        } else {
            stopProgress();
        }
    }

    private void prepareRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void startProgress() {
        btnSearch.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        progressBarIsShowing = true;
    }

    private void stopProgress() {
        progressBar.setVisibility(View.GONE);
        btnSearch.setEnabled(true);
        progressBarIsShowing = false;
    }

    //TaskCallbacks implementation

    @Override
    public void onPreExecute() {
        startProgress();
    }

    @Override
    public void onCancelled() {
    }

    @Override
    public void onPostExecute(List<String> response) {
        stopProgress();
        adapter.setStringsList(response);
    }
}
