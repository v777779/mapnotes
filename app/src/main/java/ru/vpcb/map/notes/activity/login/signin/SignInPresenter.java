package ru.vpcb.map.notes.activity.login.signin;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import ru.vpcb.map.notes.base.ScopedPresenter;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.ext.ValidationExt;

public class SignInPresenter extends ScopedPresenter<SignInView> implements SignInMvpPresenter {

    private IAppExecutors appExecutors;
    private UserRepository userRepository;
    private SignInView view;
    private Disposable disposable;

    public SignInPresenter(IAppExecutors appExecutors, UserRepository userRepository) {
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
        this.view = null;
        if (disposable != null)
            disposable.dispose();
        super.onDetach();
    }

    @SuppressLint("CheckResult")
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

        view.sendAnalytics(email);
        view.sendAnalytics(120000012, "ImageValue");  // works

        disposable = userRepository.signIn(email, password)
                .observeOn(appExecutors.ui())
                .subscribe(result -> {
                    if (result instanceof Result.Success)
                        view.navigateToMapScreen();
                    else
                        view.displaySignInError();
                }, t -> {
                    view.displaySignInError();
                });

    }

}
