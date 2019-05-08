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


    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();




        int k = 1;
    }

    private static class Sync<T> {
        private final ReentrantLock lock = new ReentrantLock();
        private boolean isReady;
        private Result<T> mResult;


        ReentrantLock getLock() {
            return lock;
        }


        void lock() {
            setReady(false);
        }

        void unlock() {
            setReady(true);
            lock.notifyAll();
        }

        void waiting() {
            while (!isReady()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        void setResult(Result<T> result) {
            synchronized (lock) {
                mResult = result;
            }
        }

        Result<T> getResult() {
            synchronized (lock) {
                return mResult;
            }
        }

        synchronized void setReady(boolean isReady) {
            this.isReady = isReady;
        }

        synchronized boolean isReady() {
            return this.isReady;
        }
    }


}
