package ru.vpcb.test.map.di.activity.login.signin;

import dagger.Subcomponent;
import ru.vpcb.test.map.activity.login.signin.SignInActivity;
import ru.vpcb.test.map.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = SignInModule.class)
public interface SignInComponent {
    void inject(SignInActivity activity);

}
