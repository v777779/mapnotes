package ru.vpcb.map.notes.activity.home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import ru.vpcb.map.notes.map.MapFragment;
import ru.vpcb.map.notes.nopermissions.NoLocationPermissionFragment;
import ru.vpcb.map.notes.search.SearchNotesFragment;

public class HomeActivity extends BaseActivity implements HomeView {
    public static final String DISPLAY_LOCATION = "display_location";
    public static final String EXTRA_NOTE = "note";

    // TODO by inject
    @Inject
    HomeMvpPresenter presenter;
    @Inject
    MapFragment mapFragment;
    @Inject
    FAManager analyticsManager;

    private View layout;
    private View bottomSheet;
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
        bottomSheet = findViewById(R.id.bottomSheet);
        navigation = findViewById(R.id.navigation);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        hideExpandedMenuListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getParcelableExtra(EXTRA_NOTE) != null) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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
                if (navigation.getSelectedItemId() == item.getItemId() &&
                        bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                    return false;
                }
                return presenter.handleNavigationItemClick(item.getItemId());
            }
        };

    }

    @Override
    public void setupComponent() {
        HomeComponent component = MainApp.plus(this);
        component.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        presenter.checkEnablePermissions();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(hideExpandedMenuListener, new IntentFilter(DISPLAY_LOCATION));
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
        finish();
        NavigationExt.navigateTo(this, LoginActivity.class);
    }


    @Override
    public boolean checkLocationPermission() {
        return PermissionExt.checkLocationPermission(this);
    }

    @Override
    public boolean shouldShowRequestPermission() {
        return ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }



@Override
    public void requestLocationPermission() {
        PermissionExt.requestLocationPermissions(this);
    }


// methods

    private void replaceBottomFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bottomSheetContainer, fragment)
                .commit();
    }

}
