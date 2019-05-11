package ru.vpcb.test.map.executors;

public interface IJob<T> {
    void join(T t);
}
