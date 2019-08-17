package ru.vpcb.map.notes.activity.login.signup;

import androidx.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
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
    private CompositeDisposable composite;

    public SignUpPresenter(IAppExecutors appExecutors, UserRepository userRepository) {
        this.appExecutors = appExecutors;
        this.userRepository = userRepository;
        this.view = null;
    }

    @Override
    public void onAttach(@NonNull SignUpView view) {
        super.onAttach(view);
        this.view = view;
        composite = new CompositeDisposable();
    }

    @Override
    public void onDetach() {
        this.view = null;
        if (composite != null) {
            composite.dispose();
        }
        super.onDetach();
    }

    @Override
    public void signUp(String name, String email, String password) {
        if (view == null) return;
        if (email.isEmpty() || !ValidationExt.isValidEmail(email)) {
            view.displayEmailError();
            return;
        } else if (password.isEmpty()) {
            view.displayPasswordError();
            return;
        } else if (name.isEmpty()) {
            view.displayEmptyUserNameError();
            return;
        }

        view.sendAnalytics(-1, email);

        Disposable disposable = userRepository.signUp(email, password)
                .observeOn(appExecutors.ui())
                .subscribe(result -> {
                    if (result instanceof Result.Success) {
                        Disposable disposableChange = userRepository.changeUserName(result.getData(), name)
                                .observeOn(appExecutors.ui())
                                .subscribe(resultChange -> {
                                    if (resultChange instanceof Result.Success) {
                                        view.navigateToMapScreen();
                                    } else {
                                        view.displayChangeUserNameError();
                                    }
                                });
                        composite.add(disposableChange);
                    } else {
                        view.displaySignUpError();
                    }
                });
        composite.add(disposable);

    }



}

