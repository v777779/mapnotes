package ru.vpcb.map.notes.activity.home;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.activity.splash.SplashMvpPresenter;
import ru.vpcb.map.notes.activity.splash.SplashView;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.AuthUser;

@RunWith(RobolectricTestRunner.class)
@Config(
        sdk = 28,
        application = MainApp.class
)
public class HomePresenterTests {

    private Result<AuthUser> authUser;
    private Result.Error<AuthUser> notAuthUser;
    private int playServiceAvailableCode;
    private int playServiceNotAvailableCode;
    private int gpsRequestCode;
    private int nonGPSRequestCode;

    @Mock
    private SplashView view;
    @Mock
    private IAppExecutors appExecutors;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SplashMvpPresenter presenter;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void home() {

    }

    @After
    public void tearDown() throws Exception {

    }
}
