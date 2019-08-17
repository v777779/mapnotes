package ru.vpcb.map.notes.fragment.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.data.formatter.LatLonFormatter;
import ru.vpcb.map.notes.executors.IConsumer;
import ru.vpcb.map.notes.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private LatLonFormatter formatter;
    private IConsumer<Note> itemClick;
    private List<Note> notes;

    public NotesAdapter(LatLonFormatter formatter, IConsumer<Note> itemClick) {
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

   public void addNote(Note note) {
        this.notes.add(note);
        notifyDataSetChanged();
    }

    public void clear() {
        this.notes.clear();
        notifyDataSetChanged();
    }

    public void removeNote(int position) {
        if(notes == null || position <0 || position >= notes.size()){
            return;
        }
        this.notes.remove(position);
        notifyDataSetChanged();
    }

    public Note getNote(int position){
        if(notes == null || position <0 || position >= notes.size()){
            return null;
        }
        return notes.get(position);
    }

}
