package ru.vpcb.map.notes.fragments.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.Note;

@RunWith(RobolectricTestRunner.class)   // setup Robolectric  build.gradle, gradle.properties
@Config(
        sdk = 28,
        application = MainApp.class
)
public class SearchNotesPresenterTests {
    private Result.Success<List<Note>> resultGetNotesSuccess;
    private Result.Error<List<Note>> resultGetNotesError;
    private Result.Success<List<Note>> resultSearchByTextSuccess;
    private Result.Success<List<Note>> resultSearchByUserSuccess;
    private Result.Success<String> resultRemoveNoteSuccess;
    private Result.Error<String> resultRemoveNoteError;


    private List<Note> testNotes;
    private Note testNote;
    private String authUserUID;
    private String authUserUID2;
    private String authUserName;
    private String authUserName2;
    private String defaultUserName;
    private String emptyUserName;
    private String searchByNoteRequest;
    private String searchByUserRequest;
    private int noteSearchCategory;
    private int userSearchCategory;
    private int unknownSearchCategory;
    private String searchEmptyRequest;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private SearchNotesView view;
    @Mock
    private IAppExecutors appExecutors;
    @Mock
    private UserRepository userRepository;
    @Mock
    private NotesRepository notesRepository;

    private SearchNotesMvpPresenter presenter;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        authUserUID = "11111111";
        authUserName = "Jacob";
        authUserUID2 = "22222222";
        authUserName2 = "Jenkins";
        emptyUserName = "";
        defaultUserName = "Unknown";

        resultRemoveNoteSuccess = new Result.Success<>("success");
        resultRemoveNoteError = new Result.Error<>(new RuntimeException("error"));

        noteSearchCategory = 0;
        userSearchCategory = 1;
        unknownSearchCategory = -1;
        searchByNoteRequest = "test note";
        searchByUserRequest = "Jenkins";
        searchEmptyRequest = "";
        testNotes = new ArrayList<>(Arrays.asList(
                new Note(0, 0, "test note 1_1", authUserUID),
                new Note(0, 0, "test note 2_1", authUserUID),
                new Note(0, 0, "test note 1_2", authUserUID2),
                new Note(0, 0, "test type 2_2", authUserUID2),
                new Note(0, 0, "test type 3_2", authUserUID2)));

        testNote = new Note(0, 0, "test note", authUserUID);
        resultGetNotesSuccess = new Result.Success<>(testNotes);
        resultGetNotesError = new Result.Error<>(new RuntimeException("get notes error"));
        resultSearchByTextSuccess = new Result.Success<>(testNotes.subList(0, 3));
        resultSearchByUserSuccess = new Result.Success<>(testNotes.subList(2, 5));

        presenter = new SearchNotesPresenter(appExecutors, userRepository, notesRepository);

        Mockito.when(appExecutors.ui()).then(
                (Answer<Scheduler>) invocation -> AndroidSchedulers.mainThread());
        Mockito.when(appExecutors.net()).then(
                (Answer<Scheduler>) invocation -> AndroidSchedulers.mainThread());

        Mockito.doAnswer(invocation -> null).when(view).displayNote(testNote);
        Mockito.doAnswer(invocation -> null).when(view).displayLoadingNotesError();
        Mockito.doAnswer(invocation -> null).when(view).displayUnknownUserError();
        Mockito.doAnswer(invocation -> null).when(view).displayUnknownNoteError();
        Mockito.doAnswer(invocation -> null).when(view).displayNoteDataError();
        Mockito.doAnswer(invocation -> null).when(view).displayNoInternetError();
        Mockito.doAnswer(invocation -> null).when(view).displayRemoveNoteError();
        Mockito.doAnswer(invocation -> null).when(view).displayDefaultUserNameError();
        Mockito.doAnswer(invocation -> null).when(view).clearSearchResults();
        Mockito.doAnswer(invocation -> null).when(view).showProgress(Mockito.anyBoolean());
        Mockito.doAnswer(invocation -> null).when(view).refreshAdapter();
        Mockito.doAnswer(invocation -> null).when(view).refreshFragment();

