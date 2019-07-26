package ru.vpcb.test.map.activity.login.signup;

import androidx.annotation.NonNull;

import ru.vpcb.test.map.base.ScopedPresenter;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.ext.ValidationExt;
import ru.vpcb.test.map.model.AuthUser;

public class SignUpPresenter extends ScopedPresenter<SignUpView> implements SignUpMvpPresenter {

    private AppExecutors appExecutors;
    private UserRepository userRepository;
    private SignUpView view;

    public SignUpPresenter(AppExecutors appExecutors, UserRepository userRepository) {
        this.appExecutors = appExecutors;
        this.userRepository = userRepository;
        this.view = null;
    }

    @Override
    public void onAttach(@NonNull SignUpView view) {
        super.onAttach(view);
        this.view = view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.view = null;
    }

    @Override
    public void signUp(String name, String email, String password) {
        if (view == null) return;
        if (email.isEmpty() || !ValidationExt.isValidEmail(email)) {
            view.displayEmailError();

        } else if (password.isEmpty()) {
            view.displayPasswordError();
        } else if (name.isEmpty()) {
            view.displayEmptyUserNameError();
        }

// TODO launch
        appExecutors = new AppExecutors() {
            @Override
            public <T> void resume(Result<T> result) {
                if (result instanceof Result.Success) {
// TODO launch continue
                    userRepository.changeUserName((AuthUser) result.getData(), name);
                    view.navigateToMapScreen();
                } else if (result instanceof Result.Error) {
                    view.displaySignUpError();

                }
            }
        };
        userRepository.setAppExecutors(appExecutors);
        Result<AuthUser> result = userRepository.signUp(email, password);

    }


// Alternative

//    public void signUp(String name, String email, String password) {
//        if (view == null) return;
//        if (email.isEmpty() || !ValidationExt.isValidEmail(email)) {
//            view.displayEmailError();
//
//        } else if (password.isEmpty()) {
//            view.displayPasswordError();
//        } else if (name.isEmpty()) {
//            view.displayEmptyUserNameError();
//        }
//
//// TODO_ launch
//        appExecutors = new AppExecutors() {
//            @Override
//            public <T> void resume(Result<T> result) {
//                if (result instanceof Result.Success) {
//// TODO_ launch
//                    appExecutors = new AppExecutors() {
//                        @Override
//                        public <T> void resume(Result<T> result) {
//                            if (result instanceof Result.Success) {
//                                view.navigateToMapScreen();
//                            }
//                            if (result instanceof Result.Error) {
//                                view.displaySignUpError();
//                            }
//                        }
//                    };
//                    userRepository.setAppExecutors(appExecutors);
//                    userRepository.changeUserName(((Result.Success<AuthUser>) result).getData(), name);
//
//
//                } else if (result instanceof Result.Error) {
//                    view.displaySignUpError();
//
//                }
//            }
//        };
//
//        userRepository.setAppExecutors(appExecutors);
//        Result<AuthUser> result = userRepository.signUp(email, password);
//
//    }
}
