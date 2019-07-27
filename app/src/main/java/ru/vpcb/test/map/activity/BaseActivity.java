package ru.vpcb.test.map.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ru.vpcb.test.map.R;
import ru.vpcb.test.map.manager.FCManager;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setupComponent();
        } catch (Exception e) {
            FCManager.log(getClass().getSimpleName(), e.toString());
        }

    }

    abstract protected void setupComponent();

    protected void setupActionBar(int id) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        title.setText(id);
        setSupportActionBar(toolbar);
    }
}
