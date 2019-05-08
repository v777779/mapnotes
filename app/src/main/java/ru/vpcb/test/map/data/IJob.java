package ru.vpcb.test.map.data;

import ru.vpcb.test.map.model.Note;

public interface IJob<T> {
    void join(T t);
}
