package ru.vpcb.map.notes.di.activity.login.signup;

import dagger.Subcomponent;
import ru.vpcb.map.notes.activity.login.signup.SignUpActivity;
import ru.vpcb.map.notes.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = SignUpModule.class)
public interface SignUpComponent {
    void inject(SignUpActivity activity);
}
