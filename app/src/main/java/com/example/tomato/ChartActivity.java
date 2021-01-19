package com.example.tomato;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class ChartActivity extends AppCompatActivity {

    @java.lang.Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setToolBar();

        FragmentTransaction tra = getSupportFragmentManager().beginTransaction();
        tra.add(R.id.frgmt, new ChartFragment());
        tra.commit();
    }

    private void setToolBar() {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}