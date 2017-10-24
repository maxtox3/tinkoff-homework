package gusev.max.tinkoff_homework.first;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by v on 23/10/2017.
 */

public class FirstTaskFragment extends Fragment {

    interface TaskCallbacks {

        void onPreExecute();

        void onCancelled();

        void onPostExecute(List<String> list);
    }

    private TaskCallbacks callbacks;
    private List<String> response;
    private MyTask task;

    //Fragment methods

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //Тема с фрагментами - колбэками очень помогла при решении этой задачи!
        callbacks = (TaskCallbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        prepareResponse();
        startTask();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    //FirstTaskFragment methods

    public void startTask(){
            task = new MyTask();
            task.execute();
    }

    private void prepareResponse() {
        response = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            response.add("chislo: " + i);
        }
    }

    //Стопаем таск, выкидывает InterruptedException
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
                try {
                    for (int i = 0; i < 5; i++) {
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
                callbacks.onPostExecute(response);

                //Если не обнулить ссылку, то при backPressed app помирает
                task = null;
            }
        }
    }

}
