package gusev.max.tinkoff_homework.businesslogic;

import android.support.annotation.NonNull;

/**
 * Created by v on 27/11/2017.
 */

public final class RepositoryProvider {

    private static Repository repository;

    private RepositoryProvider() {}

    @NonNull
    public static Repository provideRepository() {
        if (repository == null) {
            repository = new RepositoryImpl();
        }
        return repository;
    }

    public static void init() {
        repository = new RepositoryImpl();
    }
}
