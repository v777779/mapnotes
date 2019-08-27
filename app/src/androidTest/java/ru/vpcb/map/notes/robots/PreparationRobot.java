package ru.vpcb.map.notes.robots;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.model.AuthUser;
import ru.vpcb.map.notes.model.Note;

public class PreparationRobot {
    private MockTest scope;
    private AuthUser authUser;

    public static PreparationRobot prepare(MockTest scope) {
        return new PreparationRobot(scope);
    }

    private PreparationRobot(MockTest scope) {
        this.scope = scope;
        this.authUser = new AuthUser("111111");
    }

    public PreparationRobot mockLocationProvider(boolean isLocationAvailable) {
        LocationProvider locationProvider = scope.getLocationProvider();
        Mockito.doAnswer(invocation -> null).when(locationProvider).startLocationUpdates();
        Mockito.doAnswer(invocation -> null).when(locationProvider).stopLocationUpdates();
        Mockito.doAnswer(invocation -> null).when(locationProvider)
                .addUpdatableLocationListener(Mockito.any());

        Mockito.when(locationProvider.isLocationAvailable()).thenReturn(isLocationAvailable);

        return this;
    }

    public PreparationRobot mockLocationProvider() {
        mockLocationProvider(false);
        return this;
    }

    public void mockSignUpError() {
        UserRepository userRepository = scope.getUserRepository();

        Mockito.when(userRepository.signUp(Mockito.any(), Mockito.anyString()))
                .thenReturn(Single.just(new Result.Error<>(new RuntimeException("SignUp error"))));
    }

    public PreparationRobot mockSignUpSuccess(String name, String email, String password) {
        UserRepository userRepository = scope.getUserRepository();
        Mockito.doAnswer(invocation -> null).when(userRepository).changeUserName(authUser, name);
        Mockito.when(userRepository.signUp(email, password))
                .thenReturn(Single.just(new Result.Success<>(authUser)));
        return this;
    }

    public void mockNoAuthorizedUser() {
        UserRepository userRepository = scope.getUserRepository();
        Mockito.when(userRepository.getCurrentUser())
                .thenReturn(new Result.Error<>(new RuntimeException()));
    }

    public void mockAuthorizedUser() {
        UserRepository userRepository = scope.getUserRepository();
        Mockito.when(userRepository.getCurrentUser())
                .thenReturn(new Result.Success<>(authUser));
    }


    public void mockUserSignOut() {
        UserRepository userRepository = scope.getUserRepository();
        Mockito.doAnswer(invocation -> null).when(userRepository).signOut();
    }


    public void mockSignInSuccess( String email, String password) {
        UserRepository userRepository = scope.getUserRepository();

        Mockito.when(userRepository.signIn(email, password))
                .thenReturn(Single.just(new Result.Success<>(authUser)));
        Mockito.when(userRepository.getCurrentUser())
                .thenReturn(new Result.Success<>(authUser));
    }

    public void mockSignInErrorWithException() {
        UserRepository userRepository = scope.getUserRepository();

        Mockito.when(userRepository.signIn(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Single.just(new Result.Error<>(new RuntimeException("Signin error"))));
    }


    public void mockSearchUserId( String userId) {
        UserRepository userRepository = scope.getUserRepository();

        Mockito.when(userRepository.getUserIdFromHumanReadableName(Mockito.anyString()))
                .thenReturn(Single.just(new Result.Success<>(userId)));
    }

    public void mockErrorDuringLoadingUserNames() {
        UserRepository userRepository = scope.getUserRepository();

        Mockito.when(userRepository.getUserIdFromHumanReadableName(Mockito.anyString()))
                .thenReturn(Single.just(new Result.Error<>(new RuntimeException("Database Error"))));
    }

    public void mockLoadingListOfNotesEmpty() {
        NotesRepository notesRepository = scope.getNotesRepository();

        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(new Result.Success<>(new ArrayList<>())));
    }

    public void mockLoadingListOfNotesError() {
        NotesRepository notesRepository = scope.getNotesRepository();

        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(new Result.Error<>(new RuntimeException())));
    }

    public void mockLoadingListOfNotesByTextEmpty() {
        NotesRepository notesRepository = scope.getNotesRepository();

        Mockito.when(notesRepository.getNotesByNoteText(Mockito.anyString()))
                .thenReturn(Single.just(new Result.Error<>(new RuntimeException())));
    }

    public void mockLoadingListOfNotes(List<Note> list) {
        NotesRepository notesRepository = scope.getNotesRepository();

        Mockito.when(notesRepository.getNotes())
                .thenReturn(Single.just(new Result.Success<>(list)));
    }

    public void mockSearchByAnyText(List<Note> list) {
        NotesRepository notesRepository = scope.getNotesRepository();

        Mockito.when(notesRepository.getNotesByNoteText(Mockito.anyString()))
                .thenReturn(Single.just(new Result.Success<>(list)));
    }

    public void mockSearchByAnyUser(List<Note> list) {
        NotesRepository notesRepository = scope.getNotesRepository();

        Mockito.when(notesRepository.getNotesByUser(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Single.just(new Result.Success<>(list)));
    }


}
