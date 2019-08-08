package ru.vpcb.map.notes.add;

import ru.vpcb.map.notes.base.MvpView;
import ru.vpcb.map.notes.model.Note;

public interface AddNoteView extends MvpView {
    void clearNoteText();

    void displayCurrentLocation(String address);

    void hideKeyboard();

    void displayCurrentLocation(Note note);
}
