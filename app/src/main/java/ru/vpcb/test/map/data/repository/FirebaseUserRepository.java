package ru.vpcb.test.map.data.repository;

import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.model.AuthUser;

public class FirebaseUserRepository implements UserRepository{
    @Override
    public Result<AuthUser> signIn(String email, String password) {
        return null;
    }

    @Override
    public Result<AuthUser> signUp(String email, String password) {
        return null;
    }

    @Override
    public void signOut() {

    }

    @Override
    public Result<AuthUser> getCurrentUser() {
        return null;
    }

    @Override
    public void changeUserName(AuthUser user, String name) {

    }

    @Override
    public Result<String> getHumanReadableName(String userId) {
        return null;
    }

    @Override
    public Result<String> getUserIdFromHumanReadableName(String userName) {
        return null;
    }
}
