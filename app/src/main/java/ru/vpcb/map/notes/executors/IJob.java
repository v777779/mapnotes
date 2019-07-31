package ru.vpcb.map.notes.executors;

public interface IJob<T> {
    void join(T t);
}
