package ru.vpcb.map.notes.di;

import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.fragments.add.AddNoteFragment;
import ru.vpcb.map.notes.fragments.map.GoogleMapFragment;
import ru.vpcb.map.notes.fragments.search.SearchNotesFragment;

public class HomeAdapter implements HomeComponent {
    @Override
    public void inject(HomeActivity activity) {

    }

    @Override
    public void inject(GoogleMapFragment fragment) {

    }

    @Override
    public void inject(SearchNotesFragment fragment) {

    }

    @Override
    public void inject(AddNoteFragment fragment) {

    }
}
