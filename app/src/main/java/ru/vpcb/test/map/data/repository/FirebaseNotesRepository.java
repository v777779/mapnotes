package ru.vpcb.test.map.data.repository;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ru.vpcb.test.map.AppExecutors;
import ru.vpcb.test.map.Sync;
import ru.vpcb.test.map.data.IJob;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.model.Note;

public class FirebaseNotesRepository implements NotesRepository {

    private AppExecutors mAppExecutors;
    private FirebaseDatabase database;

    private String notesPath;
    private String textKey;
    private String userKey;


    public FirebaseNotesRepository(AppExecutors mAppExecutors) {
        this.mAppExecutors = mAppExecutors;
        this.notesPath = "notes";
        this.textKey = "text";
        this.userKey = "user";
        this.database = FirebaseDatabase.getInstance();
    }


    @Override
    public void addNote(Note note) {
        DatabaseReference notesRef = database.getReference(notesPath);
        DatabaseReference newNoteRef = notesRef.push();
        newNoteRef.setValue(note);
    }


    @Override
    public Result<List<Note>> getNotes(IJob<Note> replaceAuthorName) {
        final Sync<List<Note>> sync = new Sync<>();
        database.getReference(notesPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Note> noteResults = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Note note = child.getValue(Note.class);
                        replaceAuthorName.join(note);
                        noteResults.add(note);
                    }
                    Result<List<Note>> result = new Result.Success<>(noteResults);
                    sync.setResult(result);
                } else {
                    Result<List<Note>> result = new Result.Error<>(new NullPointerException());
                    sync.setResult(result);
                }
                sync.unlock();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Result<List<Note>> result = new Result.Error<>(databaseError.toException());
                sync.setResult(result);
                sync.unlock();
            }
        });

        sync.waiting();
        return sync.getResult();
    }


    @Override
    public Result<List<Note>> getNotesByNoteText(String text, IJob<Note> replaceAuthorName) {
        final Sync<List<Note>> sync = new Sync<>();
        database.getReference(notesPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Note> noteResults = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Note note = child.getValue(Note.class);
                        replaceAuthorName.join(note);
                        noteResults.add(note);
                    }
                    Result<List<Note>> result = new Result.Success<>(noteResults);
                    sync.setResult(result);
                } else {
                    Result<List<Note>> result = new Result.Error<>(new NullPointerException());
                    sync.setResult(result);
                }
                sync.unlock();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Result<List<Note>> result = new Result.Error<>(databaseError.toException());
                sync.setResult(result);
                sync.unlock();
            }
        });

        sync.waiting();
        return sync.getResult();
    }

    @Override
    public Result<List<Note>> getNotesByUser(String userId, String humanReadableName) {

        final Sync<List<Note>> sync = new Sync<>();
        database.getReference(notesPath)
                .orderByChild(userKey)
                .equalTo(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            List<Note> noteResults = new ArrayList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Note note = child.getValue(Note.class);
                                if (note != null) note.setUser(humanReadableName);
                                noteResults.add(note);
                            }
                            Result<List<Note>> result = new Result.Success<>(noteResults);
                            sync.setResult(result);
                        } else {
                            Result<List<Note>> result = new Result.Error<>(new NullPointerException());
                            sync.setResult(result);
                        }
                        sync.unlock();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Result<List<Note>> result = new Result.Error<>(databaseError.toException());
                        sync.setResult(result);
                        sync.unlock();
                    }
                });

        sync.waiting();
        return sync.getResult();
    }


}
