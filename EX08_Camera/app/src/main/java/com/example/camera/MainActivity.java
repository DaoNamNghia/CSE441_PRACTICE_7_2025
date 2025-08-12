package com.example.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView myimg;
    ImageButton btncapture; @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
        myimg  = findViewById(R.id.myimg);
        btncapture = findViewById(R.id.btncapture);
        btncapture.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            Intent myintent = new Intent(ACTION_IMAGE_CAPTURE);
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(MainActivity.this,new  String[]{Manifest.permission.CAMERA}, 1);
                return;
            }
            Intent cameraintent = null;
            startActivityForResult(cameraintent,99);
        }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && requestCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            myimg.setImageBitmap(photo);
        }
    }

}