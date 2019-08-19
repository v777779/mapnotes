package ru.vpcb.map.notes.activity.home;

import android.security.keystore.UserNotAuthenticatedException;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.AuthUser;

@RunWith(RobolectricTestRunner.class)
@Config(
        sdk = 28,
        application = MainApp.class
)
public class HomePresenterTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Result<AuthUser> authUser;
    private Result.Error<AuthUser> notAuthUser;
    private String unknownResourceException;
    private int unknownResource;

    @Mock
    private HomeView view;
    @Mock
    private IAppExecutors appExecutors;
    @Mock
    private UserRepository userRepository;

    private HomeMvpPresenter presenter;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        String authUserUID = "111111";
        authUser = new Result.Success<>(new AuthUser(authUserUID));
        notAuthUser = new Result.Error<>(new UserNotAuthenticatedException());
        unknownResourceException = "Should have thrown IllegalArgumentException";
        unknownResource = -1;


        presenter = new HomePresenter(appExecutors, userRepository);

        Mockito.when(appExecutors.ui())
                .then((Answer<Scheduler>) invocation -> AndroidSchedulers.mainThread());
        Mockito.when(appExecutors.net())
                .then((Answer<Scheduler>) invocation -> AndroidSchedulers.mainThread());


        Mockito.doAnswer(invocation -> null).when(userRepository).signOut();
        Mockito.when(userRepository.getCurrentUser()).thenReturn(authUser);

        Mockito.doAnswer(invocation -> null).when(view)
                .updateMapInteractionMode(Mockito.anyBoolean());
        Mockito.doAnswer(invocation -> null).when(view).updateNavigationState(Mockito.anyInt());
        Mockito.doAnswer(invocation -> null).when(view).displayAddNote();
        Mockito.doAnswer(invocation -> null).when(view).displaySearchNotes();
        Mockito.doAnswer(invocation -> null).when(view).hideContentWhichRequirePermissions();
        Mockito.doAnswer(invocation -> null).when(view).showContentWhichRequirePermissions();
        Mockito.doAnswer(invocation -> null).when(view).showPermissionExplanationSnackBar();
        Mockito.doAnswer(invocation -> null).when(view).navigateToLoginScreen();
    }

// 0     handleNavigationItemClickNavigation    item is navigation add node button

    @Test
    public void handleNavigationItemClickNavigationAddNoteWithNonNullViewDisplayAddNoteCalledReturnTrue() {
        presenter.onAttach(view);
        boolean isResult = presenter.handleNavigationItemClick(R.id.navigation_add_note);

        Mockito.verify(view, Mockito.times(1))
                .updateMapInteractionMode(true);
        Mockito.verify(view, Mockito.times(1)).displayAddNote();
        Mockito.verify(view, Mockito.times(1))
                .updateNavigationState(BottomSheetBehavior.STATE_COLLAPSED);
        Assert.assertTrue(isResult);
    }

    @Test
    public void handleNavigationItemClickNavigationAddNoteWithNullViewDisplayAddNoteNotCalledReturnFalse() {
        presenter.onAttach(null);
        boolean isResult = presenter.handleNavigationItemClick(R.id.navigation_add_note);

        Mockito.verify(view, Mockito.times(0))
                .updateMapInteractionMode(true);
        Mockito.verify(view, Mockito.times(0)).displayAddNote();
        Mockito.verify(view, Mockito.times(0))
                .updateNavigationState(BottomSheetBehavior.STATE_COLLAPSED);
        Assert.assertFalse(isResult);
    }

    @Test
    public void handleNavigationItemClickNavigationAddNoteWithViewDetachedDisplayAddNoteNotCalledReturnFalse() {
        presenter.onAttach(view);
        presenter.onDetach();
        boolean isResult = presenter.handleNavigationItemClick(R.id.navigation_add_note);

        Mockito.verify(view, Mockito.times(0))
                .updateMapInteractionMode(true);
        Mockito.verify(view, Mockito.times(0)).displayAddNote();
        Mockito.verify(view, Mockito.times(0))
                .updateNavigationState(BottomSheetBehavior.STATE_COLLAPSED);
        Assert.assertFalse(isResult);
    }

