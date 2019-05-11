package ru.vpcb.test.map.add;

import android.app.Activity;
import android.content.Context;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

import ru.vpcb.test.map.data.formatter.FullAddressFormatter;
import ru.vpcb.test.map.data.formatter.LocationFormatter;
import ru.vpcb.test.map.data.provider.AddressLocationProvider;
import ru.vpcb.test.map.data.provider.LocationProvider;
import ru.vpcb.test.map.data.repository.FirebaseNotesRepository;
import ru.vpcb.test.map.data.repository.FirebaseUserRepository;
import ru.vpcb.test.map.data.repository.NotesRepository;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;

public class AddNoteFragment extends Fragment implements AddNoteView{
// TODO by inject
    private AddNoteMvpPresenter presenter;

    private AppExecutors appExecutors;
    private UserRepository userRepository;
    private NotesRepository notesRepository;
    private LocationProvider locationProvider;
    private LocationFormatter locationFormatter;

    private AppCompatActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = (AppCompatActivity)context;

        appExecutors = null;
        userRepository = new FirebaseUserRepository(appExecutors);
        notesRepository = new FirebaseNotesRepository(appExecutors);
        locationProvider = new AddressLocationProvider(mActivity);
// TODO check
        locationFormatter = new FullAddressFormatter(new Geocoder(mActivity, Locale.ENGLISH));


    }

    @Override
    public void clearNoteText() {

    }

    @Override
    public void displayCurrentLocation(String address) {

    }

    @Override
    public void hideKeyboard() {

    }
}
