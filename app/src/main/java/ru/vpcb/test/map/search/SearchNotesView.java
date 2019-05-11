package ru.vpcb.test.map.search;

import ru.vpcb.test.map.base.MvpView;
import ru.vpcb.test.map.model.Note;

public interface SearchNotesView extends MvpView {
    void displayNote(Note note);

    void displayLoadingNotesError();

    void displayUnknownUserError();

    void clearSearchResults();
}
