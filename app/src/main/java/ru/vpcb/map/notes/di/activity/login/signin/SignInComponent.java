package ru.vpcb.map.notes.di.activity.login.signin;

import dagger.Subcomponent;
import ru.vpcb.map.notes.activity.login.signin.SignInActivity;
import ru.vpcb.map.notes.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = SignInModule.class)
public interface SignInComponent {
    void inject(SignInActivity activity);

}
