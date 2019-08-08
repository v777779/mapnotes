package ru.vpcb.map.notes.data.repository;

import java.util.List;

import io.reactivex.Single;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.model.Note;

public interface NotesRepository {
    void addNote(Note note);

    Single<Result<List<Note>>> getNotes();

    Single<Result<List<Note>>> getNotesByNoteText(String text);

    Single<Result<List<Note>>> getNotesByUser(String userId, String humanReadableName);

    Single<Result<String>> removeNote(Note note);

}
