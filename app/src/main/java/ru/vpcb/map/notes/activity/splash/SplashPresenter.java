package ru.vpcb.map.notes.activity.splash;

import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;

import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;

import static com.google.android.gms.common.ConnectionResult.SUCCESS;
import static ru.vpcb.map.notes.activity.splash.SplashActivity.GPS_REQUEST_CODE;

public class SplashPresenter implements SplashMvpPresenter {

    private SplashView view;
    private IAppExecutors appExecutors;
    private UserRepository userRepository;


    public SplashPresenter(IAppExecutors appExecutors, UserRepository userRepository) {
        this.appExecutors= appExecutors;
        this.userRepository = userRepository;
    }


    @Override
    public void onAttach(@NonNull SplashView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void start() {
        if (view == null) return;
        int code = view.isPlayServicesAvailable();
        if (code == ConnectionResult.SUCCESS) {
            this.startMapNotes();
        }else {
            if (view.isInstalledPlayMarket()) {
                view.getErrorDialog(code);          // play market
            } else {
                view.getAlertDialog();              // no play market
            }
        }
    }

    @Override
    public void startMapNotes() {
        if (view == null) return;
        if (userRepository.getCurrentUser() instanceof Result.Success) {
            view.navigateToHome();
        } else {
            view.navigateToLogin();
        }
        view.finishActivity();
    }

    @Override
    public void playMarketResults(int requestCode) {
        if (view == null) return;
        if (requestCode == GPS_REQUEST_CODE) {
            if (view.isPlayServicesAvailable() == SUCCESS) {
                startMapNotes();
            } else {
                view.finishActivity();
            }
        }else {
            view.finishActivity();
        }
    }

    @Override
    public void onPositive() {
        if (view == null) {
            return;
        }
        view.navigateToPlayMarket();
    }

    @Override
    public void onNegative() {
        if (view == null) {
            return;
        }
        view.finishActivity();
    }


}
