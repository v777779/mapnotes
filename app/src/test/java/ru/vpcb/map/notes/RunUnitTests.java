package ru.vpcb.map.notes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ru.vpcb.map.notes.activity.home.HomePresenterTests;
import ru.vpcb.map.notes.activity.login.LoginPresenterTests;
import ru.vpcb.map.notes.activity.login.signin.SignInPresenterTests;
import ru.vpcb.map.notes.activity.login.signup.SignUpPresenterTests;
import ru.vpcb.map.notes.activity.splash.SplashPresenterTests;
import ru.vpcb.map.notes.data.formatter.CoordinateFormatterTests;
import ru.vpcb.map.notes.ext.ValidationExtTests;
import ru.vpcb.map.notes.fragments.add.AddNotePresenterTests;
import ru.vpcb.map.notes.fragments.map.GoogleMapPresenterTests;
import ru.vpcb.map.notes.fragments.search.SearchNotesPresenterTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({SplashPresenterTests.class, HomePresenterTests.class, LoginPresenterTests.class,
        SignInPresenterTests.class, SignUpPresenterTests.class, AddNotePresenterTests.class,
        CoordinateFormatterTests.class, SearchNotesPresenterTests.class,
        GoogleMapPresenterTests.class, ValidationExtTests.class})
public class RunUnitTests {

}
