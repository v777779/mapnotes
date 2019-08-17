package ru.vpcb.map.notes.login.signup;

import android.security.keystore.UserNotAuthenticatedException;

import com.google.firebase.database.DatabaseException;

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
import ru.vpcb.map.notes.activity.login.signup.SignUpMvpPresenter;
import ru.vpcb.map.notes.activity.login.signup.SignUpPresenter;
import ru.vpcb.map.notes.activity.login.signup.SignUpView;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.AuthUser;

@RunWith(RobolectricTestRunner.class)   // setup Robolectric  build.gradle, gradle.properties
@Config(
        sdk = 28,
        application = MainApp.class
)
public class SignUIpPresenterTests {
    private String authUserUID;
    private AuthUser authUser;
    private String correctUserName;
    private String emptyUserName;
    private String correctEmail;
    private String incorrectEmail;
    private String emptyEmail;
    private String correctPassword;
    private String emptyPassword;
    private String errorMessage;

    @Mock
    private SignUpView view;
    @Mock
    private IAppExecutors appExecutors;
    @Mock
    private UserRepository userRepository;

    private SignUpMvpPresenter presenter;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        authUserUID = "111111";
        authUser = new AuthUser(authUserUID);
        correctUserName = "TestUser";
        emptyUserName = "";
        correctEmail = "test@test.com";
        incorrectEmail = "test";
        emptyEmail = "";
        correctPassword = "correctPassword";
        emptyPassword = "";
        errorMessage = "The email address is already in use by another account.";
        presenter = new SignUpPresenter(appExecutors, userRepository);

        Mockito.when(appExecutors.ui()).then(
                (Answer<Scheduler>) inv -> AndroidSchedulers.mainThread());
        Mockito.when(appExecutors.net()).then(
                (Answer<Scheduler>) inv -> AndroidSchedulers.mainThread());

        Mockito.doAnswer(invocation -> null).when(view).displaySignUpError();
        Mockito.doAnswer(invocation -> null).when(view).displayEmptyUserNameError();
        Mockito.doAnswer(invocation -> null).when(view).displayEmailError();
        Mockito.doAnswer(invocation -> null).when(view).displayPasswordError();
        Mockito.doAnswer(invocation -> null).when(view).navigateToMapScreen();

        Mockito.when(userRepository.signUp(correctEmail, correctPassword))
                .thenReturn(Single.just(new Result.Success<>(authUser)));

        Mockito.when(userRepository.changeUserName(authUser,correctUserName))
                .thenReturn(Single.just(new Result.Success<>( null)));
//        Mockito.when(userRepository.changeUserName(authUser,correctUserName))
//                .thenReturn(Single.just(new Result.Success<>("12")));

    }

    // 0 correct all
    @Test
    public void verifySingUpWithCorrectEmailPasswordAndNameNonNullViewAttachedToPresenter() {
        presenter.onAttach(view);
        presenter.signUp(correctUserName, correctEmail, correctPassword);

        Mockito.verify(userRepository, Mockito.times(1))
                .signUp(correctEmail, correctPassword);
        Mockito.verify(userRepository, Mockito.times(1))
                .changeUserName(authUser, correctUserName);

        Mockito.verify(view, Mockito.times(1))
                .navigateToMapScreen();

    }

    @Test
    public void verifySingUpWithCorrectEmailPasswordAndNameNullViewAttachedToPresenter() {
        presenter.onAttach(null);
        presenter.signUp(correctUserName, correctEmail, correctPassword);

        Mockito.verify(userRepository, Mockito.times(0))
                .signUp(correctEmail, correctPassword);
        Mockito.verify(userRepository, Mockito.times(0))
                .changeUserName(authUser, correctUserName);

        Mockito.verify(view, Mockito.times(0))
                .navigateToMapScreen();

    }

    @Test
    public void verifySingUpWithCorrectEmailPasswordAndNameViewDetachedFromPresenter() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signUp(correctUserName, correctEmail, correctPassword);

        Mockito.verify(userRepository, Mockito.times(0))
                .signUp(correctEmail, correctPassword);
        Mockito.verify(userRepository, Mockito.times(0))
                .changeUserName(authUser, correctUserName);

        Mockito.verify(view, Mockito.times(0))
                .navigateToMapScreen();

    }

