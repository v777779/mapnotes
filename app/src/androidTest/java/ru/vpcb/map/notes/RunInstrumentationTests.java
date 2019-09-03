package ru.vpcb.map.notes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ru.vpcb.map.notes.activity.home.HomeActivityTest;
import ru.vpcb.map.notes.activity.login.LoginActivityTest;
import ru.vpcb.map.notes.activity.login.signin.SignInActivityTest;
import ru.vpcb.map.notes.activity.login.signup.SignUpActivityTest;
import ru.vpcb.map.notes.activity.splash.SplashActivityTest;
import ru.vpcb.map.notes.fragments.add.AddNoteFragmentTest;
import ru.vpcb.map.notes.fragments.nopermissions.NoLocationPermissionFragmentTest;
import ru.vpcb.map.notes.fragments.search.SearchNotesFragmentTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({SplashActivityTest.class,
        HomeActivityTest.class, LoginActivityTest.class,
        SignInActivityTest.class, SignUpActivityTest.class, AddNoteFragmentTest.class,
        NoLocationPermissionFragmentTest.class,
        SearchNotesFragmentTest.class,
      })
public class RunInstrumentationTests {

}
