package ru.vpcb.map.notes.fragment.add;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

@RunWith(RobolectricTestRunner.class)   // setup Robolectric  build.gradle, gradle.properties
@Config(
        sdk = 28,
        application = MainApp.class
)
public class AddNotePresenterTests {
    private String uid;
    private Location sydneyLocation;
    private String testNoteText;
    private Note sydneyTestNote;

    @Mock
    private AddNoteView view;
    @Mock
    private IAppExecutors appExecutors;
    @Mock
    private UserRepository userRepository;
    @Mock
    private NotesRepository notesRepository;
    @Mock
    private LocationProvider locationProvider;
    @Mock
    private LocationFormatter locationFormatter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        uid = "111111";
        sydneyLocation = new Location(-33.8688, 151.2093);
        testNoteText = "test note";
        sydneyTestNote = new Note(sydneyLocation.getLatitude(), sydneyLocation.getLongitude(),
                testNoteText, uid);
    }

    @Test
    public void name() {

        int k = 1;
    }
}
