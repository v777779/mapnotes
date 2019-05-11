package ru.vpcb.test.map.search;

import ru.vpcb.test.map.base.MvpPresenter;

public interface SearchNotesMvpPresenter extends MvpPresenter<SearchNotesView> {
    void getNotes(String defaultUserName);

    void searchNotes(String text, int categoryPosition, String defaultUserName);
}
