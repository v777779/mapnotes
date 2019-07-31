package ru.vpcb.map.notes.activity.login.signin;

import ru.vpcb.map.notes.base.MvpPresenter;

public interface SignInMvpPresenter extends MvpPresenter<SignInView> {
    void signIn(String email,String password);
}
