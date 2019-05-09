package ru.vpcb.test.map.login.signin;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.vpcb.test.map.R;
import ru.vpcb.test.map.data.repository.FirebaseUserRepository;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.ext.NavigationExt;
import ru.vpcb.test.map.home.HomeActivity;

public class SignInActivity extends AppCompatActivity implements SignInView {

    private View mSignInRoot;
    private EditText mEditEmail;
    private EditText mEditPass;


    private SignInPresenter presenter;

    // TODO by inject
    private AppExecutors appExecutors = new AppExecutors();
    private UserRepository userRepository = new FirebaseUserRepository(appExecutors);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSignInRoot = findViewById(android.R.id.content);
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

        presenter = new SignInPresenter(appExecutors, userRepository);
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onDetach();
    }

// sing in view support
    @Override
    public void navigateToMapScreen() {
        NavigationExt.clearAndNavigateTo(this, HomeActivity.class);
    }

    @Override
    public void displayEmailError() {
        Snackbar.make(mSignInRoot, R.string.error_email_should_be_valid, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void displayPasswordError() {
        Snackbar.make(mSignInRoot, R.string.error_password_should_not_be_empty, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void displaySignInError() {
        Snackbar snackbar = Snackbar.make(mSignInRoot, R.string.error_user_cannot_be_authenticated, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.ok_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
