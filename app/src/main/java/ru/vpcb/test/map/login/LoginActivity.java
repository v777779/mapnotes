package ru.vpcb.test.map.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.vpcb.test.map.R;
import ru.vpcb.test.map.ext.NavigationExt;
import ru.vpcb.test.map.login.signin.SignInActivity;
import ru.vpcb.test.map.login.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private LoginMvpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter();

        findViewById(R.id.signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openSignIn();

            }
        });

        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openSignUp();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        presenter.onDetach();
        super.onStop();
    }

// login view support

    @Override
    public void navigateToSignIn() {
        NavigationExt.navigateTo(this, SignInActivity.class);
    }

    @Override
    public void navigateToSignUp() {
        NavigationExt.navigateTo(this, SignUpActivity.class);
    }
}
