package ru.vpcb.test.map.data.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.Sync;
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
        final Sync<AuthUser> sync = new Sync<>();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> authResultTask) {
                if (authResultTask.isSuccessful() && authResultTask.getResult() != null) {
                    String uid = authResultTask.getResult().getUser().getUid();
                    appExecutors.resume(new Result.Success<>(new AuthUser(uid)));
                    Result<AuthUser> result = new Result.Success<>(new AuthUser(uid));
                    sync.setResult(result);
                } else {
                    appExecutors.resume(new Result.Error<>(new UserNotAuthenticatedException()));
                    Result<AuthUser> result = new Result.Error<>(new UserNotAuthenticatedException());
                    sync.setResult(result);
                }
                sync.unlock();
            }
        });

        sync.waiting();
        return sync.getResult();
    }


    @Override
    public Result<AuthUser> signUp(String email, String password) {
        final Sync<AuthUser> sync = new Sync<>();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> authResultTask) {
                if (authResultTask.isSuccessful() && authResultTask.getResult() != null) {
                    String uid = authResultTask.getResult().getUser().getUid();
                    appExecutors.resume(new Result.Success<>(new AuthUser(uid)));
                    Result<AuthUser> result = new Result.Success<>(new AuthUser(uid));
                    sync.setResult(result);

                } else {
                    appExecutors.resume(new Result.Error<>(new UserNotAuthenticatedException()));
                    Result<AuthUser> result = new Result.Error<>(new UserNotAuthenticatedException());
                    sync.setResult(result);
                }
                sync.unlock();
            }
        });
        sync.waiting();
        return sync.getResult();
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
        final Sync<String> sync = new Sync<>();
        database.getReference(usersPath).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot snapshot = dataSnapshot.getChildren().iterator().next();
                    String s = "";
                    if (!(snapshot == null || snapshot.getValue() == null)) {
                        s = snapshot.getValue().toString();
                    }

                    appExecutors.resume(new Result.Success<>(s));
                    Result<String> result = new Result.Success<>(s);
                    sync.setResult(result);
                }
                sync.unlock();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Result<String> result = new Result.Error<>(databaseError.toException());
                sync.setResult(result);
                sync.unlock();
            }
        });
        sync.waiting();
        return sync.getResult();
    }

    @Override
    public Result<String> getUserIdFromHumanReadableName(String userName) {
        final Sync<String> sync = new Sync<>();
        database.getReference(usersPath)
                .orderByChild(nameKey)
                .equalTo(userName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            DataSnapshot snapshot = dataSnapshot.getChildren().iterator().next();
                            String s = "";
                            if (!(snapshot == null || snapshot.getKey() == null)) {
                                s = snapshot.getKey();
                            }
                            appExecutors.resume(new Result.Success<>(s));
                            Result<String> result = new Result.Success<>(s);
                            sync.setResult(result);
                        }
                        sync.unlock();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Result<String> result = new Result.Error<>(databaseError.toException());
                        sync.setResult(result);
                        sync.unlock();
                    }
                });
        sync.waiting();
        return sync.getResult();
    }
}
