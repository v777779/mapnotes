package ru.vpcb.test.map.activity.login.signin;

import ru.vpcb.test.map.base.MvpView;

public interface SignInView extends MvpView {
    void navigateToMapScreen();

    void displayEmailError();

    void displayPasswordError();

    void displaySignInError();
}
