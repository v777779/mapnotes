package ru.vpcb.map.notes.search;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.vpcb.map.notes.base.ScopedPresenter;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.executors.IJob;
import ru.vpcb.map.notes.model.AuthUser;
import ru.vpcb.map.notes.model.Note;

public class SearchNotesPresenter extends ScopedPresenter<SearchNotesView>
        implements SearchNotesMvpPresenter {

    private AppExecutors appExecutors;
    private UserRepository userRepository;
    private NotesRepository notesRepository;

    private SearchNotesView view;
    private int notesSearchCategory;
    private int usersSearchCategory;
    private CompositeDisposable composite;

    public SearchNotesPresenter(AppExecutors appExecutors, UserRepository userRepository,
                                NotesRepository notesRepository) {
        this.appExecutors = appExecutors;
        this.userRepository = userRepository;
        this.notesRepository = notesRepository;
        this.view = null;
        this.notesSearchCategory = 0;
        this.usersSearchCategory = 1;
    }


    @Override
    public void onAttach(@NonNull SearchNotesView view) {
        super.onAttach(view);
        this.view = view;
        this.composite = new CompositeDisposable();
    }

    @Override
    public void onDetach() {
        this.view = null;
        if (composite != null) {
            composite.dispose();
        }
        super.onDetach();

    }

    @Override
    public void getNotes(String defaultUserName) {
        if (view == null) return;
        view.clearSearchResults();
// TODO launch
        appExecutors = new AppExecutors() {
            @Override
            public <T> void resume(Result<T> result) {
                if (result instanceof Result.Error) {
                    view.displayLoadingNotesError();
                }
                if (result instanceof Result.Success) {
                    for (Object note : (List) result.getData()) {
                        view.displayNote((Note) note);
                    }
                }

            }
        };
        IJob<Note> notesPreProcessor = new IJob<Note>() {
            @Override
            public void join(Note note) {
                replaceNoteAuthorIdToNameJob(note, defaultUserName);
            }
        };

        notesRepository.setExecutors(appExecutors);
        Result<List<Note>> notes = notesRepository.getNotes(notesPreProcessor);
    }

    @Override
    public void searchNotes(String text, int categoryPosition, String defaultUserName) {
        if (view == null) return;

        view.clearSearchResults();
        if (text.isEmpty()) {
            getNotes(defaultUserName);
            return;
        }

        if (categoryPosition == this.notesSearchCategory) {
// TODO launch
            appExecutors = new AppExecutors() {
                @Override
                public <T> void resume(Result<T> result) {
                    if (result instanceof Result.Error) {
                        view.displayLoadingNotesError();
                    }
                    if (result instanceof Result.Success) {
                        for (Object note : (List) result.getData()) {
                            view.displayNote((Note) note);
                        }
                    }
                }
            };
// TODO sync problem
            IJob<Note> notePreProcessor = new IJob<Note>() {
                @Override
                public void join(Note note) {
                    replaceNoteAuthorIdToNameJob(note, defaultUserName);
                }
            };
            notesRepository.setExecutors(appExecutors);
            Result<List<Note>> notes = notesRepository.getNotesByNoteText(text, notePreProcessor);

        } else if (categoryPosition == usersSearchCategory) {
// TODO launch
            AppExecutors noteExecutors = new AppExecutors() {
                @Override
                public <T> void resume(Result<T> result) {
                    if (result instanceof Result.Error) {
                        view.displayLoadingNotesError();
                    }
                    if (result instanceof Result.Success) {
                        for (Object note : (List) result.getData()) {
                            view.displayNote((Note) note);
                        }
                    }
                }
            };

            AppExecutors userExecutors = new AppExecutors() {
                @Override
                public <T> void resume(Result<T> result) {
                    if (result instanceof Result.Success) {
// TODO launch
                        notesRepository.setExecutors(noteExecutors);
                        Result<List<Note>> notes = notesRepository.getNotesByUser(
                                ((AuthUser) result.getData()).getUid(), text);
                    }
                    if (result instanceof Result.Error) {
                        view.displayUnknownUserError();
                    }
                }
            };
// TODO launch
            userRepository.setAppExecutors(userExecutors);
            Result<String> userId = userRepository.getUserIdFromHumanReadableName(text);
        } else {
            throw new IllegalArgumentException("Incorrect ID of category");

        }
    }

//methods

    private void replaceNoteAuthorIdToNameJob(Note note, String defaultUserName) {
// TODO launch
        AppExecutors userExecutors = new AppExecutors() {
            @Override
            public <T> void resume(Result<T> result) {
                if (result instanceof Result.Success) {
                    note.setUser((String) result.getData());
                } else {
                    note.setUser(defaultUserName);
                }
            }
        };
// TODO launch
        userRepository.setAppExecutors(userExecutors);
        Disposable disposable = userRepository.getHumanReadableName(note.getUser())
                .subscribe(result -> {
                    if (result instanceof Result.Success) {
                        note.setUser((String) result.getData());
                    } else {
                        note.setUser(defaultUserName);
                    }
                });
        if (composite != null) {
            composite.add(disposable);
        }

    }
}
