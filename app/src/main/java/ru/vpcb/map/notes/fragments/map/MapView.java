package ru.vpcb.map.notes.fragments.map;

import androidx.annotation.NonNull;

import ru.vpcb.map.notes.base.MvpView;
import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

public interface MapView extends MvpView {

    void animateCamera(@NonNull Location currentLocation);

    void displayNoteOnMap(Note note);

    void showLocationAlertDialog();

    void openSettings();

    void exit();

    void sendAnalytics(Location location);

    void sendAnalytics(Note note);

}
