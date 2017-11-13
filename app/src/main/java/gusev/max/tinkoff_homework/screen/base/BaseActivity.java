package gusev.max.tinkoff_homework.screen.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by v on 13/11/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
