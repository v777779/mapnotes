package ru.vpcb.test.map.executors;

import io.reactivex.Scheduler;

public interface IAppExecutors {
    Scheduler ui();
    Scheduler io();
    Scheduler computation();
    Scheduler net();

}
