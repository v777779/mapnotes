package ru.vpcb.map.notes.activity.login.signin;

import android.security.keystore.UserNotAuthenticatedException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.AuthUser;

@RunWith(RobolectricTestRunner.class)   // setup Robolectric  build.gradle, gradle.properties
@Config(
        sdk = 28,
        application = MainApp.class
)
public class SignInPresenterTests {
    private String authUserUID;
    private AuthUser authUser;
    private String correctEmail;
    private String incorrectEmail;
    private String emptyEmail;
    private String correctPassword;
    private String incorrectPassword;
    private String emptyPassword;

    @Mock
    private SignInView view;
    @Mock
    private IAppExecutors appExecutors;
    @Mock
    private UserRepository userRepository;

    private SignInPresenter presenter;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        authUserUID = "111111";
        authUser = new AuthUser(authUserUID);
        correctEmail = "test@test.com";
        incorrectEmail = "test";
        emptyEmail = "";
        correctPassword = "password";
        incorrectPassword = "incorrect password";
        emptyPassword = "";

        presenter = new SignInPresenter(appExecutors, userRepository);

        Mockito.when(appExecutors.ui()).then((Answer<Scheduler>) inv -> AndroidSchedulers.mainThread());
//        Mockito.when(appExecutors.net()).then((Answer<Scheduler>) inv -> AndroidSchedulers.mainThread());
//        Mockito.doAnswer(invocation -> null).when(view).displayEmailError();
//        Mockito.doAnswer(invocation -> null).when(view).displayPasswordError();
//        Mockito.doAnswer(invocation -> null).when(view).displaySignInError();
//        Mockito.doAnswer(invocation -> null).when(view).navigateToMapScreen();

    }

// 0    correct email, password

    @Test
    public void singInWithCorrectEmailPasswordWithNonNullViewNavigateToMapScreenCalled() {
        Mockito.when(userRepository.signIn(Mockito.any(), Mockito.any()))
                .thenReturn(Single.just(new Result.Success<>(authUser)));

        presenter.onAttach(view);
        presenter.signIn(correctEmail, correctPassword);

        Mockito.verify(view, Mockito.times(1)).navigateToMapScreen();
    }

    @Test
    public void singInWithCorrectEmailPasswordWithNullViewNavigateToMapScreenNotCalled() {
        Mockito.when(userRepository.signIn(Mockito.any(), Mockito.any()))
                .thenReturn(Single.just(new Result.Success<>(authUser)));
        presenter.onAttach(null);
        presenter.signIn(correctEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).navigateToMapScreen();
    }

    @Test
    public void singInWithCorrectEmailPasswordWithViewDetachedFromPresenterNavigateToMapScreenNotCalled() {
        Mockito.when(userRepository.signIn(Mockito.any(), Mockito.any()))
                .thenReturn(Single.just(new Result.Success<>(authUser)));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(correctEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).navigateToMapScreen();
    }

// 1    empty email, empty password

    @Test
    public void singInWithEmptyEmailPasswordWithNonNullViewDisplayEmailErrorCalled() {

        presenter.onAttach(view);
        presenter.signIn(emptyEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(1)).displayEmailError();
    }

    @Test
    public void singInWithEmptyEmailPasswordWithNullViewDisplayEmailErrorNotCalled() {

        presenter.onAttach(null);
        presenter.signIn(emptyEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();
    }

    @Test
    public void  singInWithEmptyEmailPasswordWithViewDetachedFromPresenterDisplayEmailErrorNotCalled() {

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(emptyEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();
    }

// 2    incorrect email, empty password

    @Test
    public void singInWithIncorrectEmailEmptyPasswordWithNonNullViewDisplayEmailErrorCalled() {

        presenter.onAttach(view);
        presenter.signIn(incorrectEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(1)).displayEmailError();
    }

    @Test
    public void singInWithIncorrectEmailEmptyPasswordWithNullViewDisplayEmailErrorNotCalled() {

        presenter.onAttach(null);
        presenter.signIn(incorrectEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();
    }

    @Test
    public void singInWithIncorrectEmailEmptyPasswordWithViewDetachedFromPresenterDisplayEmailErrorNotCalled() {

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(incorrectEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();
    }

// 3    correct email, empty password

    @Test
    public void singInWithCorrectEmailEmptyPasswordWithNonNullViewDisplayPasswordErrorCalled() {

        presenter.onAttach(view);
        presenter.signIn(correctEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(1)).displayPasswordError();
    }

    @Test
    public void singInWithCorrectEmailEmptyPasswordWithNullViewDisplayPasswordErrorNotCalled() {

        presenter.onAttach(null);
        presenter.signIn(correctEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayPasswordError();
    }

    @Test
    public void  singInWithCorrectEmailEmptyPasswordWithViewDetachedFromPresenterDisplayPasswordErrorNotCalled() {

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(correctEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayPasswordError();
    }

// 4    correct email incorrect password

    @Test
    public void singInWithCorrectEmailIncorrectPasswordWithNonNullViewDisplaySignInErrorCalled() {
        Mockito.when(userRepository.signIn(correctEmail, incorrectPassword))
                .thenReturn(Single.just(new Result.Error<>(new UserNotAuthenticatedException())));

        presenter.onAttach(view);
        presenter.signIn(correctEmail, incorrectPassword);

        Mockito.verify(view, Mockito.times(1)).displaySignInError();

    }

    @Test
    public void singInWithCorrectEmailIncorrectPasswordWithNullViewDisplaySignInErrorNotCalled() {
        Mockito.when(userRepository.signIn(correctEmail, incorrectPassword))
                .thenReturn(Single.just(new Result.Error<>(new UserNotAuthenticatedException())));

        presenter.onAttach(null);
        presenter.signIn(correctEmail, incorrectPassword);

        Mockito.verify(view, Mockito.times(0)).displaySignInError();
    }

    @Test
    public void singInWithCorrectEmailIncorrectPasswordWithViewDetachedToPresenterDisplaySignInErrorNotCalled() {
        Mockito.when(userRepository.signIn(correctEmail, incorrectPassword))
                .thenReturn(Single.just(new Result.Error<>(new UserNotAuthenticatedException())));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(correctEmail, incorrectPassword);

        Mockito.verify(view, Mockito.times(0)).displaySignInError();
    }

    @After
    public void tearDown() throws Exception {
        if (presenter != null) {
            presenter.onDetach();
        }
    }
}
