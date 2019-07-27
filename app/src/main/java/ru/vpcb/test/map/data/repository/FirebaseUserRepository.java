package ru.vpcb.test.map.data.repository;


import androidx.annotation.NonNull;

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

import io.reactivex.Observable;
import ru.vpcb.test.map.Sync;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.exception.UserNotAuthenticatedException;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.executors.IAppExecutors;
import ru.vpcb.test.map.model.AuthUser;

public class FirebaseUserRepository implements UserRepository {

    private IAppExecutors appExecutors;
    private AppExecutors oldAppExecutors;

    private String usersPath;
    private String nameKey;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    public FirebaseUserRepository(IAppExecutors appExecutors) {  // ok
        this.appExecutors = appExecutors;
        this.usersPath = "users";
        this.nameKey = "name";
        this.auth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
    }

    @Override
    public Observable<Result<AuthUser>> signIn(String email, String password) {
        return Observable.<Result<AuthUser>>create(
                emitter -> auth
                        .signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(authResultTask -> {
                            if (emitter.isDisposed()) return;

                            if (authResultTask.isSuccessful() && authResultTask.getResult() != null) {
                                String uid = authResultTask.getResult().getUser().getUid();

                                emitter.onNext(new Result.Success<>(new AuthUser(uid)));
                            } else {
                                emitter.onNext(new Result.Error<>(new UserNotAuthenticatedException()));
                             //                                emitter.onError(new UserNotAuthenticatedException());
                           }
                        })).subscribeOn(appExecutors.net())
                .observeOn(appExecutors.ui());

    }


    @Override
    public Result<AuthUser> signUp(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> authResultTask) {
                if (authResultTask.isSuccessful() && authResultTask.getResult() != null) {
                    String uid = authResultTask.getResult().getUser().getUid();
                    oldAppExecutors.resume(new Result.Success<>(new AuthUser(uid)));
                } else {
                    oldAppExecutors.resume(new Result.Error<>(new UserNotAuthenticatedException()));
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

        database.getReference(usersPath).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot snapshot = dataSnapshot.getChildren().iterator().next();
                    String s = "";
                    if (!(snapshot == null || snapshot.getValue() == null)) {
                        s = snapshot.getValue().toString();
                    }
                    Result<String> result = new Result.Success<>(s);
                    oldAppExecutors.resume(result);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Result<String> result = new Result.Error<>(databaseError.toException());
                oldAppExecutors.resume(result);
            }
        });

        return null;
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
                            oldAppExecutors.resume(new Result.Success<>(s));
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


    // TODO inject
    @Override
    public void setAppExecutors(AppExecutors appExecutors) {
        this.oldAppExecutors = appExecutors;
    }


// Alternative

//    @Override
//    public void changeUserName(AuthUser user, String name) {
//        DatabaseReference usersRef = database.getReference(usersPath);
//        usersRef.child(user.getUid()).child(nameKey).setValue(name, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                if (databaseError == null)
//                    oldAppExecutors.resume(new Result.Success<>(null));
//                else
//                    oldAppExecutors.resume(new Result.Error<>(databaseError.toException()));
//            }
//        });
//
//    }
}
