package ru.vpcb.map.notes.fragments.add;

public class AddNoteFragmentAccess {
    public static void set(AddNoteFragment fragment, AddNoteMvpPresenter presenter) {
        fragment.presenter = presenter;
        fragment.activity = fragment.getActivity();

    }
}
