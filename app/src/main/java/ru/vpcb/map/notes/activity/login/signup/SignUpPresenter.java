package ru.vpcb.map.notes.activity.login.signup;

import androidx.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import ru.vpcb.map.notes.base.ScopedPresenter;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.ext.ValidationExt;

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

        view.sendAnalytics(-1, email);

        disposable = userRepository.signUp(email, password)
                .observeOn(appExecutors.ui())
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

