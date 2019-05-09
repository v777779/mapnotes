package ru.vpcb.test.map.login;

import ru.vpcb.test.map.base.MvpPresenter;

public interface LoginMvpPresenter extends MvpPresenter<LoginView> {
    void openSignIn();

    void openSignUp();
}
