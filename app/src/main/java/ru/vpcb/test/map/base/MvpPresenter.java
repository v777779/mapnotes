package ru.vpcb.test.map.base;

import android.support.annotation.NonNull;

public interface MvpPresenter<V extends MvpView> {
    void onAttach(@NonNull V view);

    void onDetach();
}
