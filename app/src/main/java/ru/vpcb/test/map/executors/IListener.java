package ru.vpcb.test.map.executors;

import ru.vpcb.test.map.model.Location;

public interface IListener<T> {
    void invoke(T t);
}
