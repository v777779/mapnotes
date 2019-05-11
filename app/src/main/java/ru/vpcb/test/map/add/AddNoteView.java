package ru.vpcb.test.map.add;

import ru.vpcb.test.map.base.MvpView;

public interface AddNoteView extends MvpView {
    void clearNoteText();

    void displayCurrentLocation(String address);

    void hideKeyboard();
}
