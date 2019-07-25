package ru.vpcb.test.map.executors;

import io.reactivex.Scheduler;
import ru.vpcb.test.map.data.Result;

public interface IAppExecutors {
    Scheduler ui();
    Scheduler io();
    Scheduler computation();

}
