package ru.vpcb.map.notes.activity.login.signup;

import ru.vpcb.map.notes.base.MvpView;

public interface SignUpView extends MvpView {
    void navigateToMapScreen();

    void displayEmailError();

    void displayPasswordError();

    void displaySignUpError();

    void displayEmptyUserNameError();

    void sendAnalytics(int id, String s);

}
