package ru.vpcb.map.notes.search;

import android.app.Activity;
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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.IComponentFragment;
import ru.vpcb.map.notes.data.formatter.CoordinateFormatter;
import ru.vpcb.map.notes.data.formatter.LatLonFormatter;
import ru.vpcb.map.notes.data.repository.FirebaseNotesRepository;
import ru.vpcb.map.notes.data.repository.FirebaseUserRepository;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.executors.IListener;
import ru.vpcb.map.notes.ext.ValidationExt;
import ru.vpcb.map.notes.manager.FCManager;
import ru.vpcb.map.notes.model.Note;
import ru.vpcb.map.notes.search.adapter.NotesAdapter;

import static ru.vpcb.map.notes.activity.home.HomeActivity.DISPLAY_LOCATION;
import static ru.vpcb.map.notes.activity.home.HomeActivity.EXTRA_NOTE;

public class SearchNotesFragment extends Fragment implements SearchNotesView, IComponentFragment {

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

    private View rootView;
    private AppCompatActivity activity;


    @Override
    public void onAttach(Context context) {
        setupComponent((Activity) context);
        super.onAttach(context);

        oldAppExecutors = null;
        userRepository = new FirebaseUserRepository(appExecutors);
        notesRepository = new FirebaseNotesRepository(oldAppExecutors);
        presenter = new SearchNotesPresenter(appExecutors, userRepository, notesRepository);

        defaultUserName = context.getString(R.string.unknown_user);
        coordinateFormatter = new CoordinateFormatter();

        activity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_notes, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        Spinner searchOptions = rootView.findViewById(R.id.searchOptions);
        Button searchButton = rootView.findViewById(R.id.searchButton);
        TextView searchText = rootView.findViewById(R.id.searchText);

        searchOptions.setAdapter(ArrayAdapter.createFromResource(
                activity,
                R.array.search_options,
                android.R.layout.simple_dropdown_item_1line));
        adapter = new NotesAdapter(coordinateFormatter, new IListener<Note>() {
            @Override
            public void invoke(Note note) {
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(activity);
                Intent intent = new Intent(DISPLAY_LOCATION).putExtra(EXTRA_NOTE, note);
                broadcastManager.sendBroadcast(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(activity, layoutManager.getOrientation()));
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchNotes(searchText.getText().toString(),
                        searchOptions.getSelectedItemPosition(), defaultUserName);
            }
        });

        return rootView;
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
        if (activity != null) {
            getSnackBarWithOffset(R.string.loading_notes_error).show();

        }
    }

    @Override
    public void displayUnknownUserError() {
        if (activity != null) {
            getSnackBarWithOffset( R.string.unknown_user_error).show();

        }
    }

    @Override
    public void clearSearchResults() {
        adapter.clear();
    }

    @Override
    public boolean isOnline() {
        if (activity == null) {
            return false;
        }
        return ValidationExt.isOnline(activity);
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

    private Snackbar getSnackBarWithOffset(int id) {
        Snackbar snackbar = Snackbar.make(rootView, id, Snackbar.LENGTH_LONG);
        CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams();
        int margin = activity.getResources().getDimensionPixelSize(R.dimen.snackbar_bottom_margin);
        lp.setMargins(0, 0, 0, margin);
        snackbar.getView().setLayoutParams(lp);
        return snackbar;
    }
}