        Mockito.when(view.isOnline()).thenReturn(true);
        Mockito.when(view.defaultUserName()).thenReturn(defaultUserName);

        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(resultGetNotesSuccess));
        Mockito.when(notesRepository.getNotesByNoteText(searchByNoteRequest))
                .thenReturn(Single.just(resultSearchByTextSuccess));
        Mockito.when(notesRepository.getNotesByUser(authUserUID2, authUserName2))
                .thenReturn(Single.just(resultSearchByUserSuccess));
        Mockito.when(notesRepository.removeNote(testNote))
                .thenReturn(Single.just(resultRemoveNoteSuccess));

        Mockito.when(userRepository.getHumanReadableName(authUserUID)).thenReturn(
                Single.just(new Result.Success<>(authUserName)));
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Success<>(authUserName2)));
        Mockito.when(userRepository.getUserIdFromHumanReadableName(authUserName2)).thenReturn(
                Single.just(new Result.Success<>(authUserUID2)));

    }

// 0    getNotes   online, correct defaultUserName, correct humanReadableName, correct List<Note>

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameListNotesWithNonNullViewDisplayNoteCalled() {
        presenter.onAttach(view);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(1)).clearSearchResults();
        Mockito.verify(view, Mockito.times(1)).showProgress(true);
        Mockito.verify(view, Mockito.times(1)).showProgress(false);
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameListNotesWithNullViewDisplayNoteNotCalled() {
        presenter.onAttach(null);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameListNotesWithViewDetachedDisplayNoteNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

// 1    getNotes   not online, correct defaultUserName, correct humanReadableName, correct List<Note>

    @Test
    public void getNotesNotOnlineDefaultUserNameHumanReadableNameListNotesWithNonNullViewDisplayNoInternetErrorCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(view);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(1)).displayNoInternetError();
    }

    @Test
    public void getNotesNotOnlineDefaultUserNameHumanReadableNameListNotesWithNullViewDisplayNoInternetErrorNotCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(null);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).displayNoInternetError();
    }

    @Test
    public void getNotesNotOnlineDefaultUserNameHumanReadableNameListNotesWithViewDetachedDisplayNoInternetErrorNotCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).displayNoInternetError();
    }

// 2    getNotes   online, null defaultUserName, error humanReadableName, correct List<Note>

    @Test
    public void getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorCalled() {
        presenter.onAttach(view);
        presenter.getNotes(null);

        Mockito.verify(view, Mockito.times(1)).displayDefaultUserNameError();
    }

    @Test
    public void getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithNullViewDisplayDefaultUserNameErrorNotCalled() {
        presenter.onAttach(null);
        presenter.getNotes(null);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

    @Test
    public void getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithViewDetachedDisplayDefaultUserNameErrorNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(null);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

// 3    getNotes   online, empty defaultUserName, error humanReadableName, correct List<Note>

    @Test
    public void getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorCalled() {
        presenter.onAttach(view);
        presenter.getNotes(emptyUserName);

        Mockito.verify(view, Mockito.times(1)).displayDefaultUserNameError();
    }

    @Test
    public void getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorNotCalled() {
        presenter.onAttach(null);
        presenter.getNotes(emptyUserName);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

    @Test
    public void getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithViewDetachedDisplayDefaultUserNameErrorNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(emptyUserName);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

// 4    getNotes   online, defaultUserName, null humanReadableName, correct List<Note>

    @Test
    public void getNotesOnlineDefaultUserNameNullHumanReadableNameResultSuccessWithNonNullViewDisplayNoteCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Success<>(null)));

        presenter.onAttach(view);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(1)).clearSearchResults();
        Mockito.verify(view, Mockito.times(1)).showProgress(true);
        Mockito.verify(view, Mockito.times(1)).showProgress(false);
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

    @Test
    public void getNotesOnlineDefaultUserNameNullHumanReadableNameResultSuccessWithNonNullViewDisplayNoteNotCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Success<>(null)));

        presenter.onAttach(null);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

    @Test
    public void getNotesOnlineDefaultUserNameNullHumanReadableNameResultSuccessWithViewDetachedDisplayNoteNotCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Success<>(null)));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }


