package ru.vpcb.test.map;

import ru.vpcb.test.map.data.Result;

public interface IExecutors {
    <T> void resume(Result<T> result);
}
