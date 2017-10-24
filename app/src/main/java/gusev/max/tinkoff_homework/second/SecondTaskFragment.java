package gusev.max.tinkoff_homework.second;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by v on 24/10/2017.
 */

public class SecondTaskFragment extends Fragment {

    interface TaskCallbacks {

        void onPreExecute();

        void onCancelled();

        void onPostExecute(int i);
    }

    private TaskCallbacks callbacks;
    private MyTask task;
    private Handler h;

    //Fragment methods

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (TaskCallbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        startTask();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    //SecondTaskFragment methods

    public void startTask(){
        task = new MyTask();
        Handler.Callback hc = new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                callbacks.onPostExecute(msg.what);
                return false;
            }
        };
        h = new Handler(hc);
        task.execute();
    }

    public void cancelTask() {
        if (task == null) return;
        Log.i(getClass().getName(), "task canceled");
        task.cancel(true);
    }

    private class MyTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            if (callbacks != null) {
                callbacks.onPreExecute();
            }
        }

        @Override
        protected Void doInBackground(Void... ignore) {
            Log.i("secondFrag", "Start task of second frag");
            try {
                for (int i = 0; i < 3; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    if (isCancelled()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            if (callbacks != null) {
                callbacks.onCancelled();
            }
        }

        @Override
        protected void onPostExecute(Void ignore) {
            if (callbacks != null) {
                h.sendEmptyMessage(1);
                h.sendEmptyMessageDelayed(2, 5000);

                task = null;
            }
        }
    }
}
