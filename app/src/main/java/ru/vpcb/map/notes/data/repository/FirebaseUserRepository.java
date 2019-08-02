package ru.vpcb.map.notes.data.repository;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import ru.vpcb.map.notes.Sync;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.exception.UserNotAuthenticatedException;
import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.AuthUser;

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
    public Single<Result<AuthUser>> signIn(String email, String password) {
        return Single.<Result<AuthUser>>create(                                                     // single for auto dispose
                emitter -> {
                    auth.signInWithEmailAndPassword(email, password)                                // net pool thread works
                            .addOnCompleteListener(authResultTask -> {
                                if (emitter.isDisposed())                                           // main thread by Firebase
                                    return;
                                if (authResultTask.isSuccessful() && authResultTask.getResult() != null) {
                                    String uid = authResultTask.getResult().getUser().getUid();

                                    emitter.onSuccess(new Result.Success<>(new AuthUser(uid)));
                                } else {
                                    emitter.onSuccess(new Result.Error<>(new UserNotAuthenticatedException()));
//                                    emitter.onError(new UserNotAuthenticatedException());
                                }
                            });
                }).subscribeOn(appExecutors.net())
                .observeOn(appExecutors.ui());
    }


    @Override
    public Single<Result<AuthUser>> signUp(String email, String password) {
        return Single.<Result<AuthUser>>create(emitter -> {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(authResultTask -> {
                        if (emitter.isDisposed()) return;
                        if (authResultTask.isSuccessful() && authResultTask.getResult() != null) {
                            String uid = authResultTask.getResult().getUser().getUid();
                            emitter.onSuccess(new Result.Success<>(new AuthUser(uid)));

                        } else {
                            emitter.onSuccess(new Result.Error<>(new UserNotAuthenticatedException()));
//                            emitter.onError(new UserNotAuthenticatedException());
                        }
                    });
        }).subscribeOn(appExecutors.net())
                .observeOn(appExecutors.ui());
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
    public Single<Result<String>> getHumanReadableName(String userId) {
        return Single.create(new SingleOnSubscribe<Result<String>>() {
            @Override
            public void subscribe(SingleEmitter<Result<String>> emitter) throws Exception {
                database.getReference(usersPath).child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(emitter.isDisposed())return;
                        if (dataSnapshot.exists()) {
                            DataSnapshot snapshot = dataSnapshot.getChildren().iterator().next();
                            if (snapshot == null || snapshot.getValue() == null) {
                                return;
                            }
                            emitter.onSuccess(new Result.Success<>(snapshot.getValue().toString()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        if(emitter.isDisposed()){
//                            return;
//                        }
//                        emitter.onError(databaseError.toException());
                    }
                });
            }
        }).subscribeOn(appExecutors.net());

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
