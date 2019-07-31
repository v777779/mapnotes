package ru.vpcb.map.notes.data.repository;

import java.util.List;

import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.executors.IJob;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.model.Note;

public interface NotesRepository {
    void addNote(Note note);
    Result<List<Note>> getNotes(IJob<Note> replaceAuthorName);

    Result<List<Note>> getNotesByNoteText(String text, IJob<Note> replaceAuthorName);

    Result<List<Note>> getNotesByUser(String userId, String humanReadableName);

    void setExecutors(AppExecutors appExecutors);
}
