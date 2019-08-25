package ru.vpcb.map.notes.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.vpcb.map.notes.MockTest;

@Singleton
@Component(modules = TestAppModule.class)
public interface TestAppComponent {

    void inject(MockTest mockTest);

}
