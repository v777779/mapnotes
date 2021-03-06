package ru.vpcb.map.notes.activity.home;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.base.ScopedPresenter;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.AuthUser;

public class HomePresenter extends ScopedPresenter<HomeView> implements HomeMvpPresenter {

    private IAppExecutors appExecutors;
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
    public void showLocationPermissionRationale() {
        if (view == null) {
            return;
        }
        view.showPermissionExplanationSnackBar();
        view.hideContentWhichRequirePermissions();
    }

    @Override
    public void showLocationRequirePermissions() {
        if (view == null) {
            return;
        }
        view.showContentWhichRequirePermissions();
    }


    @Override
    public void checkUser() {
        if (view == null) return;
        Result<AuthUser> result = userRepository.getCurrentUser();
        if (result instanceof Result.Error) {
            view.navigateToLoginScreen();
        }
    }

    @Override
    public void signOut() {
        if (view == null) return;
        userRepository.signOut();
        view.navigateToLoginScreen();
    }

}
