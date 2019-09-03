package ru.vpcb.map.notes.fragments.search;

public class SearchNotesFragmentAccess {
    public static void set(SearchNotesFragment fragment, SearchNotesMvpPresenter presenter) {
        fragment.presenter = presenter;
        fragment.activity = fragment.getActivity();
    }
}
