package ru.vpcb.test.map.login.signin;

import ru.vpcb.test.map.base.MvpView;

public interface SignInView extends MvpView {
    void navigateToMapScreen();

    void displayEmailError();

    void displayPasswordError();

    void displaySignInError();
}
