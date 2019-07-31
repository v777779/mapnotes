package ru.vpcb.test.map.activity.home;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import ru.vpcb.test.map.R;
import ru.vpcb.test.map.base.ScopedPresenter;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.executors.IAppExecutors;
import ru.vpcb.test.map.model.AuthUser;

public class HomePresenter extends ScopedPresenter<HomeView> implements HomeMvpPresenter {

    private IAppExecutors appExecutors;
    private AppExecutors oldAppExecutors;
    private UserRepository userRepository;
    private HomeView view;

    public HomePresenter(IAppExecutors appExecutors, UserRepository userRepository) {
        this.appExecutors = appExecutors;
        this.userRepository = userRepository;
    }


    @Override
    public void onAttach(@NonNull HomeView view) {
        super.onAttach(view);
        this.view = view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.view = null;
    }

    @Override
    public boolean handleNavigationItemClick(int itemId) {
        if (view == null) return false;
        switch (itemId) {
            case R.id.navigation_add_note:
                view.updateMapInteractionMode(true);
                view.displayAddNote();
                view.updateNavigationState(BottomSheetBehavior.STATE_COLLAPSED);
                return true;

            case R.id.navigation_map:
                view.updateNavigationState(BottomSheetBehavior.STATE_HIDDEN);
                return true;

            case R.id.navigation_search_notes:
                view.updateMapInteractionMode(true);
                view.displaySearchNotes();
                view.updateNavigationState(BottomSheetBehavior.STATE_EXPANDED);
                return true;

            default:
                throw new IllegalArgumentException("Unknown itemId");
        }

    }


    @Override
    public void checkUser() {
        if (view == null) return;


// TODO launch  UI Context
        oldAppExecutors = new AppExecutors() {
            @Override
            public <T> void resume(Result<T> result) {
                if (result instanceof Result.Error) {
                    view.navigateToLoginScreen();
                }
            }
        };
        userRepository.setAppExecutors(oldAppExecutors);
        Result<AuthUser> currentUser = userRepository.getCurrentUser();

    }

    @Override
    public void signOut() {
        if (view == null) return;
// TODO launch UI Context
        userRepository.signOut();
        view.navigateToLoginScreen();

    }

    public void checkEnablePermissions() {
        if (view == null) return;

        if (!view.checkLocationPermission()) {
            if (view.shouldShowRequestPermission()) {
                view.showPermissionExplanationSnackBar();
                view.hideContentWhichRequirePermissions();
            } else {
                view.requestLocationPermission();
            }
        } else {
            view.showContentWhichRequirePermissions();
        }
    }



}
