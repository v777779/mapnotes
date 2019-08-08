package ru.vpcb.map.notes.search;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import ru.vpcb.map.notes.base.ScopedPresenter;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.Note;

public class SearchNotesPresenter extends ScopedPresenter<SearchNotesView>
        implements SearchNotesMvpPresenter {
    private static final int NOTES_SEARCH_CATEGORY = 0;
    private static final int USERS_SEARCH_CATEGORY = 1;

    private IAppExecutors appExecutors;
    private UserRepository userRepository;
    private NotesRepository notesRepository;

    private SearchNotesView view;
    private CompositeDisposable composite;

    public SearchNotesPresenter(IAppExecutors appExecutors, UserRepository userRepository,
                         NotesRepository notesRepository) {
        this.appExecutors = appExecutors;
        this.userRepository = userRepository;
        this.notesRepository = notesRepository;
        this.view = null;
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
    public void getNotes(String defaultUserName) {  // defaultUserName подстановка
        if (view == null) {
            return;
        }
        view.clearSearchResults();

        if (!view.isOnline()) {
            view.displayNoInternetError();
            return;
        }

        view.showProgress(true);
        Single<Result<List<Note>>> notes = notesRepository
                .getNotes()
                .compose(updateNames(defaultUserName));

        Disposable disposable = notes
                .observeOn(appExecutors.ui())
                .subscribe(result -> {
                    view.showProgress(false);
                    if (result instanceof Result.Error) {
                        view.displayLoadingNotesError();
                    }
                    if (result instanceof Result.Success) {
                        for (Note note : result.getData()) {
                            view.displayNote(note);
                        }
                    }
                });

        composite.add(disposable);
    }


    @Override
    public void searchNotes(String text, int categoryPosition, String defaultUserName) {
        if (view == null) {
            return;
        }

        if (!view.isOnline()) {
            view.displayNoInternetError();
            return;
        }

        view.clearSearchResults();
        if (text.isEmpty()) {
            getNotes(defaultUserName); // just reload all notes
            return;
        }

        if (categoryPosition == NOTES_SEARCH_CATEGORY) {  // search notes name
            Disposable disposable = notesRepository
                    .getNotesByNoteText(text)
                    .compose(updateNames(defaultUserName))
                    .observeOn(appExecutors.ui())
                    .subscribe(result -> {
                        if (result instanceof Result.Error) {
                            view.displayUnknownNoteError();
                        }
                        if (result instanceof Result.Success) {
                            for (Object note : (List) result.getData()) {
                                view.displayNote((Note) note);
                            }
                        }
                    });
            composite.add(disposable);

        } else if (categoryPosition == USERS_SEARCH_CATEGORY) { // search user name

            Disposable disposable = userRepository
                    .getUserIdFromHumanReadableName(text)
                    .observeOn(appExecutors.net())
                    .flatMap(new Function<Result<String>, SingleSource<Result<List<Note>>>>() {
                        @Override
                        public SingleSource<Result<List<Note>>> apply(Result<String> result) throws Exception {
                            if (result instanceof Result.Success &&
                                    !TextUtils.isEmpty(result.getData())) {
                                String uid = result.getData();
                                return notesRepository.getNotesByUser(uid, text);
                            } else {
                                return Single.just(new Result.Error<>(new NullPointerException()));
                            }
                        }
                    }).observeOn(appExecutors.ui())
                    .subscribe(result -> {
                        if (result instanceof Result.Error) {
                            view.displayUnknownUserError();
                        }
                        if (result instanceof Result.Success) {
                            for (Object note : (List) result.getData()) {
                                view.displayNote((Note) note);
                            }
                        }
                    });
            composite.add(disposable);

        } else {
            throw new IllegalArgumentException("Incorrect ID of category");

        }
    }

    @Override
    public void onPositive(@NonNull Note note) {
        if (view == null) {
            return;
        }
        if (!view.isOnline()) {
            view.displayNoInternetError();
            return;
        }
        view.showProgress(true);
        Disposable disposable = notesRepository.removeNote(note)
                .observeOn(appExecutors.ui())
                .subscribe(result -> {
                    view.showProgress(false);
                    if (result instanceof Result.Success) {
                        view.refreshFragment();
                    } else {
                        view.displayRemoveNoteError();
                        view.refreshAdapter();
                    }
                });

        composite.add(disposable);
    }

    @Override
    public void onNegative() {
        if (view == null) {
            return;
        }
        view.refreshAdapter();
    }

//methods

    private SingleTransformer<Result<List<Note>>, Result<List<Note>>> updateNames(String defaultUserName) {
        return upstream -> upstream
                .observeOn(appExecutors.net())
                .flatMap((Function<Result<List<Note>>, SingleSource<Result<List<Note>>>>)
                        result -> {                                                                 // flatMap <R<List>> to Single<R<List>>
                            if (result instanceof Result.Success) {
                                // flatMap ordered <Note> to O<Note> ordered
                                return Observable
                                        .fromIterable(result.getData())                             // Observable<Note>
                                        .concatMap((Function<Note, ObservableSource<Note>>)
                                                note -> Observable.zip(                             // zip(O<Note>,O<String>) to O<Note>
                                                        Observable.just(new Result.Success<>(note)),
                                                        userRepository
                                                                .getHumanReadableName(note.getUser())
                                                                .toObservable()
                                                                .observeOn(appExecutors.net()),
                                                        (n, s) -> {
                                                            if (s instanceof Result.Success) {              // note, name-> note.set(name)
                                                                note.setUser(s.getData());
                                                            } else {
                                                                note.setUser(defaultUserName);
                                                            }
                                                            return note;
                                                        }))
                                        .toList()                                                   // Single<List>
                                        .map(Result.Success::new);                                  // Single<R<List>>
                            }
                            return Single.just(new Result.Error<>(result.getException()));
                        });
    }

}

// alternative
//
//    private SingleTransformer<Result<List<Note>>, Result<List<Note>>> updateNames2(String defaultUserName) {
//        return new SingleTransformer<Result<List<Note>>, Result<List<Note>>>() {
//            @Override
//            public SingleSource<Result<List<Note>>> apply(Single<Result<List<Note>>> upstream) {
//                return upstream
//                        .observeOn(appExecutors.net())
//                        .flatMap((Function<Result<List<Note>>, SingleSource<Result<List<Note>>>>)   // flatMap <R> to Single<R>
//                                result -> {
//                                    if (result instanceof Result.Success) {
//                                        Observable<Note> notes = Observable.fromIterable(result.getData());  // O<Note>
//                                        Observable<String> names = notes
//                                                .concatMap((Function<Note, ObservableSource<String>>) // flatMap ordered <Note> to O<String>
//                                                        note -> userRepository
//                                                                .getHumanReadableName(note.getUser())
//                                                                .toObservable()
//                                                                .observeOn(appExecutors.net())
//                                                                .map(r -> {
//                                                                    if (r instanceof Result.Success) {
//                                                                        return r.getData();
//                                                                    } else {
//                                                                        return defaultUserName;
//                                                                    }
//                                                                }));
//                                        return Observable.zip(notes, names, (n, s) -> {             // zip(O<Note>,O<String>) to O<Note>
//                                            n.setUser(s);
//                                            return n;
//                                        }).toList()
//                                                .map(Result.Success::new);
//                                    }
//                                    return Single.just(new Result.Error<>(result.getException()));
//                                });
//            }
//        };
//    }
//
// from single<list> to single<list>
//                    Single<Result<List<Note>>> notes = notesRepository.getNotes()
//                            .observeOn(appExecutors.net())
//                            .flatMap(new Function<Result<List<Note>>, SingleSource<Result<List<Note>>>>() {
//                                @Override
//                                public SingleSource<Result<List<Note>>> apply(Result<List<Note>> result) throws Exception {
//                                    if (result instanceof Result.Success) {
//                                        return Observable.fromIterable(result.getData())                        // Observable<Note>
//                                                .concatMap((Function<Note, ObservableSource<Note>>) note ->     // zip(<Note>,<Name>)
//                                                        Observable.zip(Observable.just(new Result.Success<>(note)),
//                                                                userRepository
//                                                                        .getHumanReadableName(note.getUser())
//                                                                        .toObservable()
//                                                                        .observeOn(appExecutors.net()),
//                                                                (n, s) -> {
//                                                                    if (s instanceof Result.Success) {          // note, name-> note.set(name)
//                                                                        note.setUser(s.getData());
//                                                                    } else {
//                                                                        note.setUser(defaultUserName);
//                                                                    }
//                                                                    return note;
//                                                                }))
//                                                .toList()                                                       // Single<List>
//                                                .map(Result.Success::new);                                      // Single<Result<List>>
//                                    }
//                                    return Single.just(new Result.Error<>(result.getException()));
//                                }
//                            });


// from observable<> to observable<>
//                    Observable<Result<Note>> notes = notesRepository.getNote()
//                            .observeOn(appExecutors.net())
//                            .concatMap(result -> {
//                                if (result instanceof Result.Success) {
//                                    return Observable.zip(Observable.just(result),
//                                            userRepository
//                                                    .getHumanReadableName(result.getData().getUser())
//                                                    .toObservable()
//                                                    .observeOn(appExecutors.net()),
//                                            (r, s) -> {
//                                                if (s instanceof Result.Success)
//                                                    r.getData().setUser(s.getData());
//                                                else
//                                                    r.getData().setUser(defaultUserName);
//                                                return r;
//                                            });
//                                } else {
//                                    return Observable.fromCallable(() -> result); // фиксированный объект смысла нет
//                                }
//                            });
//
//                    Disposable disposable = notes.observeOn(appExecutors.ui())
//                            .subscribe(result -> {
//                                if (result instanceof Result.Error) {
//                                    view.displayLoadingNotesError();
//                                }
//                                if (result instanceof Result.Success) {
//                                    Note note = result.getData();
//                                    view.displayNote((Note) note);
//                                }
//                            });

//    private Single<Result<List<Note>>> updateUserNames(Single<Result<List<Note>>> single,
//                                                       String defaultUserName) {
//        return single
//                .observeOn(appExecutors.net())
//                .flatMap(new Function<Result<List<Note>>, SingleSource<Result<List<Note>>>>() {
//                    @Override
//                    public SingleSource<Result<List<Note>>> apply(Result<List<Note>> result) throws Exception {
//                        if (result instanceof Result.Success) {
//                            Observable<Note> notes = Observable.fromIterable(result.getData());
//                            Observable<String> names = Observable.fromIterable(result.getData())
//                                    .concatMap(new Function<Note, ObservableSource<String>>() {
//                                        @Override
//                                        public ObservableSource<String> apply(Note note) throws Exception {
//                                            return userRepository
//                                                    .getHumanReadableName(note.getUser())
//                                                    .toObservable()
//                                                    .observeOn(appExecutors.net())
//                                                    .map(r -> {
//                                                        if (r instanceof Result.Success) {
//                                                            return r.getData();
//                                                        } else {
//                                                            return defaultUserName;
//                                                        }
//                                                    });
//                                        }
//                                    });
//                            return Observable.zip(notes, names, (n, s) -> {
//                                n.setUser(s);
//                                return n;
//                            }).toList()
//                                    .map(Result.Success::new);
//                        }
//                        return Single.just(new Result.Error<>(result.getException()));
//                    }
//                });
//
//    }