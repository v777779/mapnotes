package ru.vpcb.map.notes.activity.login;

import ru.vpcb.map.notes.base.MvpPresenter;

public interface LoginMvpPresenter extends MvpPresenter<LoginView> {
    void openSignIn();

    void openSignUp();
}
