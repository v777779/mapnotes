package ru.vpcb.map.notes.activity.splash;

import android.security.keystore.UserNotAuthenticatedException;

import com.google.android.gms.common.ConnectionResult;

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
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.AuthUser;

@RunWith(RobolectricTestRunner.class)
@Config(
        sdk = 28,
        application = MainApp.class
)
public class SplashPresneterTests {

    private Result<AuthUser> authUser;
    private Result.Error<AuthUser> notAuthUser;
    private int playServiceAvailableCode;
    private int playServiceNotAvailableCode;
    private int gpsRequestCode;
    private int nonGPSRequestCode;

    @Mock
    private SplashView view;
    @Mock
    private IAppExecutors appExecutors;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SplashMvpPresenter presenter;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        String authUserUID = "111111";
        authUser = new Result.Success<>(new AuthUser(authUserUID));
        notAuthUser = new Result.Error<>(new UserNotAuthenticatedException());

        playServiceAvailableCode = ConnectionResult.SUCCESS;
        playServiceNotAvailableCode = ConnectionResult.UNKNOWN;

        gpsRequestCode = SplashActivity.GPS_REQUEST_CODE;
        nonGPSRequestCode = -1;

        presenter = new SplashPresenter(appExecutors, userRepository);

        Mockito.when(appExecutors.ui()).then(
                (Answer<Scheduler>) inv -> AndroidSchedulers.mainThread());
        Mockito.when(appExecutors.net()).then(
                (Answer<Scheduler>) inv -> AndroidSchedulers.mainThread());

        Mockito.doAnswer(invocation -> null).when(view).navigateToHome();
        Mockito.doAnswer(invocation -> null).when(view).navigateToLogin();
        Mockito.doAnswer(invocation -> null).when(view).navigateToPlayMarket();
        Mockito.doAnswer(invocation -> {
            Mockito.doAnswer(invocation1 -> null).when(view).finishActivity();
            return null;
        }).when(view).getErrorDialog(Mockito.anyInt());
        Mockito.doAnswer(invocation -> null).when(view).getAlertDialog();  // two choices

        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceAvailableCode);
        Mockito.when(view.isInstalledPlayMarket()).thenReturn(true);
        Mockito.when(userRepository.getCurrentUser()).thenReturn(authUser);

    }

// start code success user authenticated

    @Test
    public void startWithCodeSuccessUserAuthenticatedNonNullViewNavigateToHomeCalled() {
        presenter.onAttach(view);
        presenter.start();

        Mockito.verify(view, Mockito.times(1)).navigateToHome();
        Mockito.verify(view, Mockito.times(1)).finishActivity();
    }

    @Test
    public void startWithCodeSuccessUserAuthenticatedNullViewStartNavigateToHomeNotCalled() {
        presenter.onAttach(null);
        presenter.start();

        Mockito.verify(view, Mockito.times(0)).navigateToHome();
        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }

    @Test
    public void startWithCodeSuccessUserAuthenticatedWithViewDetachedFromPresenterNavigateToHomeNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.start();

        Mockito.verify(view, Mockito.times(0)).navigateToHome();
        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }

// start code success user not authenticated

    @Test
    public void startWithCodeSuccessUserNotAuthenticatedNonNullViewNavigateToLoginCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(view);
        presenter.start();

        Mockito.verify(view, Mockito.times(1)).navigateToLogin();
        Mockito.verify(view, Mockito.times(1)).finishActivity();
    }

    @Test
    public void startWithCodeSuccessUserNotAuthenticatedNullViewNavigateToLoginNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(null);
        presenter.start();

        Mockito.verify(view, Mockito.times(0)).navigateToLogin();
        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }

    @Test
    public void startWithCodeSuccessUserNotAuthenticatedWithViewDetachedFromPresenterNavigateToLoginNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.start();

        Mockito.verify(view, Mockito.times(0)).navigateToLogin();
        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }


