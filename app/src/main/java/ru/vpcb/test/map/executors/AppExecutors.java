package ru.vpcb.test.map.executors;

import ru.vpcb.test.map.data.Result;

public interface AppExecutors {
    <T> void resume(Result<T> result);

}
