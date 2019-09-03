package ru.vpcb.map.notes.di.activity.login.signin;

import dagger.Subcomponent;
import ru.vpcb.map.notes.activity.login.signin.SignInActivity;
import ru.vpcb.map.notes.di.ActivityComponent;
import ru.vpcb.map.notes.di.ActivityComponentBuilder;
import ru.vpcb.map.notes.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = SignInModule.class)
public interface SignInComponent extends ActivityComponent<SignInActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<SignInComponent, SignInModule>{
    }

}