// 1     handleNavigationItemClickNavigation    item is navigation map button

    @Test
    public void handleNavigationItemClickNavigationMapWithNonNullViewUpdateNavigationStateCalledReturnTrue() {
        presenter.onAttach(view);
        boolean isResult = presenter.handleNavigationItemClick(R.id.navigation_map);

        Mockito.verify(view, Mockito.times(1))
                .updateNavigationState(BottomSheetBehavior.STATE_HIDDEN);
        Assert.assertTrue(isResult);
    }

    @Test
    public void handleNavigationItemClickNavigationMapWithNullViewUpdateNavigationStateNotCalledReturnFalse() {
        presenter.onAttach(null);
        boolean isResult = presenter.handleNavigationItemClick(R.id.navigation_map);

        Mockito.verify(view, Mockito.times(0))
                .updateNavigationState(BottomSheetBehavior.STATE_HIDDEN);
        Assert.assertFalse(isResult);
    }

    @Test
    public void handleNavigationItemClickNavigationMapWithViewDetachedUpdateNavigationStateNotCalledReturnFalse() {
        presenter.onAttach(view);
        presenter.onDetach();
        boolean isResult = presenter.handleNavigationItemClick(R.id.navigation_map);

        Mockito.verify(view, Mockito.times(0))
                .updateNavigationState(BottomSheetBehavior.STATE_HIDDEN);
        Assert.assertFalse(isResult);
    }

// 2     handleNavigationItemClickNavigation    navigation search notes button

    @Test
    public void handleNavigationItemClickNavigationSearchNotesWithNonNullViewDisplaySearchNotesCalledReturnTrue() {
        presenter.onAttach(view);
        boolean isResult = presenter.handleNavigationItemClick(R.id.navigation_search_notes);

        Mockito.verify(view, Mockito.times(1))
                .updateMapInteractionMode(true);
        Mockito.verify(view, Mockito.times(1)).displaySearchNotes();
        Mockito.verify(view, Mockito.times(1))
                .updateNavigationState(BottomSheetBehavior.STATE_EXPANDED);
        Assert.assertTrue(isResult);
    }

    @Test
    public void handleNavigationItemClickNavigationSearchNotesWithNullViewDisplaySearchNotesNotCalledReturnFalse() {
        presenter.onAttach(null);
        boolean isResult = presenter.handleNavigationItemClick(R.id.navigation_search_notes);

        Mockito.verify(view, Mockito.times(0))
                .updateMapInteractionMode(true);
        Mockito.verify(view, Mockito.times(0)).displaySearchNotes();
        Mockito.verify(view, Mockito.times(0))
                .updateNavigationState(BottomSheetBehavior.STATE_EXPANDED);
        Assert.assertFalse(isResult);
    }

    @Test
    public void handleNavigationItemClickNavigationSearchNotesWithViewDetachedDisplaySearchNotesNotCalledReturnFalse() {
        presenter.onAttach(view);
        presenter.onDetach();
        boolean isResult = presenter.handleNavigationItemClick(R.id.navigation_search_notes);

        Mockito.verify(view, Mockito.times(0))
                .updateMapInteractionMode(true);
        Mockito.verify(view, Mockito.times(0)).displaySearchNotes();
        Mockito.verify(view, Mockito.times(0))
                .updateNavigationState(BottomSheetBehavior.STATE_EXPANDED);
        Assert.assertFalse(isResult);
    }

// 3     handleNavigationItemClickNavigation    navigation unknown resource

    @Test
    public void handleNavigationItemClickUnknownResourceWithNonNullViewThrowIllegalArgumentException() {
        presenter.onAttach(view);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Unknown itemId");
        presenter.handleNavigationItemClick(unknownResource);
    }


    @Test
    public void handleNavigationItemClickUnknownResourceWithNullViewNotThrowIllegalArgumentExceptionReturnFalse() {
        presenter.onAttach(null);

        boolean isResult = presenter.handleNavigationItemClick(unknownResource);
        Assert.assertFalse(isResult);
    }

    @Test
    public void handleNavigationItemClickUnknownResourceWithViewDetachedNotThrowIllegalArgumentExceptionReturnFalse() {
        presenter.onAttach(view);
        presenter.onDetach();
            boolean isResult = presenter.handleNavigationItemClick(unknownResource);
            Assert.assertFalse(isResult);
    }

// 4     showLocationPermissionRationale

    @Test
    public void showLocationPermissionRationaleWithNonNullViewShowPermissionExplanationSnackBarCalled() {
        presenter.onAttach(view);
        presenter.showLocationPermissionRationale();

        Mockito.verify(view, Mockito.times(1))
                .showPermissionExplanationSnackBar();
        Mockito.verify(view, Mockito.times(1))
                .hideContentWhichRequirePermissions();
    }

    @Test
    public void showLocationPermissionRationaleWithNullViewShowPermissionExplanationSnackBarNotCalled() {
        presenter.onAttach(null);
        presenter.showLocationPermissionRationale();

        Mockito.verify(view, Mockito.times(0))
                .showPermissionExplanationSnackBar();
        Mockito.verify(view, Mockito.times(0))
                .hideContentWhichRequirePermissions();
    }

    @Test
    public void showLocationPermissionRationaleWithViewDetachedShowPermissionExplanationSnackBarNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.showLocationPermissionRationale();

        Mockito.verify(view, Mockito.times(0))
                .showPermissionExplanationSnackBar();
        Mockito.verify(view, Mockito.times(0))
                .hideContentWhichRequirePermissions();
    }