// start code cancelled market installed

    @Test
    public void startWithCodeCancelledMarketInstalledNonNullViewGetErrorDialog() {
        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceNotAvailableCode);

        presenter.onAttach(view);
        presenter.start();

        Mockito.verify(view, Mockito.times(1))
                .getErrorDialog(playServiceNotAvailableCode);
    }

    @Test
    public void startWithCodeCancelledMarketInstalledNullViewViewGetErrorDialogNotCalled() {
        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceNotAvailableCode);

        presenter.onAttach(null);
        presenter.start();

        Mockito.verify(view, Mockito.times(0))
                .getErrorDialog(playServiceNotAvailableCode);
    }

    @Test
    public void startWithCodeCancelledMarketInstalledWithViewDetachedFromPresenterViewGetErrorDialogNotCalled() {
        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceNotAvailableCode);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.start();
        Mockito.verify(view, Mockito.times(0))
                .getErrorDialog(playServiceNotAvailableCode);

    }

    @Test
    public void startWithCodeCancelledMarketNotInstalledNonNullViewGetAlertDialog() {
        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceNotAvailableCode);
        Mockito.when(view.isInstalledPlayMarket()).thenReturn(false);

        presenter.onAttach(view);
        presenter.start();

        Mockito.verify(view, Mockito.times(1)).getAlertDialog();

    }

    @Test
    public void startWithCodeCancelledMarketNotInstalledNullViewGetAlertDialogNotCalled() {
        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceNotAvailableCode);
        Mockito.when(view.isInstalledPlayMarket()).thenReturn(false);

        presenter.onAttach(null);
        presenter.start();

        Mockito.verify(view, Mockito.times(0)).getAlertDialog();

    }

    @Test
    public void startWithCodeCancelledMarketNotInstalledWithViewDetachedFromPresenterGetAlertDialogNotCalled() {
        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceNotAvailableCode);
        Mockito.when(view.isInstalledPlayMarket()).thenReturn(false);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.start();

        Mockito.verify(view, Mockito.times(0)).getAlertDialog();

    }

// startMapNotes

    @Test
    public void startMapNotesWithUserAuthenticatedWithNonNullViewNavigateToHomeCalled() {
        presenter.onAttach(view);
        presenter.startMapNotes();

        Mockito.verify(view, Mockito.times(1)).navigateToHome();
        Mockito.verify(view, Mockito.times(1)).finishActivity();

    }

    @Test
    public void startMapNotesWithUserAuthenticatedWithNullViewNavigateToHomeNotCalled() {
        presenter.onAttach(null);
        presenter.startMapNotes();

        Mockito.verify(view, Mockito.times(0)).navigateToHome();
        Mockito.verify(view, Mockito.times(0)).finishActivity();

    }

    @Test
    public void startMapNotesWithUserAuthenticatedWithViewDetachedFromPresenterNavigateToHomeNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.startMapNotes();

        Mockito.verify(view, Mockito.times(0)).navigateToHome();
        Mockito.verify(view, Mockito.times(0)).finishActivity();

    }

    @Test
    public void startMapNotesWithUserNotAuthenticatedWithNonNullViewNavigateToLoginCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(view);
        presenter.startMapNotes();

        Mockito.verify(view, Mockito.times(1)).navigateToLogin();
        Mockito.verify(view, Mockito.times(1)).finishActivity();

    }


    @Test
    public void startMapNotesWithUserNotAuthenticatedWithNullViewNavigateToLoginNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(null);
        presenter.startMapNotes();

        Mockito.verify(view, Mockito.times(0)).navigateToLogin();
        Mockito.verify(view, Mockito.times(0)).finishActivity();

    }

    @Test
    public void startMapNotesWithUserNotAuthenticatedWithViewDetachedFromPresenterNavigateToLoginNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.startMapNotes();

        Mockito.verify(view, Mockito.times(0)).navigateToLogin();
        Mockito.verify(view, Mockito.times(0)).finishActivity();

    }


