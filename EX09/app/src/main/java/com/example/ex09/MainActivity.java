package com.example.ex09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageButton btn_play, btn_stop;
    Boolean flag = true;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_play = findViewById(R.id.btn_play);
        btn_stop = findViewById(R.id.btn_stop);
        btn_play.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, Myservice.class);
            startService(intent1);
            if (flag) {
                btn_play.setImageResource(R.drawable.stop);
                flag = false;
            } else {
                btn_play.setImageResource(R.drawable.play);
                flag = true;
            }
        });
        btn_stop.setOnClickListener(view -> {
            Intent intent2 = new Intent(MainActivity.this, Myservice.class);
            stopService(intent2);
            btn_play.setImageResource(R.drawable.play);
        });
    }
}