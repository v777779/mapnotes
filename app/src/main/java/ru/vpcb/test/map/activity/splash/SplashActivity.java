package ru.vpcb.test.map.activity.splash;

import android.os.Bundle;

import javax.inject.Inject;

import ru.vpcb.test.map.MainApp;
import ru.vpcb.test.map.activity.BaseActivity;
import ru.vpcb.test.map.activity.home.HomeActivity;
import ru.vpcb.test.map.activity.login.LoginActivity;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.ext.NavigationExt;


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

    }

    @Override
    public void setupComponent(){
            MainApp.get(this)
                    .getSplashComponent()
                    .inject(this);

    }

    private boolean isAuthenticated() {
        return (authRepository.getCurrentUser() instanceof Result.Success);
    }

}
