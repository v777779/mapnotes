package ru.vpcb.map.notes.search.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.data.formatter.LatLonFormatter;
import ru.vpcb.map.notes.executors.IListener;
import ru.vpcb.map.notes.model.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {


    private IListener<Note> itemClick;
    private LatLonFormatter formatter;

    private TextView noteText;
    private TextView noteLocation;
    private TextView noteUser;


    public NoteViewHolder(@NonNull View view, IListener<Note> itemClick, LatLonFormatter formatter) {
        super(view);
        this.itemClick = itemClick;
        this.formatter = formatter;

        this.noteText = view.findViewById(R.id.noteText);
        this.noteLocation = view.findViewById(R.id.noteLocation);
        this.noteUser = view.findViewById(R.id.noteUser);

    }

    void bindNote(Note note) {
        if (note == null) return;

            noteText.setText(note.getText());
            noteLocation.setText(formatter.format(note.getLatitude(), note.getLongitude()));
            noteUser.setText(note.getUser());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.invoke(note);

                }
            });

    }

}
