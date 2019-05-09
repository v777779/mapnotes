package ru.vpcb.test.map.login;

import android.support.annotation.NonNull;

public class LoginPresenter implements LoginMvpPresenter{
    private LoginView view;


    @Override
    public void openSignIn() {
        if(view == null)return;
        view.navigateToSignIn();
    }

    @Override
    public void openSignUp() {
        if(view == null)return;
        view.navigateToSignUp();
    }

    @Override
    public void onAttach(@NonNull LoginView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
