package ru.vpcb.map.notes.executors;

public interface IConsumer<T> {
    void accept(T t);
}
