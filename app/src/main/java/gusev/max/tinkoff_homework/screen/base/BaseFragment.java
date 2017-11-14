package gusev.max.tinkoff_homework.screen.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.v4.app.Fragment;

/**
 * Created by v on 14/11/2017.
 */

public class BaseFragment extends Fragment {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

}
