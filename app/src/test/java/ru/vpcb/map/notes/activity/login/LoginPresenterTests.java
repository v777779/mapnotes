package ru.vpcb.map.notes.activity.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.vpcb.map.notes.MainApp;

@RunWith(RobolectricTestRunner.class)   // setup Robolectric  build.gradle, gradle.properties
@Config(
        sdk = 28,
        application = MainApp.class
)
public class LoginPresenterTests {

    @Mock
    private LoginView view;
    private LoginMvpPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(LoginView.class);
        presenter = LoginPresenter.getInstance();
    }

// 0    openSignIn

    @Test
    public void openSignInWithNonNullViewNavigateToSignInCalled() {
        Mockito.doAnswer(invocation -> null).when(view).navigateToSignIn();

        presenter.onAttach(view);
        presenter.openSignIn();

        Mockito.verify(view,Mockito.times(1)).navigateToSignIn();
    }

    @Test
    public void openSignInWithNullViewNavigateToSignInNotCalled() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(view).navigateToSignIn();

        presenter.onAttach(null);
        presenter.openSignIn();

        Mockito.verify(view,Mockito.times(0)).navigateToSignIn();

    }

    @Test
    public void openSignInWithViewDetachedNavigateToSignInNotCalled() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(view).navigateToSignIn();

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.openSignIn();

        Mockito.verify(view,Mockito.times(0)).navigateToSignIn();
    }

// 1    openSignUp
    @Test
    public void openSignUpWithNonNullViewNavigateToSignUpCalled() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(view).navigateToSignUp();

        presenter.onAttach(view);
        presenter.openSignUp();

        Mockito.verify(view,Mockito.times(1)).navigateToSignUp();
    }

    @Test
    public void openSignUpWithNullViewNavigateToSignUpNotCalled() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(view).navigateToSignUp();

        presenter.onAttach(null);
        presenter.openSignIn();

        Mockito.verify(view,Mockito.times(0)).navigateToSignUp();

    }

    @Test
    public void openSignUpWithViewDetachedNavigateToSignUpNotCalled() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(view).navigateToSignUp();

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.openSignUp();

        Mockito.verify(view,Mockito.times(0)).navigateToSignUp();

    }

}