// 5    getNotes   online, defaultUserName, empty humanReadableName, correct List<Note>

    @Test
    public void getNotesOnlineDefaultUserNameEmptyHumanReadableNameResultSuccessWithNonNullViewDisplayNoteCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Success<>(emptyUserName)));

        presenter.onAttach(view);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(1)).clearSearchResults();
        Mockito.verify(view, Mockito.times(1)).showProgress(true);
        Mockito.verify(view, Mockito.times(1)).showProgress(false);
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

    @Test
    public void getNotesOnlineDefaultUserNameEmptyHumanReadableNameResultSuccessWithNonNullViewDisplayNoteNotCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Success<>(emptyUserName)));

        presenter.onAttach(null);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

    @Test
    public void getNotesOnlineDefaultUserNameEmptyHumanReadableNameResultSuccessWithViewDetachedDisplayNoteNotCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Success<>(emptyUserName)));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

// 6    getNotes   online, defaultUserName, humanReadableName, null List<Note>

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameNullResultSuccessWithNonNullViewDisplayLoadingNotesErrorCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(new Result.Success<>(null)));

        presenter.onAttach(view);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(1)).clearSearchResults();
        Mockito.verify(view, Mockito.times(1)).showProgress(true);
        Mockito.verify(view, Mockito.times(1)).showProgress(false);
        Mockito.verify(view, Mockito.times(1)).displayLoadingNotesError();
    }

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameNullResultSuccessWithNonNullViewDisplayLoadingNotesErrorNotCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(new Result.Success<>(null)));

        presenter.onAttach(null);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).displayLoadingNotesError();
    }

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameNullResultSuccessWithViewDetachedDisplayLoadingNotesErrorNotCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(new Result.Success<>(null)));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).displayLoadingNotesError();
    }

// 7    getNotes   online, defaultUserName, humanReadableName, empty List<Note>

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithNonNullViewDisplayLoadingNotesErrorCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(new Result.Success<>(new ArrayList<>())));

        presenter.onAttach(view);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(1)).clearSearchResults();
        Mockito.verify(view, Mockito.times(1)).showProgress(true);
        Mockito.verify(view, Mockito.times(1)).showProgress(false);
        Mockito.verify(view, Mockito.times(1)).displayLoadingNotesError();
    }

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithNonNullViewDisplayLoadingNotesErrorNotCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(new Result.Success<>(new ArrayList<>())));

        presenter.onAttach(null);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).displayLoadingNotesError();
    }

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithViewDetachedDisplayLoadingNotesErrorNotCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(new Result.Success<>(new ArrayList<>())));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).displayLoadingNotesError();
    }

// 8    getNotes   online, defaultUserName, humanReadableName, error List<Note>

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameResultErrorWithNonNullViewDisplayLoadingNotesErrorCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(resultGetNotesError));

        presenter.onAttach(view);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(1)).clearSearchResults();
        Mockito.verify(view, Mockito.times(1)).showProgress(true);
        Mockito.verify(view, Mockito.times(1)).showProgress(false);
        Mockito.verify(view, Mockito.times(1)).displayLoadingNotesError();
    }

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameResultErrorWithNonNullViewDisplayLoadingNotesErrorNotCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(resultGetNotesError));

        presenter.onAttach(null);
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).displayLoadingNotesError();
    }

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameResultErrorWithViewDetachedDisplayLoadingNotesErrorNotCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(resultGetNotesError));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).displayLoadingNotesError();
    }

