package ru.vpcb.map.notes.activity.login.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.BaseActivity;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.di.activity.login.signup.SignUpComponent;
import ru.vpcb.map.notes.di.activity.login.signup.SignUpModule;
import ru.vpcb.map.notes.ext.NavigationExt;
import ru.vpcb.map.notes.manager.FAManager;
import ru.vpcb.map.notes.manager.FCManager;

public class SignUpActivity extends BaseActivity implements SignUpView {

    @Inject
    SignUpMvpPresenter presenter;
    @Inject
    FAManager analyticsManager;

    private View signUpRoot;
    private EditText mEditName;
    private EditText mEditEmail;
    private EditText mEditPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpRoot = findViewById(android.R.id.content);
        mEditName = findViewById(R.id.name);
        mEditEmail = findViewById(R.id.email);
        mEditPass = findViewById(R.id.password);
        Button signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(v -> {
            String name = mEditName.getText().toString();
            String email = mEditEmail.getText().toString();
            String pass = mEditPass.getText().toString();
            presenter.signUp(name, email, pass);
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

    @Override
    public void setupComponent() {
        try {
            SignUpComponent.Builder builder =
                    (SignUpComponent.Builder) MainApp
                            .provider(this)
                            .provide(SignUpActivity.class);
            builder.module(new SignUpModule(this))
                    .build()
                    .inject(this);
        } catch (Exception e) {
            FCManager.log(e);
        }
    }

//  sign up view support

    @Override
    public void navigateToMapScreen() {
        NavigationExt.clearAndNavigateTo(this, HomeActivity.class);
    }

    @Override
    public void displayEmailError() {
        Snackbar.make(signUpRoot, R.string.error_email_should_be_valid, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displayPasswordError() {
        Snackbar.make(signUpRoot, R.string.error_password_should_not_be_empty, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displaySignUpError() {
        Snackbar.make(signUpRoot, R.string.error_account_cannot_be_created, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displayEmptyUserNameError() {
        Snackbar.make(signUpRoot, R.string.error_name_should_not_be_empty, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displayChangeUserNameError() {
        Snackbar.make(signUpRoot, R.string.error_name_not_changed, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void sendAnalytics(int id, String s) {
        if (analyticsManager == null) {
            return;
        }
        analyticsManager.logEventLogin(s);
    }
}
