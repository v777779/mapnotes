package ru.vpcb.test.map.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.repository.FirebaseUserRepository;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.ext.NavigationExt;
import ru.vpcb.test.map.home.HomeActivity;
import ru.vpcb.test.map.login.LoginActivity;


public class SplashActivity extends AppCompatActivity {

    private AppExecutors appExecutors;
    private UserRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appExecutors = null;
        authRepository = new FirebaseUserRepository(appExecutors);

        if (isAuthenticated()) {
            NavigationExt.navigateTo(this, HomeActivity.class);
        } else {
            NavigationExt.navigateTo(this, LoginActivity.class);
        }
        finish();

    }

    private boolean isAuthenticated() {
        return (authRepository.getCurrentUser() instanceof Result.Success);
    }

}
