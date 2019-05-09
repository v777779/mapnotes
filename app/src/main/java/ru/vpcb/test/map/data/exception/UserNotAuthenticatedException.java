package ru.vpcb.test.map.data.exception;

public class UserNotAuthenticatedException extends Exception {
    public UserNotAuthenticatedException() {
        super("User not authenticated");
    }
}
