package ru.vpcb.map.notes.activity.login.signin;

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
import ru.vpcb.map.notes.di.activity.login.signin.SignInComponent;
import ru.vpcb.map.notes.di.activity.login.signin.SignInModule;
import ru.vpcb.map.notes.ext.NavigationExt;
import ru.vpcb.map.notes.manager.FAManager;
import ru.vpcb.map.notes.manager.FCManager;

public class SignInActivity extends BaseActivity implements SignInView {

    @Inject
    SignInMvpPresenter presenter;
    @Inject
    FAManager analyticsManager;

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

        signIn.setOnClickListener(v -> {
            String email = mEditEmail.getText().toString();
            String pass = mEditPass.getText().toString();
            presenter.signIn(email, pass);

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
            SignInComponent.Builder builder =
                    (SignInComponent.Builder) MainApp
                            .provider(this)
                            .provide(SignInActivity.class);
            builder.module(new SignInModule(this))
                    .build()
                    .inject(this);
        } catch (Exception e) {
            FCManager.log(e);
        }
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
        snackbar.setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void sendAnalytics(String s) {   // works
        if (analyticsManager == null) {
            return;
        }
        analyticsManager.logEventLogin(s);
    }

    @Override
    public void sendAnalytics(int id, String s) {
        if (analyticsManager == null) {
            return;
        }
        analyticsManager.logEventImage(String.valueOf(id), s);
    }


}
