package ru.vpcb.map.notes.activity.login.signup;

import ru.vpcb.map.notes.base.MvpPresenter;

public interface SignUpMvpPresenter extends MvpPresenter<SignUpView> {
    void signUp(String name, String email, String password);
}
