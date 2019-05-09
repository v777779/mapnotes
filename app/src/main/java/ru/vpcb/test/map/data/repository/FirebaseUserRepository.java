package ru.vpcb.test.map.data.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.vpcb.test.map.AppExecutors;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.exception.UserNotAuthenticatedException;
import ru.vpcb.test.map.model.AuthUser;

public class FirebaseUserRepository implements UserRepository {

    private AppExecutors appExecutors;
    private String usersPath;
    private String nameKey;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    // new


    public FirebaseUserRepository(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        this.usersPath = "users";
        this.nameKey = "name";
        this.auth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
    }

    @Override
    public Result<AuthUser> signIn(String email, String password) {
// TODO suspend call
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> authResultTask) {
                if (authResultTask.isSuccessful() && authResultTask.getResult() != null) {
                    String uid = authResultTask.getResult().getUser().getUid();
                    appExecutors.resume(new Result.Success<>(new AuthUser(uid)));

                } else {
                    appExecutors.resume(new Result.Error<>(new UserNotAuthenticatedException()));
                }
            }
        });
        return null;
    }


    @Override
    public Result<AuthUser> signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> authResultTask) {
                if (authResultTask.isSuccessful() && authResultTask.getResult() != null) {
                    String uid = authResultTask.getResult().getUser().getUid();
                    appExecutors.resume(new Result.Success<>(new AuthUser(uid)));

                } else {
                    appExecutors.resume(new Result.Error<>(new UserNotAuthenticatedException()));
                }
            }
        });
        return null;
    }

    @Override
    public void signOut() {
        auth.signOut();
    }

    @Override
    public Result<AuthUser> getCurrentUser() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            return new Result.Success<>(new AuthUser(user.getUid()));
        } else {
            return new Result.Error<>(new UserNotAuthenticatedException());
        }
    }

    @Override
    public void changeUserName(AuthUser user, String name) {
        DatabaseReference usersRef = database.getReference(usersPath);
        usersRef.child(user.getUid()).child(nameKey).setValue(name);

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
