package ru.vpcb.map.notes.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.Note;

public class FirebaseNotesRepository implements NotesRepository {

    private IAppExecutors appExecutors;
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
                                    if (note == null) {
                                        return;
                                    }
                                    note.setKey(child.getKey());
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
                                    if (note == null) {
                                        continue;
                                    }
                                    note.setKey(child.getKey());
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
                                    note.setKey(child.getKey());
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
        }).subscribeOn(appExecutors.net());
    }

    @Override
    public Single<Result<String>> removeNote(Note note) {

        return Single.create(new SingleOnSubscribe<Result<String>>() {
            @Override
            public void subscribe(SingleEmitter<Result<String>> emitter) throws Exception {
                DatabaseReference notesRef = database.getReference(notesPath + "/" + note.getKey());
                notesRef.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                        if(emitter.isDisposed()){
                            return;
                        }
                        if(databaseError == null){
                            emitter.onSuccess(new Result.Success<>(databaseReference.getKey()));
                        }else {
                            emitter.onSuccess(new Result.Error<>(databaseError.toException()));
                        }
                    }
                });
            }
        }).subscribeOn(appExecutors.net());

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
