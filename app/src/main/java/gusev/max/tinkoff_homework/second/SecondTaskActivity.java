package gusev.max.tinkoff_homework.second;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import gusev.max.tinkoff_homework.R;

public class SecondTaskActivity extends AppCompatActivity implements SecondTaskFragment.TaskCallbacks{

    private Button startBtn;
    private TextView textView;
    private ProgressBar progressBar;
    private SecondTaskFragment secondTaskFragment;

    private boolean progressBarIsShowing;
    private int result = 0;

    private static final String TAG_TASK_FRAGMENT = "task_fragment";

    //Activity methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_task);

        textView = (TextView) findViewById(R.id.second_part_text_view);
        progressBar = (ProgressBar) findViewById(R.id.pbStart);
        startBtn = (Button) findViewById(R.id.start_btn);

        prepareProgress(savedInstanceState);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                secondTaskFragment = (SecondTaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
                if (secondTaskFragment == null) {
                    secondTaskFragment = new SecondTaskFragment();
                    fm.beginTransaction().add(secondTaskFragment, TAG_TASK_FRAGMENT).commit();
                } else {
                    secondTaskFragment.startTask();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopProgress();
        if(secondTaskFragment != null) {
            secondTaskFragment.cancelTask();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (progressBarIsShowing) {
            outState.putBoolean("progressBarIsShowing", true);
            outState.putInt("result", result);
        }
    }

    //SecondTaskActivity private methods

    private void prepareProgress(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("progressBarIsShowing")) {
            startProgress();
            if(savedInstanceState.containsKey("result") && savedInstanceState.getInt("result") != 0){
                textView.setText(String.valueOf(savedInstanceState.getInt("result")));
            }
        } else {
            stopProgress();
        }
    }

    private void startProgress() {
        startBtn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        progressBarIsShowing = true;
    }

    private void stopProgress() {
        progressBar.setVisibility(View.GONE);
        startBtn.setEnabled(true);
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
    public void onPostExecute(int i) {
        result = i;
        textView.setText(String.valueOf(i));
        if(i == 2) {
            stopProgress();
        }
    }
}
