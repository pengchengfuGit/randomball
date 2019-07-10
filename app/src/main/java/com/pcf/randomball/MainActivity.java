package com.pcf.randomball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pcf.randomball.view.RandomView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RandomView randomLayout = findViewById(R.id.rl);
        randomLayout.setMaxBallNumber(5);
    }
}
