package ru.vpcb.map.notes.login.signin;

import android.security.keystore.UserNotAuthenticatedException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.activity.login.signin.SignInPresenter;
import ru.vpcb.map.notes.activity.login.signin.SignInView;
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
    private String correctUserName;
    private String incorrectUserName;
    private String emptyUserName;
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
        correctUserName = "test@test.com";
        incorrectUserName = "test";
        emptyUserName = "";
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

    // correct login and incorrect password
    @Test
    public void verifySingInWithCorrectLoginAndIncorrectPasswordNonNullViewAttachedToPresenter() {
        Mockito.when(userRepository.signIn(correctUserName, incorrectPassword))
                .thenReturn(Single.just(new Result.Error<>(new UserNotAuthenticatedException())));

        presenter.onAttach(view);
        presenter.signIn(correctUserName, incorrectPassword);

        Mockito.verify(view, Mockito.times(1)).displaySignInError();

    }

    @Test
    public void verifySingInWithCorrectLoginAndIncorrectPasswordNullViewAttachedToPresenter() {
        Mockito.when(userRepository.signIn(correctUserName, incorrectPassword))
                .thenReturn(Single.just(new Result.Error<>(new UserNotAuthenticatedException())));

        presenter.onAttach(null);
        presenter.signIn(correctUserName, incorrectPassword);

        Mockito.verify(view, Mockito.times(0)).displaySignInError();
    }

    @Test
    public void verifySingInWithCorrectLoginAndIncorrectPasswordViewDetachedToPresenter() {
        Mockito.when(userRepository.signIn(correctUserName, incorrectPassword))
                .thenReturn(Single.just(new Result.Error<>(new UserNotAuthenticatedException())));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(correctUserName, incorrectPassword);

        Mockito.verify(view, Mockito.times(0)).displaySignInError();
    }

// correct login and empty password

    @Test
    public void verifySingInWithCorrectLoginAndEmptyPasswordNonNullViewAttachedToPresenter() {

        presenter.onAttach(view);
        presenter.signIn(correctUserName, emptyPassword);

        Mockito.verify(view, Mockito.times(1)).displayPasswordError();
    }

    @Test
    public void verifySingInWithCorrectLoginAndEmptyPasswordNullViewAttachedToPresenter() {

        presenter.onAttach(null);
        presenter.signIn(correctUserName, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayPasswordError();
    }

    @Test
    public void verifySingInWithCorrectLoginAndEmptyPasswordViewDetachedFromPresenter() {

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(correctUserName, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayPasswordError();
    }

// correct login and empty password

    @Test
    public void verifySingInWithCorrectLoginAndPasswordNonNullViewAttachedToPresenter() {
        Mockito.when(userRepository.signIn(Mockito.any(), Mockito.any()))
                .thenReturn(Single.just(new Result.Success<>(authUser)));

        presenter.onAttach(view);
        presenter.signIn(correctUserName, correctPassword);

        Mockito.verify(view, Mockito.times(1)).navigateToMapScreen();
    }

    @Test
    public void verifySingInWithCorrectLoginAndPasswordNullViewAttachedToPresenter() {
        Mockito.when(userRepository.signIn(Mockito.any(), Mockito.any()))
                .thenReturn(Single.just(new Result.Success<>(authUser)));
        presenter.onAttach(null);
        presenter.signIn(correctUserName, correctPassword);

        Mockito.verify(view, Mockito.times(0)).navigateToMapScreen();
    }

    @Test
    public void verifySingInWithCorrectLoginAndPasswordViewDetachedFromPresenter() {
        Mockito.when(userRepository.signIn(Mockito.any(), Mockito.any()))
                .thenReturn(Single.just(new Result.Success<>(authUser)));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(correctUserName, correctPassword);

        Mockito.verify(view, Mockito.times(0)).navigateToMapScreen();
    }

// incorrect login and empty password

    @Test
    public void verifySingInWithIncorrectLoginAndEmptyPasswordNonNullViewAttachedToPresenter() {

        presenter.onAttach(view);
        presenter.signIn(incorrectUserName, emptyPassword);

        Mockito.verify(view, Mockito.times(1)).displayEmailError();
    }

    @Test
    public void verifySingInWithIncorrectLoginAndEmptyPasswordNullViewAttachedToPresenter() {

        presenter.onAttach(null);
        presenter.signIn(incorrectUserName, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();
    }

    @Test
    public void verifySingInWithIncorrectLoginAndEmptyPasswordViewDetachedFromPresenter() {

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(incorrectUserName, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();
    }

// empty login and incorrect password

    @Test
    public void verifySingInWithEmptyLoginAndPasswordNonNullViewAttachedToPresenter() {

        presenter.onAttach(view);
        presenter.signIn(emptyUserName, emptyPassword);

        Mockito.verify(view, Mockito.times(1)).displayEmailError();
    }

    @Test
    public void verifySingInWithEmptyLoginAndPasswordNullViewAttachedToPresenter() {

        presenter.onAttach(null);
        presenter.signIn(emptyUserName, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();
    }

    @Test
    public void verifySingInWithEmptyLoginAndPasswordViewDetachedFromPresenter() {

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signIn(emptyUserName, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();
    }


    @After
    public void tearDown() throws Exception {
        if (presenter != null) {
            presenter.onDetach();
        }
    }
}
