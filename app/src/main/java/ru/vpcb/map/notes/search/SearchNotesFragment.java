package ru.vpcb.map.notes.search;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.IComponentFragment;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.data.formatter.CoordinateFormatter;
import ru.vpcb.map.notes.data.formatter.LatLonFormatter;
import ru.vpcb.map.notes.executors.IConsumer;
import ru.vpcb.map.notes.ext.ValidationExt;
import ru.vpcb.map.notes.manager.FCManager;
import ru.vpcb.map.notes.model.Note;
import ru.vpcb.map.notes.search.adapter.NotesAdapter;

import static ru.vpcb.map.notes.activity.home.HomeActivity.DISPLAY_LOCATION;
import static ru.vpcb.map.notes.activity.home.HomeActivity.EXTRA_NOTE;

public class SearchNotesFragment extends Fragment implements SearchNotesView, IComponentFragment {

    @Inject
    SearchNotesMvpPresenter presenter;
    @Inject
    Activity activity;

    private View rootView;
    private ProgressBar progressBar;

    private LatLonFormatter coordinateFormatter;
    private NotesAdapter adapter;
    private String defaultUserName;


    @Override
    public void onAttach(Context context) {
        setupComponent((Activity) context);
        super.onAttach(context);

        defaultUserName = context.getString(R.string.unknown_user);
        coordinateFormatter = new CoordinateFormatter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_notes, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        Spinner searchOptions = rootView.findViewById(R.id.searchOptions);
        Button searchButton = rootView.findViewById(R.id.searchButton);
        TextView searchText = rootView.findViewById(R.id.searchText);
        progressBar = rootView.findViewById(R.id.progressBar);

        searchOptions.setAdapter(ArrayAdapter.createFromResource(
                activity,
                R.array.search_options,
                android.R.layout.simple_dropdown_item_1line));

        adapter = new NotesAdapter(coordinateFormatter, new IConsumer<Note>() {
            @Override
            public void accept(Note note) {
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(activity);
                Intent intent = new Intent(DISPLAY_LOCATION).putExtra(EXTRA_NOTE, note);  // message for BroadCastReceiver
                broadcastManager.sendBroadcast(intent);
            }
        });
        SwipeHelper swipeHelper = new SwipeHelper(ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
        swipeHelper.attachToRecyclerView(recyclerView);

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
            Snackbar.make(rootView, R.string.loading_notes_error, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void displayUnknownNoteError() {
        if (activity != null) {
            Snackbar.make(rootView, R.string.unknown_note_error, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void displayUnknownUserError() {
        if (activity != null) {
            Snackbar.make(rootView, R.string.unknown_user_error, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void displayNoInternetError() {
        if (activity != null) {
            Snackbar.make(rootView, R.string.no_internet_error, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void displayRemoveNoteError() {
        if (activity != null) {
            Snackbar.make(rootView, R.string.remove_note_error, Snackbar.LENGTH_LONG).show();
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

    @Override
    public void showProgress(boolean isVisible) {
        if (progressBar == null) {
            return;
        }
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public String defaultUserName() {
        return defaultUserName;
    }

    @Override
    public void refreshAdapter() {
        if (adapter == null) {
            return;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshFragment() {
        if (activity == null) {
            return;
        }
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(activity);
        Intent intent = new Intent(HomeActivity.REFRESH_NOTES);  // message for BroadCastReceiver
        broadcastManager.sendBroadcast(intent);
    }


    private void getAlertDialog(int position) {
        if (activity == null) {
            return;
        }
        Note note = adapter.getNote(position);
        if (note == null || TextUtils.isEmpty(note.getText())) {
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage(activity.getString(R.string.remove_note_message, note.getText()))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (presenter == null) {
                            return;
                        }
                        presenter.onPositive(note);

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (presenter == null) {
                            return;
                        }
                        presenter.onNegative();

                    }
                }).create();

        dialog.show();
    }


// classes

    private class SwipeCallback extends ItemTouchHelper.SimpleCallback {

        SwipeCallback(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            getAlertDialog(position);
        }
    }

    private class SwipeHelper extends ItemTouchHelper {

        private SwipeHelper(int movement) {
            super(new SwipeCallback(0, movement));
        }
    }

}
