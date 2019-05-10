package ru.vpcb.test.map.map;

import android.support.annotation.NonNull;

import ru.vpcb.test.map.base.MvpView;
import ru.vpcb.test.map.model.Location;
import ru.vpcb.test.map.model.Note;

public interface MapView extends MvpView {

    void animateCamera(@NonNull Location currentLocation);

    void displayNoteOnMap(Note note);
    
}
