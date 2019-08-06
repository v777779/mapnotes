package ru.vpcb.map.notes.data.repository;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.executors.IJob;
import ru.vpcb.map.notes.model.Note;

public class FirebaseNotesRepository implements NotesRepository {

    private IAppExecutors appExecutors;
    private AppExecutors oldAppExecutors;
    private FirebaseDatabase database;

    private String notesPath;
    private String textKey;
    private String userKey;


    public FirebaseNotesRepository(IAppExecutors appExecutors) {
        this.appExecutors = appExecutors;
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
    public Single<Result<List<Note>>> getNotes() {
        return Single.<Result<List<Note>>>create(emitter -> {
            database
                    .getReference(notesPath)
                    .addListenerForSingleValueEvent(new ValueEventListener() {  // self removed
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (emitter.isDisposed()) {
                                return;
                            }
                            if (dataSnapshot.exists()) {
                                List<Note> noteResults = new ArrayList<>();
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Note note = child.getValue(Note.class);
                                    noteResults.add(note);
                                }
                                emitter.onSuccess(new Result.Success<>(noteResults));
                            } else {
                                emitter.onSuccess(new Result.Error<>(new NullPointerException()));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            if (emitter.isDisposed()) {
                                return;
                            }
                            emitter.onSuccess(new Result.Error<>(databaseError.toException()));
                        }
                    });
        }).subscribeOn(appExecutors.net());

    }

    @Override
    public Result<List<Note>> getNotes(IJob<Note> replaceAuthorName) {

        database.getReference(notesPath)
                .addListenerForSingleValueEvent(new ValueEventListener() {          // self removed
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            List<Note> noteResults = new ArrayList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Note note = child.getValue(Note.class);
// TODO check sync
                                replaceAuthorName.join(note);
                                noteResults.add(note);
                            }
                            Result<List<Note>> result = new Result.Success<>(noteResults);
                            oldAppExecutors.resume(result);

                        } else {
                            Result<List<Note>> result = new Result.Error<>(new NullPointerException());
                            oldAppExecutors.resume(result);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Result<List<Note>> result = new Result.Error<>(databaseError.toException());
                        oldAppExecutors.resume(result);
                    }
                });

        return null;
    }


    @Override
    public Single<Result<List<Note>>> getNotesByNoteText(String text) {
        return Single.<Result<List<Note>>>create(emitter -> {
            database.getReference(notesPath)
                    .orderByChild(textKey)
                    .startAt(text)
                    .endAt(text + "\uf8ff")                                     // MUST BE HERE!!!
                    .addListenerForSingleValueEvent(new ValueEventListener() {  // self removed
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (emitter.isDisposed()) {
                                return;
                            }
                            if (dataSnapshot.exists()) {
                                List<Note> noteResults = new ArrayList<>();
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Note note = child.getValue(Note.class);
                                    noteResults.add(note);
                                }
                                emitter.onSuccess(new Result.Success<>(noteResults));

                            } else {
                                emitter.onSuccess(new Result.Error<>(new NullPointerException()));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            if (emitter.isDisposed()) {
                                return;
                            }
                            emitter.onSuccess(new Result.Error<>(databaseError.toException()));
                        }

                    });
        }).subscribeOn(appExecutors.net());
    }

    @Override
    public Single<Result<List<Note>>> getNotesByUser(String userId, String humanReadableName) {
        return Single.<Result<List<Note>>>create(emitter -> {
            database.getReference(notesPath)
                    .orderByChild(userKey)
                    .equalTo(userId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {  // self removed
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (emitter.isDisposed()) {
                                return;
                            }
                            if (dataSnapshot.exists()) {
                                List<Note> noteResults = new ArrayList<>();
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Note note = child.getValue(Note.class);
                                    if (note == null) {
                                        continue;
                                    }
                                    note.setUser(humanReadableName);
                                    noteResults.add(note);
                                }
                                emitter.onSuccess(new Result.Success<>(noteResults));

                            } else {
                                emitter.onSuccess(new Result.Error<>(new NullPointerException()));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            if (emitter.isDisposed()) {
                                return;
                            }
                            emitter.onSuccess(new Result.Error<>(databaseError.toException()));
                        }
                    });
// TODO replace
        }).subscribeOn(appExecutors.net());
    }

    @Override
    public void setExecutors(AppExecutors appExecutors) {
        this.oldAppExecutors = appExecutors;
    }

}

// alternative
//    public Observable<Result<Note>> getNote() {
//        return Observable.<Result<Note>>create(emitter -> {
//            database.getReference(notesPath)
//                    .addListenerForSingleValueEvent(new ValueEventListener() {  // self removed
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (emitter.isDisposed()) {
//                                return;
//                            }
//                            if (dataSnapshot.exists()) {
//                                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                                    Note note = child.getValue(Note.class);
//                                    emitter.onNext(new Result.Success<>(note));
//                                }
//                                emitter.onComplete();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            if (emitter.isDisposed()) {
//                                return;
//                            }
//                            emitter.onNext(new Result.Error<>(databaseError.toException()));
//                            emitter.onComplete();
//                        }
//                    });
//        }).subscribeOn(Schedulers.io());
//
//    }
