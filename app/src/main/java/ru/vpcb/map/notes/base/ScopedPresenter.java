package ru.vpcb.map.notes.base;

import androidx.annotation.NonNull;

public class ScopedPresenter<V extends MvpView> implements MvpPresenter<V> {

    @Override
    public void onAttach(@NonNull V view) {
//        job = Job()
    }

    @Override
    public void onDetach() {
//        job.cancel()
    }
}
