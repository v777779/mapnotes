package ru.vpcb.test.map.di.activity.login.signup;

import dagger.Subcomponent;
import ru.vpcb.test.map.activity.login.signup.SignUpActivity;
import ru.vpcb.test.map.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = SignUpModule.class)
public interface SignUpComponent {
    void inject(SignUpActivity activity);
}
