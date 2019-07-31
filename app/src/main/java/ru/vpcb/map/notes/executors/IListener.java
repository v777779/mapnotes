package ru.vpcb.map.notes.executors;

public interface IListener<T> {
    void invoke(T t);
}
