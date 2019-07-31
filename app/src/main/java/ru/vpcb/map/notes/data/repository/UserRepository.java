package ru.vpcb.map.notes.data.repository;


import io.reactivex.Single;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.model.AuthUser;

public interface UserRepository {

    Single<Result<AuthUser>> signIn(String email, String password);

    Single<Result<AuthUser>> signUp(String email, String password);

    void signOut();

    Result<AuthUser> getCurrentUser();

    void changeUserName(AuthUser user,String name);

    Result<String> getHumanReadableName(String userId);

    Result<String>  getUserIdFromHumanReadableName(String userName);

    void setAppExecutors(AppExecutors appExecutors);
}
