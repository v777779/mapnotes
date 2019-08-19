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


    private Result.Success<AuthUser> authUser;
    private Result.Error<AuthUser> notAuthUser;
    private String authUserUID;

    private int noteCategoryInSearch;
    private int userCategoryInSearch;
    private String defaultUserName;
    private String searchByNoteRequest;
    private String searchByUserUIDRequest;
    private List<Note> testNotes;
    private Note testNote;
    private Result.Success<List<Note>> resultSuccessTestNotes;
    private Result.Error<List<Note>> resultErrorTestNotes;


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

        authUserUID = "111111";
        authUser = new Result.Success<>(new AuthUser(authUserUID));
        notAuthUser = new Result.Error<>(new RuntimeException("Auth Error"));

        noteCategoryInSearch = 0;
        userCategoryInSearch = 1;
        defaultUserName = "Unknown";
        searchByNoteRequest = "test note";
        searchByUserUIDRequest = "22222222";
        testNotes = new ArrayList<>(Arrays.asList(
                new Note(0, 0, "test note 1_1", "11111111"),
                new Note(0, 0, "test note 2_1", "11111111"),
                new Note(0, 0, "test note 1_2", "22222222"),
                new Note(0, 0, "test note 2_2", "22222222"),
                new Note(0, 0, "test note 3_2", "22222222")));
        testNote = new Note(0,0, "test note", "11111111");

        resultSuccessTestNotes = new Result.Success<>(testNotes);
        resultErrorTestNotes = new Result.Error<>(new RuntimeException("get notes error"));

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
        Mockito.doAnswer(invocation -> null).when(view).clearSearchResults();
        Mockito.doAnswer(invocation -> null).when(view).showProgress(Mockito.anyBoolean());
        Mockito.doAnswer(invocation -> null).when(view).refreshAdapter();
        Mockito.doAnswer(invocation -> null).when(view).refreshFragment();

        Mockito.when(view.isOnline()).thenReturn(true);
        Mockito.when(view.defaultUserName()).thenReturn(defaultUserName);

        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(resultSuccessTestNotes));

    }

    @Test
    public void getNotesOnlineNullDefaultUserNameWithNonNullView() {

    }

    @After
    public void tearDown() throws Exception {

    }
}