// playMarketResults

    @Test
    public void playMarketResultsWithGPSRequestCodeUserAuthenticatedPlayServiceAvailableWithNonNullViewNavigateToHomeCalled() {
        presenter.onAttach(view);
        presenter.playMarketResults(gpsRequestCode);

        Mockito.verify(view, Mockito.times(1)).navigateToHome();
        Mockito.verify(view, Mockito.times(1)).finishActivity();

    }

    @Test
    public void playMarketResultsWithGPSRequestCodeUserAuthenticatedPlayServiceAvailableWithNullViewNavigateToHomeNotCalled() {
        presenter.onAttach(null);
        presenter.playMarketResults(gpsRequestCode);

        Mockito.verify(view, Mockito.times(0)).navigateToHome();
        Mockito.verify(view, Mockito.times(0)).finishActivity();

    }

    @Test
    public void playMarketResultsWithGPSRequestCodeUserAuthenticatedPlayServiceAvailableWithViewDetachedFromPresenterNavigateToHomeNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.playMarketResults(gpsRequestCode);

        Mockito.verify(view, Mockito.times(0)).navigateToHome();
        Mockito.verify(view, Mockito.times(0)).finishActivity();

    }

    @Test
    public void playMarketResultsWithRequestGPSRequestCodeUserNotAuthenticatedPlayServiceAvailableWithNonNullViewNavigateToLoginCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(view);
        presenter.playMarketResults(gpsRequestCode);

        Mockito.verify(view, Mockito.times(1)).navigateToLogin();
        Mockito.verify(view, Mockito.times(1)).finishActivity();

    }

    @Test
    public void playMarketResultsWithGPSRequestCodeUserNotAuthenticatedPlayServiceAvailableWithNullViewNavigateToLoginCalledNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(null);
        presenter.playMarketResults(gpsRequestCode);

        Mockito.verify(view, Mockito.times(0)).navigateToLogin();
        Mockito.verify(view, Mockito.times(0)).finishActivity();

    }

    @Test
    public void playMarketResultsWithGPSRequestCodeUserNotAuthenticatedPlayServiceAvailableWithViewDetachedFromPresenterNavigateToLoginCalledNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.playMarketResults(gpsRequestCode);

        Mockito.verify(view, Mockito.times(0)).navigateToLogin();
        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }


    @Test
    public void playMarketResultsWithGPSRequestCodePlayServiceNotAvailableWithNonNullViewFinishActivityCalled() {
        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceNotAvailableCode);

        presenter.onAttach(view);
        presenter.playMarketResults(gpsRequestCode);

        Mockito.verify(view, Mockito.times(1)).finishActivity();
    }

    @Test
    public void playMarketResultsWithGPSRequestCodePlayServiceNotAvailableWithNullViewFinishActivityNotCalled() {
        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceNotAvailableCode);

        presenter.onAttach(null);
        presenter.playMarketResults(gpsRequestCode);

        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }

    @Test
    public void playMarketResultsWithGPSRequestCodePlayServiceNotAvailableWithViewDetachedFromPresenterFinishActivityNotCalled() {
        Mockito.when(view.isPlayServicesAvailable()).thenReturn(playServiceNotAvailableCode);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.playMarketResults(gpsRequestCode);

        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }

    @Test
    public void playMarketResultsWithNonGPSRequestCodeWithNonNullViewFinishActivityCalled() {
        presenter.onAttach(view);
        presenter.playMarketResults(nonGPSRequestCode);

        Mockito.verify(view, Mockito.times(1)).finishActivity();
    }

    @Test
    public void playMarketResultsWithNonGPSGPSRequestCodeUserAuthenticatedWithNullViewFinishActivityNotCalled() {
        presenter.onAttach(null);
        presenter.playMarketResults(nonGPSRequestCode);

        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }

    @Test
    public void playMarketResultsWithNonGPSGPSRequestCodeUserAuthenticatedWithViewDetachedFromPresenterFinishActivityNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.playMarketResults(nonGPSRequestCode);

        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }

// onPositive

    @Test
    public void onPositiveWithNonNullViewNavigateToPlayMarketCalled() {
        presenter.onAttach(view);
        presenter.onPositive();

        Mockito.verify(view, Mockito.times(1)).navigateToPlayMarket();
    }

    @Test
    public void onPositiveWithNullViewNavigateToPlayMarketNotCalled() {
        presenter.onAttach(null);
        presenter.onPositive();

        Mockito.verify(view, Mockito.times(0)).navigateToPlayMarket();
    }

    @Test
    public void onPositiveWithViewDetachedFromPresenterNavigateToPlayMarketNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.onPositive();

        Mockito.verify(view, Mockito.times(0)).navigateToPlayMarket();
    }


// onNegative

    @Test
    public void onNegativeWithNonNullViewFinishActivityCalled() {
        presenter.onAttach(view);
        presenter.onNegative();

        Mockito.verify(view, Mockito.times(1)).finishActivity();
    }

    @Test
    public void onNegativeWithNullViewFinishActivityNotCalled() {
        presenter.onAttach(null);
        presenter.onNegative();

        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }

    @Test
    public void onNegativeWithViewDetachedFromPresenterFinishActivityNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.onNegative();

        Mockito.verify(view, Mockito.times(0)).finishActivity();
    }


    @After
    public void tearDown() throws Exception {

    }
}
