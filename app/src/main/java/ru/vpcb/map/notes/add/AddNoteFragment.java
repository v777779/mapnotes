package ru.vpcb.map.notes.add;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import javax.inject.Inject;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.IComponentFragment;
import ru.vpcb.map.notes.manager.FCManager;
import ru.vpcb.map.notes.model.Note;

import static ru.vpcb.map.notes.activity.home.HomeActivity.DISPLAY_LOCATION;
import static ru.vpcb.map.notes.activity.home.HomeActivity.EXTRA_NOTE;

public class AddNoteFragment extends Fragment implements AddNoteView, IComponentFragment {

    @Inject
    AddNoteMvpPresenter presenter;
    @Inject
    Activity activity;

    private EditText note;
    private TextView currentLocation;

    @Override
    public void onAttach(Context context) {
        setupComponent((Activity)context);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_add_note, container, false);

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
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(note.getWindowToken(), 0);
    }

    @Override
    public void displayCurrentLocation(Note note) {
        if (activity == null) {
            return;
        }
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(activity);
        Intent intent = new Intent(DISPLAY_LOCATION).putExtra(EXTRA_NOTE, note);                    // message for BroadCastReceiver
        broadcastManager.sendBroadcast(intent);

    }

    @Override
    public void setupComponent(Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            MainApp.plus(activity).inject(this);
        } catch (Exception e) {
            FCManager.log(e);
        }
    }
}
