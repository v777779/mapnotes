package ru.vpcb.map.notes.fragment.add;

import ru.vpcb.map.notes.base.MvpPresenter;

public interface AddNoteMvpPresenter extends MvpPresenter<AddNoteView> {
    void getCurrentLocation();

    void addNote(String text);
}
