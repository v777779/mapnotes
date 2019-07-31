package ru.vpcb.map.notes.map;

import androidx.annotation.NonNull;

import ru.vpcb.map.notes.base.MvpView;
import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

public interface MapView extends MvpView {

    void animateCamera(@NonNull Location currentLocation);

    void displayNoteOnMap(Note note);

    boolean isLocationAvailable();

    void showLocationAlertDialog();

    void openSettings();

    void exit();
}
