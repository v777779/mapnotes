package ru.vpcb.test.map.di.activity.login;

import dagger.Subcomponent;
import ru.vpcb.test.map.activity.login.LoginActivity;
import ru.vpcb.test.map.activity.login.signin.SignInActivity;
import ru.vpcb.test.map.activity.login.signup.SignUpActivity;
import ru.vpcb.test.map.di.activity.ActivityScope;

@ActivityScope
@Subcomponent
public interface LoginComponent {
    void inject(SignInActivity activity);
    void inject(SignUpActivity activity);
    void inject(LoginActivity activity);

}