// 5     checkUser    user authenticated

    @Test
    public void checkUserUserAuthenticatedWithNonNullViewNavigateToLoginScreenNotCalled() {
        presenter.onAttach(view);
        presenter.checkUser();

        Mockito.verify(view, Mockito.times(0)).navigateToLoginScreen();
    }

    @Test
    public void checkUserUserAuthenticatedWithNullViewNavigateToLoginScreenNotCalled() {
        presenter.onAttach(null);
        presenter.checkUser();

        Mockito.verify(view, Mockito.times(0)).navigateToLoginScreen();
    }

    @Test
    public void checkUserUserAuthenticatedWithViewDetachedNavigateToLoginScreenNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.checkUser();

        Mockito.verify(view, Mockito.times(0)).navigateToLoginScreen();
    }

// 6    checkUser    user not authenticated

    @Test
    public void checkUserUserNotAuthenticatedWithNonNullViewNavigateToLoginScreenCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);
        presenter.onAttach(view);
        presenter.checkUser();

        Mockito.verify(view, Mockito.times(1)).navigateToLoginScreen();
    }

    @Test
    public void checkUserUserNotAuthenticatedWithNullViewNavigateToLoginScreenNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);
        presenter.onAttach(null);
        presenter.checkUser();

        Mockito.verify(view, Mockito.times(0)).navigateToLoginScreen();
    }

    @Test
    public void checkUserUserNotAuthenticatedWithViewDetachedNavigateToLoginScreenNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.checkUser();

        Mockito.verify(view, Mockito.times(0)).navigateToLoginScreen();
    }

// 7    signOut

    @Test
    public void signOutWithNonNullViewSignOutCalled() {
        presenter.onAttach(view);
        presenter.signOut();

        Mockito.verify(userRepository, Mockito.times(1)).signOut();
        Mockito.verify(view, Mockito.times(1)).navigateToLoginScreen();

    }

    @Test
    public void signOutWithNullViewSignOutNotCalled() {
        presenter.onAttach(null);
        presenter.signOut();

        Mockito.verify(userRepository, Mockito.times(0)).signOut();
        Mockito.verify(view, Mockito.times(0)).navigateToLoginScreen();

    }

    @Test
    public void signOutWithViewDetachedSignOutNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.signOut();

        Mockito.verify(userRepository, Mockito.times(0)).signOut();
        Mockito.verify(view, Mockito.times(0)).navigateToLoginScreen();

    }

    @After
    public void tearDown() throws Exception {

    }
}

// alternative
//    @Test
//    public void handleNavigationItemClickUnknownResourceWithNonNullViewThrowIllegalArgumentException() {
//        presenter.onAttach(view);
//        try {
//            presenter.handleNavigationItemClick(unknownResource);
//            Assert.fail(unknownResourceException);
//        } catch (IllegalArgumentException e) {
//            Assert.assertEquals("Unknown itemId", e.getMessage());
//        } catch (Exception e) {
//            Assert.fail(unknownResourceException);
//        }
//    }
//
//    @Test
//    public void handleNavigationItemClickUnknownResourceWithNullViewNotThrowIllegalArgumentExceptionReturnFalse() {
//        presenter.onAttach(null);
//        try {
//            boolean isResult = presenter.handleNavigationItemClick(unknownResource);
//            Assert.assertFalse(isResult);
//        } catch (IllegalArgumentException e) {
//            Assert.fail();
//        } catch (Exception e) {
//            Assert.fail();
//        }
//    }
//
//    @Test
//    public void handleNavigationItemClickUnknownResourceWithViewDetachedNotThrowIllegalArgumentExceptionReturnFalse() {
//        presenter.onAttach(view);
//        presenter.onDetach();
//        try {
//            boolean isResult = presenter.handleNavigationItemClick(unknownResource);
//            Assert.assertFalse(isResult);
//        } catch (IllegalArgumentException e) {
//            Assert.fail();
//        } catch (Exception e) {
//            Assert.fail();
//        }
//    }
