package ru.vpcb.map.notes.add;

import ru.vpcb.map.notes.base.MvpView;

public interface AddNoteView extends MvpView {
    void clearNoteText();

    void displayCurrentLocation(String address);

    void hideKeyboard();
}
