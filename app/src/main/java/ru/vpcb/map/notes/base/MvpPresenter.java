package ru.vpcb.map.notes.base;

import androidx.annotation.NonNull;

public interface MvpPresenter<V extends MvpView> {
    void onAttach(@NonNull V view);

    void onDetach();
}
