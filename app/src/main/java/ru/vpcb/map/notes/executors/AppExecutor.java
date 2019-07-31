package ru.vpcb.map.notes.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppExecutor implements IAppExecutors {
    private static final int NETWORK_THREAD_POOL = 3;

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler net() {
        ExecutorService exec = Executors.newFixedThreadPool(NETWORK_THREAD_POOL);
        return Schedulers.from(exec);
    }
}
