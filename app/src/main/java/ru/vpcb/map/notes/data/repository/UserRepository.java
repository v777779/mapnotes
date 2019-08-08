package ru.vpcb.map.notes.data.repository;


import io.reactivex.Single;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.model.AuthUser;

public interface UserRepository {

    Single<Result<AuthUser>> signIn(String email, String password);

    Single<Result<AuthUser>> signUp(String email, String password);

    void signOut();

    Result<AuthUser> getCurrentUser();

    Single<Result<Void>> changeUserName(AuthUser user,String name);

    Single<Result<String>> getHumanReadableName(String userId);

    Single<Result<String>> getUserIdFromHumanReadableName(String userName);

}
