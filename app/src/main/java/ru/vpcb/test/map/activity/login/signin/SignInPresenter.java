package ru.vpcb.test.map.activity.login.signin;

import androidx.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.vpcb.test.map.base.ScopedPresenter;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.executors.IAppExecutors;
import ru.vpcb.test.map.ext.ValidationExt;

public class SignInPresenter extends ScopedPresenter<SignInView> implements SignInMvpPresenter {

    private IAppExecutors appExecutors;
    private AppExecutors oldAppExecutors;
    private UserRepository userRepository;
    private SignInView view;
    private CompositeDisposable compositeDisposable;

    public SignInPresenter(IAppExecutors appExecutors, UserRepository userRepository) {
        this.appExecutors = appExecutors;
//        this.oldAppExecutors = appExecutors;
        this.userRepository = userRepository;
        this.view = null;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(@NonNull SignInView view) {
        super.onAttach(view);
        this.view = view;
    }

    @Override
    public void onDetach() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        this.view = null;
        super.onDetach();
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
        oldAppExecutors = new AppExecutors() {
            @Override
            public <T> void resume(Result<T> result) {
                if (result instanceof Result.Success) {
                    view.navigateToMapScreen();
                } else if (result instanceof Result.Error) {
                    view.displaySignInError();

                }
            }
        };

//        userRepository.setAppExecutors(oldAppExecutors);
        Disposable disposable = userRepository.signIn(email, password)
                .subscribe(result -> {
                    view.navigateToMapScreen();
                }, t -> {
                    view.displaySignInError();
                });

        compositeDisposable.add(disposable);

    }


}
