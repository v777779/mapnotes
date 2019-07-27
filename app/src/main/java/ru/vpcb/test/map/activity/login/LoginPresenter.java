package ru.vpcb.test.map.activity.login;

import androidx.annotation.NonNull;

public class LoginPresenter implements LoginMvpPresenter{
    private LoginView view;

    private LoginPresenter() {
    }

    private static class  LazyHolder {
        private static final LoginPresenter INSTANCE = new LoginPresenter();
    }

    public static LoginMvpPresenter getInstance(){
        return LazyHolder.INSTANCE;
    }


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
