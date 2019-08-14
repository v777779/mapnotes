package ru.vpcb.map.notes.login.signin;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.vpcb.map.notes.MainApp;

@RunWith(RobolectricTestRunner.class)   // setup Robolectric  build.gradle, gradle.properties
@Config(
        sdk = 28,
        application = MainApp.class
)
public class SignInPresenterTests {

    @Before
    public void setUp() throws Exception {

    }
}