// 9    searchNotes   online, text, search notes category, defaultUserName

    @Test
    public void searchNotesOnlineTextSearchNotesCategoryDefaultUserNameWithNonNullViewDisplayNotesCalled() {
        presenter.onAttach(view);
        presenter.searchNotes(searchByNoteRequest, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(1)).clearSearchResults();
        for (Note note : resultSearchByTextSuccess.getData()) {
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

    @Test
    public void searchNotesOnlineTextSearchNotesCategoryDefaultUserNameWithNullViewDisplayNotesNotCalled() {
        presenter.onAttach(null);
        presenter.searchNotes(searchByNoteRequest, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        for (Note note : resultSearchByTextSuccess.getData()) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

    @Test
    public void searchNotesOnlineTextSearchNotesCategoryDefaultUserNameWithViewDetachedDisplayNotesNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.searchNotes(searchByNoteRequest, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        for (Note note : resultSearchByTextSuccess.getData()) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

// 10    searchNotes  not online, text, search notes category, defaultUserName

    @Test
    public void searchNotesNotOnlineTextSearchNotesCategoryDefaultUserNameWithNonNullViewDisplayNoInternetErrorCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(view);
        presenter.searchNotes(searchByNoteRequest, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(1)).displayNoInternetError();
    }

    @Test
    public void searchNotesNotOnlineTextSearchNotesCategoryDefaultUserNameWithNullViewDisplayNoInternetErrorNotCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(null);
        presenter.searchNotes(searchByNoteRequest, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).displayNoInternetError();
    }

    @Test
    public void searchNotesNotOnlineTextSearchNotesCategoryDefaultUserNameWithViewDetachedDisplayNoInternetErrorNotCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.searchNotes(searchByNoteRequest, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).displayNoInternetError();
    }

// 11    searchNotes  online, null text, search notes category, defaultUserName

    @Test
    public void searchNotesOnlineNullTextSearchNotesCategoryDefaultUserNameWithNonNullViewGetNotesCalled() {
        presenter.onAttach(view);
        presenter.searchNotes(null, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(2)).clearSearchResults();
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

    @Test
    public void searchNotesOnlineNullTextSearchNotesCategoryDefaultUserNameWithNullViewGetNotesNotCalled() {
        presenter.onAttach(null);
        presenter.searchNotes(null, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

    @Test
    public void searchNotesOnlineNullTextSearchNotesCategoryDefaultUserNameWithViewDetachedGetNotesNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.searchNotes(null, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

// 12    searchNotes  online, empty text, search notes category, defaultUserName

    @Test
    public void searchNotesOnlineEmptyTextSearchNotesCategoryDefaultUserNameWithNonNullViewGetNotesCalled() {
        presenter.onAttach(view);
        presenter.searchNotes(searchEmptyRequest, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(2)).clearSearchResults();
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

    @Test
    public void searchNotesOnlineEmptyTextSearchNotesCategoryDefaultUserNameWithNullViewGetNotesNotCalled() {
        presenter.onAttach(null);
        presenter.searchNotes(searchEmptyRequest, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

    @Test
    public void searchNotesOnlineEmptyTextSearchNotesCategoryDefaultUserNameWithViewDetachedGetNotesNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.searchNotes(searchEmptyRequest, noteSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        for (Note note : testNotes) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

// 13   searchNotes  online, text, search user category, defaultUserName

    @Test
    public void searchNotesOnlineTextSearchUserCategoryDefaultUserNameWithNonNullViewDisplayNoteCalled() {
        presenter.onAttach(view);
        presenter.searchNotes(searchByUserRequest, userSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(1)).clearSearchResults();
        for (Note note : resultSearchByUserSuccess.getData()) {
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

    @Test
    public void searchNotesOnlineTextSearchUserCategoryDefaultUserNameWithNullViewDisplayNoteNotCalled() {
        presenter.onAttach(null);
        presenter.searchNotes(searchByUserRequest, userSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        for (Note note : resultSearchByUserSuccess.getData()) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

    @Test
    public void searchNotesOnlineTextSearchUserCategoryDefaultUserNameWithViewDetachedDisplayNoteNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.searchNotes(searchByUserRequest, userSearchCategory, defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        for (Note note : resultSearchByUserSuccess.getData()) {
            Mockito.verify(view, Mockito.times(0)).displayNote(note);
        }
    }

// 14   searchNotes  online, text, unknown category, defaultUserName

    @Test
    public void searchNotesOnlineTextUnknownCategorySearchUserDefaultUserNameWithNonNullViewThrowException() {
        presenter.onAttach(view);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Incorrect ID of category");

        presenter.searchNotes(searchByUserRequest, unknownSearchCategory, defaultUserName);
    }

    @Test
    public void searchNotesOnlineTextUnknownCategorySearchUserDefaultUserNameWithNullViewNotThrowException() {
        presenter.onAttach(null);
        presenter.searchNotes(searchByUserRequest, unknownSearchCategory, defaultUserName);

    }

    @Test
    public void searchNotesOnlineTextUnknownCategorySearchUserDefaultUserNameWithViewDetachedNotThrowException() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.searchNotes(searchByUserRequest, unknownSearchCategory, defaultUserName);
    }

// 15    searchNotes  online, text, search notes category, null defaultUserName

    @Test
    public void searchNotesOnlineTextSearchNotesCategoryNullDefaultUserNameWithNonNullViewDisplayDefaultUserNameErrorCalled() {
        presenter.onAttach(view);
        presenter.searchNotes(searchByUserRequest, noteSearchCategory, null);

        Mockito.verify(view, Mockito.times(1)).displayDefaultUserNameError();
    }

    @Test
    public void searchNotesOnlineTextSearchNotesCategoryNullDefaultUserNameWithNullViewDisplayDefaultUserNameErrorCalled() {
        presenter.onAttach(null);
        presenter.searchNotes(searchByUserRequest, noteSearchCategory, null);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

    @Test
    public void searchNotesOnlineTextSearchNotesCategoryNullDefaultUserNameWithViewDetachedDisplayDefaultUserNameErrorCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.searchNotes(searchByUserRequest, noteSearchCategory, null);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

// 16    searchNotes  online, text, search notes category, empty defaultUserName

    @Test
    public void searchNotesOnlineTextSearchNotesCategoryEmptyDefaultUserNameWithNonNullViewDisplayDefaultUserNameErrorCalled() {
        presenter.onAttach(view);
        presenter.searchNotes(searchByUserRequest, noteSearchCategory, emptyUserName);

        Mockito.verify(view, Mockito.times(1)).displayDefaultUserNameError();
    }

    @Test
    public void searchNotesOnlineTextSearchNotesCategoryEmptyDefaultUserNameWithNullViewDisplayDefaultUserNameErrorNotCalled() {
        presenter.onAttach(null);
        presenter.searchNotes(searchByUserRequest, noteSearchCategory, emptyUserName);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

    @Test
    public void searchNotesOnlineTextSearchNotesCategoryEmptyDefaultUserNameWithViewDetachedDisplayDefaultUserNameErrorNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.searchNotes(searchByUserRequest, noteSearchCategory, emptyUserName);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

// 17    onPositive  online, note, remove note success

    @Test
    public void onPositiveOnlineNoteRemoveNoteSuccessWithNonNullViewRefreshFragmentCalled() {

        presenter.onAttach(view);
        presenter.onPositive(testNote);

        Mockito.verify(view, Mockito.times(1)).showProgress(true);
        Mockito.verify(view, Mockito.times(1)).showProgress(false);
        Mockito.verify(view, Mockito.times(1)).refreshFragment();
    }

    @Test
    public void onPositiveOnlineNoteRemoveNoteSuccessWithNullViewRefreshFragmentNotCalled() {

        presenter.onAttach(null);
        presenter.onPositive(testNote);

        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).refreshFragment();
    }

    @Test
    public void onPositiveOnlineNoteRemoveNoteSuccessWithViewDetachedRefreshFragmentNotCalled() {

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.onPositive(testNote);

        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).refreshFragment();
    }

// 18    onPositive  not online, note, remove note success

    @Test
    public void onPositiveNotOnlineNoteRemoveNoteSuccessWithNonNullViewDisplayNoInternetErrorCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(view);
        presenter.onPositive(testNote);

        Mockito.verify(view, Mockito.times(1)).displayNoInternetError();
    }

    @Test
    public void onPositiveNotOnlineNoteRemoveNoteSuccessWithNullViewDisplayNoInternetErrorNotCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(null);
        presenter.onPositive(testNote);

        Mockito.verify(view, Mockito.times(0)).displayNoInternetError();
    }

    @Test
    public void onPositiveNotOnlineNoteRemoveNoteSuccessWithViewDetachedDisplayNoInternetErrorNotCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.onPositive(testNote);

        Mockito.verify(view, Mockito.times(0)).displayNoInternetError();
    }

// 19    onPositive  online, null note, remove note success

    @Test
    public void onPositiveOnlineNullNoteRemoveNoteSuccessWithNonNullViewDisplayNoteDataErrorCalled() {
        presenter.onAttach(view);
        presenter.onPositive(null);

        Mockito.verify(view, Mockito.times(1)).displayNoteDataError();
    }

    @Test
    public void onPositiveOnlineNullNoteRemoveNoteSuccessWithNullViewDisplayNoteDataErrorNotCalled() {
        presenter.onAttach(null);
        presenter.onPositive(null);

        Mockito.verify(view, Mockito.times(0)).displayNoteDataError();
    }

    @Test
    public void onPositiveOnlineNullNoteRemoveNoteSuccessWithViewDetachedDisplayNoteDataErrorNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.onPositive(null);

        Mockito.verify(view, Mockito.times(0)).displayNoteDataError();
    }

// 20   onPositive  online, note, remove note error

    @Test
    public void onPositiveOnlineNoteRemoveNoteErrorWithNonNullViewDisplayRemoveNoteErrorCalled() {
        Mockito.when(notesRepository.removeNote(testNote))
                .thenReturn(Single.just(resultRemoveNoteError));

        presenter.onAttach(view);
        presenter.onPositive(testNote);

        Mockito.verify(view, Mockito.times(1)).showProgress(true);
        Mockito.verify(view, Mockito.times(1)).showProgress(false);
        Mockito.verify(view, Mockito.times(1)).displayRemoveNoteError();
        Mockito.verify(view, Mockito.times(1)).refreshAdapter();
    }

    @Test
    public void onPositiveOnlineNoteRemoveNoteErrorWithNullViewDisplayRemoveNoteErrorNotCalled() {
        Mockito.when(notesRepository.removeNote(testNote))
                .thenReturn(Single.just(resultRemoveNoteError));

        presenter.onAttach(null);
        presenter.onPositive(testNote);

        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).displayRemoveNoteError();
        Mockito.verify(view, Mockito.times(0)).refreshAdapter();
    }

    @Test
    public void onPositiveOnlineNoteRemoveNoteErrorWithViewDetachedDisplayRemoveNoteErrorNotCalled() {
        Mockito.when(notesRepository.removeNote(testNote))
                .thenReturn(Single.just(resultRemoveNoteError));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.onPositive(testNote);

        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).displayRemoveNoteError();
        Mockito.verify(view, Mockito.times(0)).refreshAdapter();
    }

// 21   onNegative

    @Test
    public void onNegativeWithNonNullViewRefreshAdapterCalled() {
        presenter.onAttach(view);
        presenter.onNegative();

        Mockito.verify(view, Mockito.times(1)).refreshAdapter();
    }

    @Test
    public void onNegativeWithNullViewRefreshAdapterNotCalled() {
        presenter.onAttach(null);
        presenter.onNegative();

        Mockito.verify(view, Mockito.times(0)).refreshAdapter();
    }

    @Test
    public void onNegativeWithViewDetachedRefreshAdapterNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.onNegative();

        Mockito.verify(view, Mockito.times(0)).refreshAdapter();
    }

    @After
    public void tearDown() throws Exception {

    }
}
