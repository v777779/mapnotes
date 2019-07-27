package ru.vpcb.test.map.activity.login.signup;

import androidx.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import ru.vpcb.test.map.base.ScopedPresenter;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.IAppExecutors;
import ru.vpcb.test.map.ext.ValidationExt;

public class SignUpPresenter extends ScopedPresenter<SignUpView> implements SignUpMvpPresenter {

    private IAppExecutors appExecutors;
    private UserRepository userRepository;
    private SignUpView view;
    private Disposable disposable;

    public SignUpPresenter(IAppExecutors appExecutors, UserRepository userRepository) {
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
        this.view = null;
        if(disposable!= null)disposable.dispose();
        super.onDetach();
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


        disposable = userRepository.signUp(email, password)
                .subscribe(result -> {
                    if (result instanceof Result.Success) {
                        userRepository.changeUserName(result.getData(), name);
                        view.navigateToMapScreen();
                    } else {
                        view.displaySignUpError();
                    }
                }, t -> {
                    view.displaySignUpError();
                });

    }


// Alternative

//        AppExecutors oldAppExecutors = new AppExecutors() {
//            @Override
//            public <T> void resume(Result<T> result) {
//                if (result instanceof Result.Success) {
//                    userRepository.changeUserName((AuthUser) result.getData(), name);
//                    view.navigateToMapScreen();
//                } else if (result instanceof Result.Error) {
//                    view.displaySignUpError();
//
//                }
//            }
//        };
//        userRepository.setAppExecutors(oldAppExecutors);
//        Result<AuthUser> result = userRepository.signUp(email, password);


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
//        oldAppExecutors = new AppExecutors() {
//            @Override
//            public <T> void resume(Result<T> result) {
//                if (result instanceof Result.Success) {
//// TODO_ launch
//                    oldAppExecutors = new AppExecutors() {
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
//                    userRepository.setAppExecutors(oldAppExecutors);
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
//        userRepository.setAppExecutors(oldAppExecutors);
//        Result<AuthUser> result = userRepository.signUp(email, password);
//
//    }
}

