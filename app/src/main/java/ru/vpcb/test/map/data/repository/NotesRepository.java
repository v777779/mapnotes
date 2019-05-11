package ru.vpcb.test.map.data.repository;

import java.util.List;

import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.executors.IJob;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.model.Note;

public interface NotesRepository {
    void addNote(Note note);
    Result<List<Note>> getNotes(IJob<Note> replaceAuthorName);

    Result<List<Note>> getNotesByNoteText(String text, IJob<Note> replaceAuthorName);

    Result<List<Note>> getNotesByUser(String userId, String humanReadableName);

    void setExecutors(AppExecutors<List<Note>> appExecutors);
}
