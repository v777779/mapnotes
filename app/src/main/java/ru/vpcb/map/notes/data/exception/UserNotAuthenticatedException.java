package ru.vpcb.map.notes.data.exception;

public class UserNotAuthenticatedException extends Exception {
    public UserNotAuthenticatedException() {
        super("User not authenticated");
    }
}
