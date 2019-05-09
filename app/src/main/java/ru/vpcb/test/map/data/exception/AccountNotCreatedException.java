package ru.vpcb.test.map.data.exception;

public class AccountNotCreatedException extends Exception {

    public AccountNotCreatedException() {
        super("Your account cannot be created");
    }
}
