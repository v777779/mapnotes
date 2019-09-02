package ru.vpcb.map.notes.di;

public interface ActivityComponentBuilder<C extends ActivityComponent, M extends ActivityModule> {
    ActivityComponentBuilder<C,M> module(M module);
    C build();
}
