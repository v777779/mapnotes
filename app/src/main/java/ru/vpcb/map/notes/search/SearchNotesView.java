package ru.vpcb.map.notes.search;

import ru.vpcb.map.notes.base.MvpView;
import ru.vpcb.map.notes.model.Note;

public interface SearchNotesView extends MvpView {
    void displayNote(Note note);

    void displayLoadingNotesError();

    void displayUnknownUserError();

    void displayUnknownNoteError();

    void displayNoInternetError();

    void clearSearchResults();

    boolean isOnline();

    void showProgress(boolean isVisible);

    String getDefaultUserName();

}
