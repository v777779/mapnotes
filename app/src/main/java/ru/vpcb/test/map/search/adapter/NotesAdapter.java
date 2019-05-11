package ru.vpcb.test.map.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.vpcb.test.map.R;
import ru.vpcb.test.map.data.formatter.LatLonFormatter;
import ru.vpcb.test.map.executors.IListener;
import ru.vpcb.test.map.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private LatLonFormatter formatter;
    private IListener<Note> itemClick;
    private List<Note> notes;

    public NotesAdapter(LatLonFormatter formatter, IListener<Note> itemClick) {
        this.formatter = formatter;
        this.itemClick = itemClick;
        this.notes = new ArrayList<>();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(view, itemClick, formatter);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bindNote(notes.get(position));
    }

    @Override
    public int getItemCount() {
        if (notes == null) return 0;
        return notes.size();
    }

// methods

    void addNote(Note note) {
        this.notes.add(note);
        notifyDataSetChanged();
    }

    void clear() {
        this.notes.clear();
        notifyDataSetChanged();
    }

}
