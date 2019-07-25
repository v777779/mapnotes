package ru.vpcb.test.map.activity.login.signin;

import ru.vpcb.test.map.base.MvpPresenter;

public interface SignInMvpPresenter extends MvpPresenter<SignInView> {
    void signIn(String email,String password);
}
