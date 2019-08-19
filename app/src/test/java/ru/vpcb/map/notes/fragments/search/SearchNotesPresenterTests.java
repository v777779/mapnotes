package ru.vpcb.map.notes.fragments.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
import ru.vpcb.map.notes.model.AuthUser;
import ru.vpcb.map.notes.model.Note;

@RunWith(RobolectricTestRunner.class)   // setup Robolectric  build.gradle, gradle.properties
@Config(
        sdk = 28,
        application = MainApp.class
)
public class SearchNotesPresenterTests {

    private Result.Success<List<Note>> resultSuccessTestNotes;
    private Result.Error<List<Note>> resultErrorTestNotes;
    private Result.Success<List<Note>> resultSearchByText;
    private Result.Success<List<Note>> resultSearchByUID;
    private Result.Success<AuthUser> authUser;
    private Result.Error<AuthUser> notAuthUser;
    private List<Note> testNotes;
    private List<String> testNames;
    private Note testNote;
    private String authUserUID;
    private String authUserUID2;
    private String authUserName;
    private String authUserName2;
    private String defaultUserName;
    private String emptyUserName;
    private String searchByNoteRequest;
    private String searchByUserUIDRequest;
    private int noteCategoryInSearch;
    private int userCategoryInSearch;
    private String searchEmptyRequest;


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

        authUser = new Result.Success<>(new AuthUser(authUserUID));
        notAuthUser = new Result.Error<>(new RuntimeException("Auth Error"));

        noteCategoryInSearch = 0;
        userCategoryInSearch = 1;
        searchByNoteRequest = "test note";
        searchByUserUIDRequest = "22222222";
        searchEmptyRequest = "";
        testNotes = new ArrayList<>(Arrays.asList(
                new Note(0, 0, "test note 1_1", authUserUID),
                new Note(0, 0, "test note 2_1", authUserUID),
                new Note(0, 0, "test note 1_2", authUserUID2),
                new Note(0, 0, "test type 2_2", authUserUID2),
                new Note(0, 0, "test type 3_2", authUserUID2)));

        testNote = new Note(0, 0, "test note", authUserUID);
        resultSuccessTestNotes = new Result.Success<>(testNotes);
        resultErrorTestNotes = new Result.Error<>(new RuntimeException("get notes error"));
        resultSearchByText = new Result.Success<>(testNotes.subList(0,3));
        resultSearchByUID = new Result.Success<>(testNotes.subList(2,5));

        presenter = new SearchNotesPresenter(appExecutors, userRepository, notesRepository);

        Mockito.when(appExecutors.ui()).then(
                (Answer<Scheduler>) invocation -> AndroidSchedulers.mainThread());
        Mockito.when(appExecutors.net()).then(
                (Answer<Scheduler>) invocation -> AndroidSchedulers.mainThread());

        Mockito.doAnswer(invocation -> null).when(view).displayNote(testNote);
        Mockito.doAnswer(invocation -> null).when(view).displayLoadingNotesError();
        Mockito.doAnswer(invocation -> null).when(view).displayUnknownUserError();
        Mockito.doAnswer(invocation -> null).when(view).displayUnknownNoteError();
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
                .thenReturn(Single.just(resultSuccessTestNotes));
        Mockito.when(notesRepository.getNotesByNoteText(searchByNoteRequest))
                .thenReturn(Single.just(resultSearchByText));
        Mockito.when(notesRepository.getNotesByUser(searchByUserUIDRequest,authUserName2))
                .thenReturn(Single.just(resultSearchByUID));

        Mockito.when(userRepository.getHumanReadableName(authUserUID)).thenReturn(
                Single.just(new Result.Success<>(authUserName)));
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Success<>(authUserName2)));


    }

// 0    getNotes   online, correct defaultUserName, correct humanReadableName, correct list<notes>

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
    public void getNotesOnlineDefaultUserNameWithNullViewDisplayNoteNotCalled() {
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
    public void getNotesOnlineDefaultUserNameWithViewDetachedDisplayNoteNotCalled() {
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

// 1    getNotes   not online, correct defaultUserName, correct humanReadableName, correct list<notes>

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

// 2    getNotes   online, null defaultUserName, error humanReadableName, correct list<notes>

    @Test
    public void getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Error<>(new NullPointerException("not found"))));

        presenter.onAttach(view);
        presenter.getNotes(null);

        Mockito.verify(view, Mockito.times(1)).displayDefaultUserNameError();
    }

    @Test
    public void getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithNullViewDisplayDefaultUserNameErrorNotCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Error<>(new NullPointerException("not found"))));

        presenter.onAttach(null);
        presenter.getNotes(null);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

    @Test
    public void getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithViewDetachedDisplayDefaultUserNameErrorNotCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Error<>(new NullPointerException("not found"))));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(null);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

