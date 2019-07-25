package ru.vpcb.test.map.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.vpcb.test.map.R;
import ru.vpcb.test.map.data.formatter.CoordinateFormatter;
import ru.vpcb.test.map.data.formatter.LatLonFormatter;
import ru.vpcb.test.map.data.repository.FirebaseNotesRepository;
import ru.vpcb.test.map.data.repository.FirebaseUserRepository;
import ru.vpcb.test.map.data.repository.NotesRepository;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.executors.IAppExecutors;
import ru.vpcb.test.map.executors.IListener;
import ru.vpcb.test.map.model.Note;
import ru.vpcb.test.map.search.adapter.NotesAdapter;

import static ru.vpcb.test.map.activity.home.HomeActivity.DISPLAY_LOCATION;
import static ru.vpcb.test.map.activity.home.HomeActivity.EXTRA_NOTE;

public class SearchNotesFragment extends Fragment implements SearchNotesView {

    // TODO by inject
    @Inject
    IAppExecutors appExecutors;

    private AppExecutors oldAppExecutors;
    private UserRepository userRepository;
    private NotesRepository notesRepository;
    private SearchNotesMvpPresenter presenter;

    private String defaultUserName;
    private LatLonFormatter coordinateFormatter;
    private NotesAdapter adapter;

    private View mRootView;
    private AppCompatActivity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        oldAppExecutors = null;
        userRepository = new FirebaseUserRepository(appExecutors);
        notesRepository = new FirebaseNotesRepository(oldAppExecutors);
        presenter = new SearchNotesPresenter(oldAppExecutors, userRepository, notesRepository);

        defaultUserName = context.getString(R.string.unknown_user);
        coordinateFormatter = new CoordinateFormatter();

        mActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_search_notes, container, false);

        RecyclerView recyclerView = mRootView.findViewById(R.id.recyclerView);
        Spinner searchOptions = mRootView.findViewById(R.id.searchOptions);
        Button searchButton = mRootView.findViewById(R.id.searchButton);
        TextView searchText = mRootView.findViewById(R.id.searchText);

        searchOptions.setAdapter(ArrayAdapter.createFromResource(
                mActivity,
                R.array.search_options,
                android.R.layout.simple_dropdown_item_1line));
        adapter = new NotesAdapter(coordinateFormatter, new IListener<Note>() {
            @Override
            public void invoke(Note note) {
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(mActivity);
                Intent intent = new Intent(DISPLAY_LOCATION).putExtra(EXTRA_NOTE, note);
                broadcastManager.sendBroadcast(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(mActivity, layoutManager.getOrientation()));
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchNotes(searchText.getText().toString(),
                        searchOptions.getSelectedItemPosition(), defaultUserName);
            }
        });

        return mRootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getNotes(defaultUserName);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        presenter.onDetach();
        super.onStop();
    }


// support view

    @Override
    public void displayNote(Note note) {
        adapter.addNote(note);
    }

    @Override
    public void displayLoadingNotesError() {
        if (mActivity != null) {
            Snackbar.make(mRootView, R.string.loading_notes_error, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void displayUnknownUserError() {
        if (mActivity != null) {
            Snackbar.make(mRootView, R.string.unknown_user_error, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void clearSearchResults() {
        adapter.clear();
    }
}
