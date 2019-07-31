package ru.vpcb.map.notes.activity.splash;

import android.os.Bundle;

import javax.inject.Inject;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.activity.BaseActivity;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.activity.login.LoginActivity;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.ext.NavigationExt;


public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";

    @Inject
    UserRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isAuthenticated()) {
            NavigationExt.navigateTo(this, HomeActivity.class);
        } else {
            NavigationExt.navigateTo(this, LoginActivity.class);
        }
        finish();

//        analyticManager.setCurrentScreen(this, TAG);
    }

    @Override
    public void setupComponent() {
        MainApp.get(this)
                .getSplashComponent()
                .inject(this);

    }

    private boolean isAuthenticated() {
        return (authRepository.getCurrentUser() instanceof Result.Success);
    }

}
