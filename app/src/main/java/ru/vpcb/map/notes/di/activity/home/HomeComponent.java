package ru.vpcb.map.notes.di.activity.home;

import dagger.Subcomponent;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.fragment.add.AddNoteFragment;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.fragment.map.GoogleMapFragment;
import ru.vpcb.map.notes.fragment.search.SearchNotesFragment;

@ActivityScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {

    void inject(HomeActivity activity);
    void inject(GoogleMapFragment fragment);
    void inject(SearchNotesFragment fragment);
    void inject(AddNoteFragment fragment);
}
