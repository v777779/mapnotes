package ru.vpcb.map.notes.search;

import ru.vpcb.map.notes.base.MvpPresenter;

public interface SearchNotesMvpPresenter extends MvpPresenter<SearchNotesView> {
    void getNotes(String defaultUserName);

    void searchNotes(String text, int categoryPosition, String defaultUserName);


    void onPositive(int position);

    void onNegative();
}
