package ru.vpcb.map.notes.activity.splash;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SplashPresneterTests {
    private int codeSuccess;
    private int codeCancelled;
    private int requestCodeGPSRequest;
    private int requestCodeNonGPSRequest;

    private int playServiceAvailableCode;
    private int playServiceNotAvailableCode;

    @Before
    public void setUp() throws Exception {

    }

    // start
    @Test
    public void startWithCodeSuccessNonNullViewStartMapNotes() {

    }

    @Test
    public void startWithCodeSuccessNullViewStartMapNotesNotCalled() {

    }

    @Test
    public void startWithCodeSuccessWithViewDetachedFromPresenterStartMapNotesNotCalled() {

    }


    @Test
    public void startWithCodeCancelledAndMarketInstalledNonNullViewGetErrorDialog() {

    }

    @Test
    public void startWithCodeCancelledAndMarketInstalledNullViewViewGetErrorDialogNotCalled() {

    }

    @Test
    public void startWithCodeCancelledAndMarketInstalledWithViewDetachedFromPresenterViewGetErrorDialogNotCalled() {

    }

    @Test
    public void startWithCodeCancelledAndMarketNotInstalledNonNullViewGetAlertDialog() {

    }

    @Test
    public void startWithCodeCancelledAndMarketNotInstalledNullViewGetAlertDialogNotCalled() {

    }

    @Test
    public void startWithCodeCancelledAndMarketNotInstalledWithViewDetachedFromPresenterGetAlertDialogNotCalled() {

    }

// startMapNotes

    @Test
    public void startMapNotesWithUserAuthenticatedWithNonNullViewNavigateToHomeViewFinish() {


    }
    @Test
    public void startMapNotesWithUserAuthenticatedWithNullViewNavigateToHomeNotCalledViewNotFinished() {

    }
    
    @Test
    public void startMapNotesWithUserAuthenticatedWithViewDetachedFromPresenterNavigateToHomeNotCalledViewNotFinished() {
        
    }
    
    @Test
    public void startMapNotesWithUserNotAuthenticatedWithNonNullViewNavigateToLoginViewFinish() {

    }


    @Test
    public void startMapNotesWithUserNotAuthenticatedWithNullViewNavigateToLoginNotCalledVieNotFinished() {

    }

    @Test
    public void startMapNotesWithUserNotAuthenticatedWithViewDetachedFromPresenterNavigateToLoginNotCalledViewNotFinished() {

    }


// playMarketResults

    @Test
    public void playMarketResultsWithRequestCodeGPSRequestWithNonNullViewStartMapNotes() {


    }

    @Test
    public void playMarketResultsWithRequestCodeGPSRequestsWithNullViewStartMapNotesNotCalled() {

    }

    @Test
    public void playMarketResultsWithRequestCodeGPSRequestsWithViewDetachedFromPresenterStartMapNotesNotCalled() {

    }


    @Test
    public void playMarketResultsWithRequestCodeNotGPSRequestsWithNonNullViewFinished() {

    }

    @Test
    public void playMarketResultsWithRequestCodeNotGPSRequestsWithNullViewViewNotFinished() {

    }

    @Test
    public void playMarketResultsWithRequestCodeNotGPSRequestsWithViewDetachedFromPresenterViewNotFinished() {

    }

// onPositive

    @Test
    public void onPositiveWithNonNullViewNavigateToPlayMarket() {

    }

    @Test
    public void onPositiveWithNullViewNavigateToPlayMarketNotCalled() {

    }

    @Test
    public void onPositiveWithViewDetachedFromPresenterNavigateToPlayMarketNotCalled() {

    }


// onNegative
    
    @Test
    public void onNegativeWithNonNullViewViewFinished() {

    }

    @Test
    public void onNegativeWithNullViewViewNotFinished() {

    }

    @Test
    public void onNegativeWithViewDetachedFromPresenterViewNotFinished() {

    }


    @After
    public void tearDown() throws Exception {

    }
}
