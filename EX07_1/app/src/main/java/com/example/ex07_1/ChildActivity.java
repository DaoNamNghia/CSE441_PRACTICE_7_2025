package com.example.ex07_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;


public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        Button btn2 = findViewById(R.id.btnBack);
        btn2.setOnClickListener(view -> {
            Intent intent1 = new Intent(ChildActivity.this,MainActivity.class);
            startActivity(intent1);
        });
    }
}
