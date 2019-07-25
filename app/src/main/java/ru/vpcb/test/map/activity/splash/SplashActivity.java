package ru.vpcb.test.map.activity.splash;

import android.os.Bundle;
import android.util.Log;

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
//        appExecutors = null;
//        authRepository = new FirebaseUserRepository(appExecutors);

        if (isAuthenticated()) {
            NavigationExt.navigateTo(this, HomeActivity.class);
        } else {
            NavigationExt.navigateTo(this, LoginActivity.class);
        }
        finish();

    }

    @Override
    protected void setupComponent() {
        try {
            ((MainApp) getApplication()).getComponent()
                    .getSplashComponent()
                    .inject(this);

        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }

    private boolean isAuthenticated() {
        return (authRepository.getCurrentUser() instanceof Result.Success);
    }

}
