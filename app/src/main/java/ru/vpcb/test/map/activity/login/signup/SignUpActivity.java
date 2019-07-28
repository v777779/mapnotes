package ru.vpcb.test.map.activity.login.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.vpcb.test.map.MainApp;
import ru.vpcb.test.map.R;
import ru.vpcb.test.map.activity.BaseActivity;
import ru.vpcb.test.map.activity.home.HomeActivity;
import ru.vpcb.test.map.ext.NavigationExt;

public class SignUpActivity extends BaseActivity implements SignUpView {

    @Inject
    SignUpMvpPresenter presenter;

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
    public void setupComponent() {
        MainApp.get(this)
                .getSignUpComponent()
                .inject(this);
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
}
