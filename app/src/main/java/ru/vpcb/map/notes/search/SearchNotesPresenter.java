package ru.vpcb.map.notes.search;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.vpcb.map.notes.base.ScopedPresenter;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.executors.IJob;
import ru.vpcb.map.notes.model.AuthUser;
import ru.vpcb.map.notes.model.Note;

public class SearchNotesPresenter extends ScopedPresenter<SearchNotesView>
        implements SearchNotesMvpPresenter {

    IAppExecutors appExecutors;

    private AppExecutors oldAppExecutors;
    private UserRepository userRepository;
    private NotesRepository notesRepository;

    private SearchNotesView view;
    private int notesSearchCategory;
    private int usersSearchCategory;
    private CompositeDisposable composite;

    public SearchNotesPresenter(IAppExecutors appExecutors, UserRepository userRepository,
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

// вариант 1
        Observable<Result<Note>> names = notesRepository.getNote()
                .observeOn(appExecutors.net())
                .flatMap(result -> {
                    if (result instanceof Result.Success) {
                        return Observable.zip(Observable.just(result),
                                userRepository
                                        .getHumanReadableName(result.getData().getUser())
                                        .toObservable()
                                        .observeOn(appExecutors.net()),
                                (r, s) -> {
                                    if (s instanceof Result.Success)
                                        r.getData().setUser(s.getData());
                                    else
                                        r.getData().setUser(defaultUserName);
                                    return r;
                                });
                    } else {
                        return Observable.fromCallable(() -> result); // фиксированный объект смысла нет
                    }
                });

        Disposable disposableNames = names.observeOn(appExecutors.ui())
                .subscribe(n -> {
                    if (n instanceof Result.Success) {
                        Note note = n.getData();
                        System.out.println(note.getUser() + " " + note.getLatitude() + " " + note.getLongitude());
                    } else {
                        System.out.println(n.getException().toString());
                    }
                });

// вариант 2
        Observable<Result<Note>> notes = notesRepository.getNote();
        Observable<Result<String>> strings = notesRepository.getNote()
                .flatMap(result -> {
                    if (result instanceof Result.Success) {
                        return userRepository
                                .getHumanReadableName(result.getData().getUser())
                                .toObservable();
                    }else {
                        return Observable.just(new Result.Error<>(result.getException()));
                    }
                }
        );

        Disposable disposables = Observable.zip(notes, strings, (n, s) -> {
            if (n instanceof Result.Success) {
                Note note = n.getData();
                if (s instanceof Result.Success) {
                    note.setUser(s.getData());
                } else {
                    note.setUser(defaultUserName);
                }
                return new Result.Success<>(note);
            } else {
                return n;
            }
        })
                .subscribeOn(appExecutors.net())
                .observeOn(appExecutors.ui())
                .subscribe(n -> {
                    if (n instanceof Result.Success) {
                        Note note = n.getData();
                        System.out.println(note.getUser() + " " + note.getLatitude() + " " + note.getLongitude());
                    } else {
                        System.out.println(n.getData().toString());
                    }
                });

// вариант 3
        Disposable disposable = notesRepository
                .getNotes()
                .observeOn(appExecutors.net())
                .map(result -> {
                    if (result instanceof Result.Success) {
                        List<Note> list = result.getData();
                        for (Note note : list) {
                            Result<String> resultName = userRepository
                                    .getHumanReadableName(note.getUser())
                                    .blockingGet();
                            if (resultName instanceof Result.Success) {
                                note.setUser(resultName.getData());
                            } else {
                                note.setUser(defaultUserName);
                            }
                        }

                        return new Result.Success<>(list);
                    } else {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result instanceof Result.Error) {
                        view.displayLoadingNotesError();
                    }
                    if (result instanceof Result.Success) {
                        for (Object note : (List) result.getData()) {
                            view.displayNote((Note) note);
                        }
                    }

                });
        composite.add(disposable);
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
            oldAppExecutors = new AppExecutors() {
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
            notesRepository.setExecutors(oldAppExecutors);
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
        Disposable disposable = userRepository.getHumanReadableName(note.getUser())
                .subscribe(result -> {
                    if (result instanceof Result.Success) {
                        note.setUser(result.getData());
                    } else {
                        note.setUser(defaultUserName);
                    }
                });
        if (composite != null) {
            composite.add(disposable);
        }
    }
}
