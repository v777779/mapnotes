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
import ru.vpcb.map.notes.di.activity.login.signin.SignInModule;
import ru.vpcb.map.notes.ext.NavigationExt;
import ru.vpcb.map.notes.manager.FAManager;

public class SignInActivity extends BaseActivity implements SignInView {
    private static final String TAG = "SignInActivityAAA";

    @Inject
    SignInMvpPresenter presenter;
    @Inject
    FAManager analyticManager;

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
            analyticManager.logEventLogin(email);
//            analyticManager.logEventImage("120000012", "SuperImageTask");  // works
        });

    }

    @Override
    public void setupComponent() {
        MainApp.get(this)
                .getSignInComponent(new SignInModule(this))
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
