package ru.vpcb.map.notes.robots;

import org.mockito.Mockito;

import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.model.AuthUser;

public class PreparationRobot {
    private  MockTest scope;
    private AuthUser authUser;

    public static PreparationRobot prepare(MockTest scope){
        return new PreparationRobot(scope);
    }

    private PreparationRobot(MockTest scope) {
        this.scope = scope;
        this.authUser = new AuthUser("111111");
    }

    public void mockLocationProvider(boolean isLocationAvailable) {
        LocationProvider locationProvider = scope.getLocationProvider();
        Mockito.doAnswer(invocation -> null).when(locationProvider).startLocationUpdates();
        Mockito.doAnswer(invocation -> null).when(locationProvider).stopLocationUpdates();
        Mockito.doAnswer(invocation -> null).when(locationProvider)
                .addUpdatableLocationListener(Mockito.any());

        Mockito.when(locationProvider.isLocationAvailable()).thenReturn(isLocationAvailable);
    }

    public void mockLocationProvider() {
        mockLocationProvider(false);
    }



    fun mockSignUpError() {
        val userRepository = scope.userRepository
        coEvery { userRepository.signUp(any(), any()) } returns Result.Error(Exception("SignUp error"))
    }

    fun mockSuccessfulSignUp(name: String, email: String, password: String) {
        val userRepository = scope.userRepository
        coEvery { userRepository.changeUserName(authUser, name) } answers { nothing }
        coEvery { userRepository.signUp(email, password) } returns Result.Success(authUser)
    }

    fun mockNoAuthorizedUser() {
        val userRepository = scope.userRepository
        coEvery { userRepository.getCurrentUser() } returns Result.Error(RuntimeException())
    }

    fun mockAuthorizedUser() {
        val userRepository = scope.userRepository
        coEvery { userRepository.getCurrentUser() } returns Result.Success(authUser)
    }

    fun mockUserSignOut() {
        val userRepository = scope.userRepository
        coEvery { userRepository.signOut() } answers { nothing }
    }

    fun mockSuccessfulSignIn(email: String, password: String) {
        val userRepository = scope.userRepository
        val authUser = AuthUser("111111")
        coEvery { userRepository.signIn(email, password) } returns Result.Success(authUser)
        coEvery { userRepository.getCurrentUser() } returns Result.Success(authUser)
    }

    fun mockUnsuccessfulSignInWithException() {
        val userRepository = scope.userRepository
        coEvery { userRepository.signIn(any(), any()) } returns Result.Error(Exception("SignIn error"))
    }

    fun mockSearchUserId(userId: String) {
        val userRepository = scope.userRepository
        coEvery { userRepository.getUserIdFromHumanReadableName(any()) } returns Result.Success(userId)
    }

    fun mockErrorDuringLoadingUserNames() {
        val userRepository = scope.userRepository
        coEvery { userRepository.getUserIdFromHumanReadableName(any()) } returns Result.Error(RuntimeException())
    }

    fun mockLoadingEmptyListOfNotes() {
        val notesRepository = scope.notesRepository
        coEvery { notesRepository.getNotes(any()) } returns Result.Success(listOf())
    }

    fun mockErrorDuringLoadingNotes() {
        val notesRepository = scope.notesRepository
        coEvery { notesRepository.getNotes(any()) } returns Result.Error(RuntimeException())
    }

    fun mockLoadingEmptyListOfNotesByNoteText() {
        val notesRepository = scope.notesRepository
        coEvery { notesRepository.getNotesByNoteText(any(), any()) } returns Result.Error(RuntimeException())
    }

    fun mockLoadingListOfNotes(notes: List<Note>) {
        val notesRepository = scope.notesRepository
        coEvery { notesRepository.getNotes(any()) } returns Result.Success(notes)
    }

    fun mockSearchNoteByAnyText(notes: List<Note>) {
        val notesRepository = scope.notesRepository
        coEvery { notesRepository.getNotesByNoteText(any(), any()) } returns Result.Success(notes)
    }

    fun mockSearchNoteByAnyUser(notes: List<Note>) {
        val notesRepository = scope.notesRepository
        coEvery { notesRepository.getNotesByUser(any(), any()) } returns Result.Success(notes)
    }

}
