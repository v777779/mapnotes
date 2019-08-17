package ru.vpcb.map.notes.fragments.search;

import ru.vpcb.map.notes.base.MvpPresenter;
import ru.vpcb.map.notes.model.Note;

public interface SearchNotesMvpPresenter extends MvpPresenter<SearchNotesView> {
    void getNotes(String defaultUserName);

    void searchNotes(String text, int categoryPosition, String defaultUserName);


    void onPositive(Note note);

    void onNegative();
}
