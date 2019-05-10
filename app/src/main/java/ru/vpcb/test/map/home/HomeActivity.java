package ru.vpcb.test.map.home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.vpcb.test.map.R;
import ru.vpcb.test.map.add.AddNoteFragment;
import ru.vpcb.test.map.data.repository.FirebaseUserRepository;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.ext.NavigationExt;
import ru.vpcb.test.map.ext.PermissionExt;
import ru.vpcb.test.map.login.LoginActivity;
import ru.vpcb.test.map.map.GeneralMapFragment;
import ru.vpcb.test.map.map.MapFragment;
import ru.vpcb.test.map.nopermissions.NoLocationPermissionFragment;
import ru.vpcb.test.map.search.SearchNotesFragment;

public class HomeActivity extends AppCompatActivity implements HomeView {
    public static final String DISPLAY_LOCATION = "display_location";
    public static final String EXTRA_NOTE = "note";

    // TODO by inject
    private AppExecutors appExecutors;
    private UserRepository userRepository;
    private HomeMvpPresenter presenter;
    private MapFragment mapFragment;

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

// TODO by inject
        userRepository = new FirebaseUserRepository(appExecutors);
        presenter = new HomePresenter(appExecutors, userRepository);
        mapFragment = new GeneralMapFragment();

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

        if (!PermissionExt.checkLocationPermission(this)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                presenter.showLocationPermissionRationale();
            } else {

                PermissionExt.requestLocationPermissions(this);
            }
        } else {
            showContentWhichRequirePermissions();
        }

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

// methods

    private void replaceBottomFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bottomSheetContainer, fragment)
                .commit();
    }

}
