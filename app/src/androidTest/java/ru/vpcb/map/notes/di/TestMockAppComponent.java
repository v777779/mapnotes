package ru.vpcb.map.notes.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.vpcb.map.notes.MockTest;

@Singleton
@Component(modules = TestMockAppModule.class)
public interface TestMockAppComponent {

    void inject(MockTest mockTest);

}
