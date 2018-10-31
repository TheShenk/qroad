package com.qwerty.qroad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

public class MapActivity extends AppCompatActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();

        title = findViewById(R.id.mapTitle);
        title.setText(intent.getStringExtra("title"));

        ImageButton button = findViewById(R.id.searchButton);
    }
}
