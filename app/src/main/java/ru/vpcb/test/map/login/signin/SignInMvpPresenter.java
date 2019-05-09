package ru.vpcb.test.map.login.signin;

import ru.vpcb.test.map.base.MvpPresenter;

public interface SignInMvpPresenter extends MvpPresenter<SignInView> {
    void signIn(String email,String password);
}
