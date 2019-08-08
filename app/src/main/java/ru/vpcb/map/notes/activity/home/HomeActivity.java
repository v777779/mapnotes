package ru.vpcb.map.notes.activity.home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.BaseActivity;
import ru.vpcb.map.notes.activity.login.LoginActivity;
import ru.vpcb.map.notes.add.AddNoteFragment;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.ext.NavigationExt;
import ru.vpcb.map.notes.ext.PermissionExt;
import ru.vpcb.map.notes.manager.FAManager;
import ru.vpcb.map.notes.manager.FCManager;
import ru.vpcb.map.notes.map.MapFragment;
import ru.vpcb.map.notes.nopermissions.NoLocationPermissionFragment;
import ru.vpcb.map.notes.search.SearchNotesFragment;

public class HomeActivity extends BaseActivity implements HomeView {
    public static final String DISPLAY_LOCATION = "display_location";
    public static final String REFRESH_NOTES = "refresh_notes";
    public static final String EXTRA_NOTE = "note";


    @Inject
    HomeMvpPresenter presenter;
    @Inject
    MapFragment mapFragment;
    @Inject
    FAManager analyticsManager;

    private View layout;
    private BottomSheetBehavior bottomSheetBehavior;
    private BroadcastReceiver hideExpandedMenuListener;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layout = findViewById(android.R.id.content);
        navigation = findViewById(R.id.navigation);
        View bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        hideExpandedMenuListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent == null) {
                    return;
                }
                String action = intent.getAction();
                if (HomeActivity.DISPLAY_LOCATION.equals(action) &&
                        intent.getParcelableExtra(EXTRA_NOTE) != null) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else if (HomeActivity.REFRESH_NOTES.equals(action)) {
                    presenter.handleNavigationItemClick(R.id.navigation_search_notes);
                }
            }
        };

        bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN &&
                        navigation.getSelectedItemId() != R.id.navigation_map) {
                    navigation.setSelectedItemId(R.id.navigation_map);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        };

        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (navigation.getSelectedItemId() == item.getItemId() &&                           // блокирует повторы с search и add
                        bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                    return false;
                }
                return presenter.handleNavigationItemClick(item.getItemId());
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_sign_out) {
            presenter.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigation.setSelectedItemId(R.id.navigation_map);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);

        if (!PermissionExt.checkLocationPermission(this)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                presenter.showLocationPermissionRationale();
            } else {
                PermissionExt.requestLocationPermissions(this);
            }
        } else {
            presenter.showLocationRequirePermissions();
        }

        IntentFilter filter = new IntentFilter(DISPLAY_LOCATION);
        filter.addAction(REFRESH_NOTES);
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(hideExpandedMenuListener, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigation.setOnNavigationItemSelectedListener(null);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(hideExpandedMenuListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        presenter.checkUser();
    }

    @Override
    protected void onStop() {
        presenter.onDetach();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (isFinishing())
            MainApp.clear(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mapFragment.isInteractionMode() || mapFragment.hasMarkersOnMap()) {
            mapFragment.setInteractionMode(false);
            mapFragment.clearAllMarkers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionExt.LOCATION_REQUEST_CODE) {
            if ((grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                showContentWhichRequirePermissions();
            } else {
                hideContentWhichRequirePermissions();
            }
        }
    }


    @Override
    public void setupComponent() {
        try {
            HomeComponent component = MainApp.plus(this);
            component.inject(this);
        } catch (Exception e) {
            FCManager.log(e);
        }
    }

// support view

    @Override
    public void updateMapInteractionMode(boolean isInteractionMode) {
        mapFragment.setInteractionMode(isInteractionMode);
    }

    @Override
    public void updateNavigationState(int newState) {
        bottomSheetBehavior.setState(newState);
    }

    @Override
    public void displayAddNote() {
        replaceBottomFragment(new AddNoteFragment());
    }

    @Override
    public void displaySearchNotes() {
        replaceBottomFragment(new SearchNotesFragment());
    }

    @Override
    public void hideContentWhichRequirePermissions() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mapContainer, new NoLocationPermissionFragment())
                .commit();
        navigation.setVisibility(View.GONE);
    }

    @Override
    public void showContentWhichRequirePermissions() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mapContainer, mapFragment.fragment())
                .commit();
        navigation.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPermissionExplanationSnackBar() {
        Snackbar.make(layout, R.string.permission_explanation, Snackbar.LENGTH_LONG)
                .setAction(R.string.permission_grant_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PermissionExt.requestLocationPermissions(HomeActivity.this);
                    }
                }).show();
    }

    @Override
    public void navigateToLoginScreen() {
        NavigationExt.navigateTo(this, LoginActivity.class);
        finish();
    }


//    @Override
//    public boolean checkLocationPermission() {
//        return PermissionExt.checkLocationPermission(this);
//    }

//    public boolean shouldShowRequestPermission() {
//        return ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//    }

//    public void requestLocationPermission() {
//        PermissionExt.requestLocationPermissions(this);
//    }


// methods

    private void replaceBottomFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bottomSheetContainer, fragment)
                .commit();
    }

}
