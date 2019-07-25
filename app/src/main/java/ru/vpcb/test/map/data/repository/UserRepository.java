package ru.vpcb.test.map.data.repository;

import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.model.AuthUser;

public interface UserRepository {

    Result<AuthUser> signIn(String email,String password);

    Result<AuthUser> signUp(String email, String password);

    void signOut();

    Result<AuthUser> getCurrentUser();

    void changeUserName(AuthUser user,String name);

    Result<String> getHumanReadableName(String userId);

    Result<String>  getUserIdFromHumanReadableName(String userName);

    void setAppExecutors(AppExecutors appExecutors);
}
