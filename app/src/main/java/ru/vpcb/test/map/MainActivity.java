package ru.vpcb.test.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import ru.vpcb.test.map.data.IJob;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.login.LoginActivity;
import ru.vpcb.test.map.model.Note;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Replace with your own action",
//                        Toast.LENGTH_SHORT).show();

                init();
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;

                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_login);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    return true;

                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_maps);
                    startActivity(new Intent(MainActivity.this, MapsActivity.class));
                    return true;
            }
            return false;
        }
    };


    private final Object lock = new Object();

    private void init() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String notesPath = "notes";


        IJob<Note> replaceAuthorName = new IJob<Note>() {
            @Override
            public void join(Note note) {

                if (note == null) return;
                note.setUser(note.getUser());
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {


                Sync<List<Note>> sync = new Sync<>();
                sync.setReady(false);


                database.getReference(notesPath).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        try {
//                            sync.getLock().lock();
                        synchronized (lock) {
                            if (dataSnapshot.exists()) {
                                List<Note> noteResults = new ArrayList<>();
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Note note = child.getValue(Note.class);
                                    replaceAuthorName.join(note);
                                    noteResults.add(note);
                                }
                                Result<List<Note>> result = new Result.Success<>(noteResults);
                                sync.setResult(result);
                            } else {
                                Result<List<Note>> result = new Result.Error<>(new NullPointerException());
                                sync.setResult(result);
                            }
                            sync.setReady(true);
                            lock.notifyAll();
                        }
//                        } finally {
//                            sync.setReady(true);
//                            sync.getCondition().signalAll();
//                            sync.getLock().unlock();
//                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Result<List<Note>> result = new Result.Error<>(databaseError.toException());

                    }
                });

//                try {
//                    sync.getLock().lock();
//                    while (!sync.isReady()) {
//                        sync.getCondition().await();
//                    }
//                } catch (InterruptedException e) {
//                    //
//                } finally {
//                    sync.getLock().unlock();
//                }

                synchronized (lock){
                    while(!sync.isReady()){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Result<List<Note>> result = sync.getResult();
             }
        }).start();


        int k = 1;
    }

    private static class Sync<T> {
        private final ReentrantLock lock;
        private final Condition condition;
        private boolean isReady;
        private Result<T> mResult;

        public Sync() {
            this.lock = new ReentrantLock();
            this.condition = lock.newCondition();
        }

        public ReentrantLock getLock() {
            return lock;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setResult(Result<T> result) {
            mResult = result;
        }

        public Result<T> getResult() {
            return mResult;
        }

        public void setReady(boolean isReady) {
            this.isReady = isReady;
        }

        public boolean isReady() {
            return this.isReady;
        }
    }


}
