package ru.vpcb.map.notes.activity.login;

import ru.vpcb.map.notes.base.MvpView;

public interface LoginView extends MvpView {
    void navigateToSignIn();

    void navigateToSignUp();
}
