package ru.vpcb.test.map.activity.login.signin;

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

public class SignInActivity extends BaseActivity implements SignInView {
    private static final String TAG = "SignInActivity";

    @Inject
    SignInMvpPresenter presenter;

    // new
    private View signInRoot;
    private EditText mEditEmail;
    private EditText mEditPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInRoot = findViewById(android.R.id.content);
        mEditEmail = findViewById(R.id.email);
        mEditPass = findViewById(R.id.password);
        Button signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEditEmail.getText().toString();
                String pass = mEditPass.getText().toString();
                presenter.signIn(email, pass);
            }
        });

    }

    @Override
    protected void setupComponent() {
        ((MainApp) getApplication()).getComponent()
                .getSignInComponent()
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

// sing in view support

    @Override
    public void navigateToMapScreen() {
        NavigationExt.clearAndNavigateTo(this, HomeActivity.class);
    }

    @Override
    public void displayEmailError() {
        Snackbar.make(signInRoot, R.string.error_email_should_be_valid, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void displayPasswordError() {
        Snackbar.make(signInRoot, R.string.error_password_should_not_be_empty, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void displaySignInError() {
        Snackbar snackbar = Snackbar.make(signInRoot, R.string.error_user_cannot_be_authenticated, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.ok_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
