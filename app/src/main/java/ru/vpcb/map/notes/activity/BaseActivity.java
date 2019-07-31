package ru.vpcb.map.notes.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.manager.FAManager;
import ru.vpcb.map.notes.manager.FCManager;

public abstract class BaseActivity extends AppCompatActivity implements IComponent {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setupComponent();
        } catch (Exception e) {
            FCManager.log(e);
        }

    }

    @Override
    public void setupComponent()  { // stub
    }


    protected void setupActionBar(int id) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        title.setText(id);
        setSupportActionBar(toolbar);
    }


    private  FAManager setupServices() {
        try {

            FCManager.setup(this);                   // Crashlytics
            return new FAManager(this);       // Analytics

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return null;
    }
}
