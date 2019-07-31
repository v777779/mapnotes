package ru.vpcb.map.notes.executors;

import ru.vpcb.map.notes.data.Result;

public interface AppExecutors {
    <T> void resume(Result<T> result);

}
