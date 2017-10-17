package gusev.max.tinkoff_homework.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import gusev.max.tinkoff_homework.ActivityCallback;

/**
 * Created by v on 16/10/2017.
 */

public abstract class BaseFragment extends Fragment{

    protected ActivityCallback activityCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //Проверяем, что контейнер активити имплементировал
        //callback интерфейс. Если нет, кидаем exception
        try {
            activityCallback = (ActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement activityCallback");
        }
    }
}
