package ru.vpcb.map.notes.activity.login.signin;

import ru.vpcb.map.notes.base.MvpView;

public interface SignInView extends MvpView {
    void navigateToMapScreen();

    void displayEmailError();

    void displayPasswordError();

    void displaySignInError();

    void sendAnalytics(String s);

    void sendAnalytics(int id, String s);

}
