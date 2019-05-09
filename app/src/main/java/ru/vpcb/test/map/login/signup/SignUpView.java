package ru.vpcb.test.map.login.signup;

import ru.vpcb.test.map.base.MvpView;

public interface SignUpView extends MvpView {
    void navigateToMapScreen();

    void displayEmailError();

    void displayPasswordError();

    void displaySignUpError();

    void displayEmptyUserNameError();
}
