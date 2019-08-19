package ru.vpcb.map.notes.fragments.add;

import androidx.annotation.VisibleForTesting;

import ru.vpcb.map.notes.base.MvpPresenter;
import ru.vpcb.map.notes.model.Location;

public interface AddNoteMvpPresenter extends MvpPresenter<AddNoteView> {
    void getCurrentLocation();

    void addNote(String text);

    @VisibleForTesting
    void updateLastLocation(Location location);
}
