package ru.vpcb.map.notes.add;

import android.content.Context;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.data.formatter.FullAddressFormatter;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.AddressLocationProvider;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.FirebaseNotesRepository;
import ru.vpcb.map.notes.data.repository.FirebaseUserRepository;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.executors.IAppExecutors;

public class AddNoteFragment extends Fragment implements AddNoteView {

    // TODO by inject
    @Inject
    IAppExecutors appExecutors;

    private AddNoteMvpPresenter presenter;
    private AppExecutors oldAppExecutors;
    private UserRepository userRepository;
    private NotesRepository notesRepository;
    private LocationProvider locationProvider;
    private LocationFormatter locationFormatter;

    private AppCompatActivity mActivity;
    private View mRootView;
    private EditText note;
    private TextView currentLocation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;

// TODO by inject
        oldAppExecutors = null;
        userRepository = new FirebaseUserRepository(appExecutors);
        notesRepository = new FirebaseNotesRepository(appExecutors);
        locationProvider = new AddressLocationProvider(mActivity);
        locationFormatter = new FullAddressFormatter(new Geocoder(mActivity));

        presenter = new AddNotePresenter(oldAppExecutors,userRepository,notesRepository,
                locationProvider,locationFormatter);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_add_note, container, false);

        note = mRootView.findViewById(R.id.note);
        currentLocation = mRootView.findViewById(R.id.currentLocation);
        Button add = mRootView.findViewById(R.id.add);


        note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                add.setEnabled(!(s == null || s.length() == 0));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = note.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    presenter.addNote(text);
                }
            }
        });

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
//        setProperty(Properties.FRAGMENT_CONTEXT, this.context!!)
        presenter.onAttach(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getCurrentLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
//        releaseProperties(Properties.FRAGMENT_CONTEXT)
        presenter.onDetach();
        super.onStop();
    }

    // support view

    @Override
    public void clearNoteText() {
        note.getText().clear();
    }

    @Override
    public void displayCurrentLocation(String address) {
        currentLocation.setText(address);
    }

    @Override
    public void hideKeyboard() {
        if(mActivity ==null)return;
        InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(note.getWindowToken(), 0);

    }
}