// 1 empty email correct password and name

    @Test
    public void verifySingUpWithEmptyEmailCorrectPasswordAndNameNonNullViewAttachedToPresenter() {
        presenter.onAttach(view);
        presenter.signUp(correctUserName, emptyEmail, correctPassword);

        Mockito.verify(view, Mockito.times(1)).displayEmailError();
    }

    @Test
    public void verifySingUpWithEmptyEmailCorrectPasswordAndNameNullViewAttachedToPresenter() {
        presenter.onAttach(null);
        presenter.signUp(correctUserName, emptyEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();

    }

    @Test
    public void verifySingUpWithEmptyEmailCorrectPasswordAndNameViewDetachedFromPresenter() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signUp(correctUserName, emptyEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();

    }

// 2 incorrect email correct password and name

    @Test
    public void verifySingUpWithIncorrectEmailCorrectPasswordAndNameNonNullViewAttachedToPresenter() {
        presenter.onAttach(view);
        presenter.signUp(correctUserName, incorrectEmail, correctPassword);

        Mockito.verify(view, Mockito.times(1)).displayEmailError();

    }

    @Test
    public void verifySingUpWithIncorrectEmailCorrectPasswordAndNameNullViewAttachedToPresenter() {
        presenter.onAttach(null);
        presenter.signUp(correctUserName, incorrectEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();

    }

    @Test
    public void verifySingUpWithIncorrectEmailCorrectPasswordAndNameViewDetachedFromPresenter() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signUp(correctUserName, incorrectEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmailError();

    }

// 3 correct email empty password correct name

    @Test
    public void verifySingUpWithCorrectEmailEmptyPasswordCorrectNameNonNullViewAttachedToPresenter() {
        presenter.onAttach(view);
        presenter.signUp(correctUserName, correctEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(1)).displayPasswordError();

    }

    @Test
    public void verifySingUpWithCorrectEmailEmptyPasswordCorrectNameNullViewAttachedToPresenter() {
        presenter.onAttach(null);
        presenter.signUp(correctUserName, correctEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayPasswordError();

    }

    @Test
    public void verifySingUpWithCorrectEmailEmptyPasswordCorrectNameViewDetachedFromPresenter() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signUp(correctUserName, correctEmail, emptyPassword);

        Mockito.verify(view, Mockito.times(0)).displayPasswordError();

    }

// 4 correct email and password, empty name

    @Test
    public void verifySingUpWithCorrectEmailAndPasswordEmptyNameNonNullViewAttachedToPresenter() {
        presenter.onAttach(view);
        presenter.signUp(emptyUserName, correctEmail, correctPassword);

        Mockito.verify(view, Mockito.times(1)).displayEmptyUserNameError();

    }

    @Test
    public void verifySingUpWithCorrectEmailAndPasswordEmptyNameNullViewAttachedToPresenter() {
        presenter.onAttach(null);
        presenter.signUp(emptyUserName, correctEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmptyUserNameError();

    }

    @Test
    public void verifySingUpWithCorrectEmailAndPasswordEmptyNameViewDetachedFromPresenter() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signUp(emptyUserName, correctEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).displayEmptyUserNameError();

    }

// 5 correct email, password, name, database error

    @Test
    public void verifySingUpWithCorrectEmailPasswordNameDatabaseErrorNonNullViewAttachedToPresenter() {
        Mockito.when(userRepository.changeUserName(authUser,correctUserName))
                .thenReturn(Single.just(new Result.Error<>(new Exception())));

        presenter.onAttach(view);
        presenter.signUp(correctUserName, correctEmail, correctPassword);

        Mockito.verify(userRepository, Mockito.times(1))
                .signUp(correctEmail, correctPassword);
        Mockito.verify(userRepository, Mockito.times(1))
                .changeUserName(authUser, correctUserName);

        Mockito.verify(view, Mockito.times(1))
                .displayChangeUserNameError();

    }

    @Test
    public void verifySingUpWithCorrectEmailPasswordNameDatabaseErrorNullViewAttachedToPresenter() {
        Mockito.when(userRepository.changeUserName(authUser,correctUserName))
                .thenReturn(Single.just(new Result.Error<>(new Exception())));

        presenter.onAttach(null);
        presenter.signUp(correctUserName, correctEmail, correctPassword);

        Mockito.verify(userRepository, Mockito.times(0))
                .signUp(correctEmail, correctPassword);
        Mockito.verify(userRepository, Mockito.times(0))
                .changeUserName(authUser, correctUserName);

        Mockito.verify(view, Mockito.times(0))
                .displayChangeUserNameError();

    }

    @Test
    public void verifySingUpWithCorrectEmailPasswordNameDatabaseErrorViewDetachedFromPresenter() {
        Mockito.when(userRepository.changeUserName(authUser,correctUserName))
                .thenReturn(Single.just(new Result.Error<>(new Exception())));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signUp(correctUserName, correctEmail, correctPassword);

        Mockito.verify(userRepository, Mockito.times(0))
                .signUp(correctEmail, correctPassword);
        Mockito.verify(userRepository, Mockito.times(0))
                .changeUserName(authUser, correctUserName);

        Mockito.verify(view, Mockito.times(0))
                .displayChangeUserNameError();

    }


// 5 correct email password, name, server send error

    @Test
    public void verifySingUpWithCorrectEmailPasswordNameServerSendErrorNonNullViewAttachedToPresenter() {
        Mockito.when(userRepository.signUp(correctEmail, correctPassword))
                .thenReturn(Single.just(new Result.Error<>(new UserNotAuthenticatedException())));

        presenter.onAttach(view);
        presenter.signUp(correctUserName, correctEmail, correctPassword);

        Mockito.verify(view, Mockito.times(1)).displaySignUpError();

    }

    @Test
    public void verifySingUpWithCorrectEmailPasswordNameServerSendErrorNullViewAttachedToPresenter() {
        Mockito.when(userRepository.signUp(correctEmail, correctPassword))
                .thenReturn(Single.just(new Result.Error<>(new UserNotAuthenticatedException())));

        presenter.onAttach(null);
        presenter.signUp(correctUserName, correctEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).displaySignUpError();

    }

    @Test
    public void verifySingUpWithCorrectEmailPasswordAndNameServerSendErrorViewDetachedFromPresenter() {
        Mockito.when(userRepository.signUp(correctEmail, correctPassword))
                .thenReturn(Single.just(new Result.Error<>(new UserNotAuthenticatedException())));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signUp(correctUserName, correctEmail, correctPassword);

        Mockito.verify(view, Mockito.times(0)).displaySignUpError();

    }


}
