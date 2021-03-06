package ru.vpcb.map.notes.activity.login;

import android.os.Bundle;
import android.view.View;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.BaseActivity;
import ru.vpcb.map.notes.activity.login.signin.SignInActivity;
import ru.vpcb.map.notes.activity.login.signup.SignUpActivity;
import ru.vpcb.map.notes.ext.NavigationExt;

public class LoginActivity extends BaseActivity implements LoginView {
    private static final String TAG = "LoginActivity";
    private LoginMvpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = LoginPresenter.getInstance();  // Lazy Singleton в качестве демо

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
