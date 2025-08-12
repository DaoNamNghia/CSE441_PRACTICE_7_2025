package com.example.ex13_3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {
    TextView txt_SubPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        txt_SubPhone  = findViewById(R.id.txt_SubPhone);
        Intent myIntent = getIntent();
        String namePhone  = myIntent.getStringExtra("name");
        txt_SubPhone.setText(namePhone);
    }
}