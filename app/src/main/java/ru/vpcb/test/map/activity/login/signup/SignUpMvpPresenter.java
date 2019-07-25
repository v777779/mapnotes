package ru.vpcb.test.map.activity.login.signup;

import ru.vpcb.test.map.base.MvpPresenter;

public interface SignUpMvpPresenter extends MvpPresenter<SignUpView> {
    void signUp(String name, String email, String password);
}
