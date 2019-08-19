package ru.vpcb.map.notes.fragments.add;

import ru.vpcb.map.notes.base.MvpPresenter;

public interface AddNoteMvpPresenter extends MvpPresenter<AddNoteView> {
    void getCurrentLocation();

    void addNote(String text);

}