// 3    getNotes   online, empty defaultUserName, error humanReadableName, correct list<notes>

    @Test
    public void getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Error<>(new NullPointerException("not found"))));

        presenter.onAttach(view);
        presenter.getNotes(emptyUserName);

        Mockito.verify(view, Mockito.times(1)).displayDefaultUserNameError();
    }

    @Test
    public void getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorNotCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Error<>(new NullPointerException("not found"))));

        presenter.onAttach(null);
        presenter.getNotes(emptyUserName);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

    @Test
    public void getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithViewDetachedDisplayDefaultUserNameErrorNotCalled() {
        Mockito.when(userRepository.getHumanReadableName(authUserUID2)).thenReturn(
                Single.just(new Result.Error<>(new NullPointerException("not found"))));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(emptyUserName);

        Mockito.verify(view, Mockito.times(0)).displayDefaultUserNameError();
    }

// 4    getNotes   online, defaultUserName, null humanReadableName, correct list<notes>

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


// 5    getNotes   online, defaultUserName, empty humanReadableName, correct list<notes>

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

// 6    getNotes   online, defaultUserName, humanReadableName, null list<notes>

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

// 7    getNotes   online, defaultUserName, humanReadableName, empty list<notes>

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

// 8    getNotes   online, defaultUserName, humanReadableName, error list<notes>

    @Test
    public void getNotesOnlineDefaultUserNameHumanReadableNameResultErrorWithNonNullViewDisplayLoadingNotesErrorCalled() {
        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(resultErrorTestNotes));

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
                .thenReturn(Single.just(resultErrorTestNotes));

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
                .thenReturn(Single.just(resultErrorTestNotes));

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getNotes(defaultUserName);

        Mockito.verify(view, Mockito.times(0)).clearSearchResults();
        Mockito.verify(view, Mockito.times(0)).showProgress(true);
        Mockito.verify(view, Mockito.times(0)).showProgress(false);
        Mockito.verify(view, Mockito.times(0)).displayLoadingNotesError();
    }

// 9    searchNotes   online, text, search notes category position, defaultUserName

    @Test
    public void searchNotesOnlineTextCategoryPositionSearchNotesDefaultUserNameWithNonNullViewDisplayNotesCalled() {

        presenter.onAttach(view);
        presenter.searchNotes(searchByNoteRequest,noteCategoryInSearch,defaultUserName);

        Mockito.verify(view, Mockito.times(1)).clearSearchResults();
        for (Note note : resultSearchByText.getData()){
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

// 10    searchNotes  not online, text, search notes category position, defaultUserName

    @Test
    public void searchNotesNotOnlineTextCategoryPositionSearchNotesDefaultUserNameWithNonNullViewDisplayNoInternetErrorCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(view);
        presenter.searchNotes(searchByNoteRequest,noteCategoryInSearch,defaultUserName);

        Mockito.verify(view, Mockito.times(1)).displayNoInternetError();
    }

    @Test
    public void searchNotesNotOnlineTextCategoryPositionSearchNotesDefaultUserNameWithNullViewDisplayNoInternetErrorNotCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(null);
        presenter.searchNotes(searchByNoteRequest,noteCategoryInSearch,defaultUserName);

        Mockito.verify(view, Mockito.times(0)).displayNoInternetError();
    }

    @Test
    public void searchNotesNotOnlineTextCategoryPositionSearchNotesDefaultUserNameWithViewDetachedDisplayNoInternetErrorNotCalled() {
        Mockito.when(view.isOnline()).thenReturn(false);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.searchNotes(searchByNoteRequest,noteCategoryInSearch,defaultUserName);

        Mockito.verify(view, Mockito.times(0)).displayNoInternetError();
    }

// 11    searchNotes  online, null text, search notes category position, defaultUserName

    @Test
    public void searchNotesOnlineNullTextCategoryPositionSearchNotesDefaultUserNameWithNonNullViewGetNotesCalled() {

        presenter.onAttach(view);
        presenter.searchNotes(null,noteCategoryInSearch,defaultUserName);

        Mockito.verify(view, Mockito.times(2)).clearSearchResults();
        for (Note note : testNotes){
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

// 12    searchNotes  online, empty text, search notes category position, defaultUserName

    @Test
    public void searchNotesOnlineEmptyTextCategoryPositionSearchNotesDefaultUserNameWithNonNullViewGetNotesCalled() {

        presenter.onAttach(view);
        presenter.searchNotes(searchEmptyRequest,noteCategoryInSearch,defaultUserName);

        Mockito.verify(view, Mockito.times(2)).clearSearchResults();
        for (Note note : testNotes){
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }

// 13   searchNotes  online, text, search user category position, defaultUserName

    @Test
    public void searchNotesOnlineEmptyTextCategoryPositionSearchUserDefaultUserNameWithNonNullViewGetNotesCalled() {

        presenter.onAttach(view);
        presenter.searchNotes(searchByUserUIDRequest,userCategoryInSearch,defaultUserName);

        Mockito.verify(view, Mockito.times(2)).clearSearchResults();
        for (Note note : resultSearchByUID.getData()){
            Mockito.verify(view, Mockito.times(1)).displayNote(note);
        }
    }



    @After
    public void tearDown() throws Exception {

    }
}
