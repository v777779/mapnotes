package ru.vpcb.test.map.add;

import ru.vpcb.test.map.base.MvpPresenter;

public interface AddNoteMvpPresenter extends MvpPresenter<AddNoteView> {
    void getCurrentLocation();

    void addNote(String text);
}
