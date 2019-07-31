package ru.vpcb.map.notes.di.activity.login;

import dagger.Subcomponent;
import ru.vpcb.map.notes.activity.login.signup.SignUpActivity;
import ru.vpcb.map.notes.di.activity.ActivityScope;

@ActivityScope
@Subcomponent
public interface LoginComponent {
    void inject(SignUpActivity activity);

}
