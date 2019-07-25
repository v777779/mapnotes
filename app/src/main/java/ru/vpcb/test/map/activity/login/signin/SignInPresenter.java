package ru.vpcb.test.map.activity.login.signin;

import androidx.annotation.NonNull;

import ru.vpcb.test.map.base.ScopedPresenter;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.ext.ValidationExt;
import ru.vpcb.test.map.model.AuthUser;

public class SignInPresenter extends ScopedPresenter<SignInView> implements SignInMvpPresenter {

    private AppExecutors appExecutors;
    private UserRepository userRepository;
    private SignInView view;

    public SignInPresenter(AppExecutors appExecutors, UserRepository userRepository) {
        this.appExecutors = appExecutors;
        this.userRepository = userRepository;
        this.view = null;
    }

    @Override
    public void onAttach(@NonNull SignInView view) {
        super.onAttach(view);
        this.view = view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.view = null;
    }

    @Override
    public void signIn(String email, String password) {

        if (view == null) return;
        if (email.isEmpty() || !ValidationExt.isValidEmail(email)) {
            view.displayEmailError();
            return;
        } else if (password.isEmpty()) {
            view.displayPasswordError();
            return;
        }

// TODO launch
        appExecutors = new AppExecutors() {
            @Override
            public <T> void resume(Result<T> result) {
                if (result instanceof Result.Success) {
                    view.navigateToMapScreen();
                } else if (result instanceof Result.Error) {
                    view.displaySignInError();

                }
            }
        };
        userRepository.setAppExecutors(appExecutors);
        Result<AuthUser> result = userRepository.signIn(email, password);


    }


}
