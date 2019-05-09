package ru.vpcb.test.map.login;

import ru.vpcb.test.map.base.MvpView;

public interface LoginView extends MvpView {
    void navigateToSignIn();

    void navigateToSignUp();
}
