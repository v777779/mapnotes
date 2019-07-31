package ru.vpcb.map.notes.data.exception;

public class AccountNotCreatedException extends Exception {

    public AccountNotCreatedException() {
        super("Your account cannot be created");
    }
}
