package com.example.ex12_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView tx1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx1 = findViewById(R.id.selection);
        final String arr1[] = {"Iphone 8 plus", "Samsung Galaxy S7", "Nokia Lumia 730", "Sony Xperia XZ", "HTC One E9"};
        Object ArrayAdapter;
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr1);
        ListView lv1 = findViewById(R.id.lv1);
        lv1.setAdapter(adapter1);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tx1.setText("Điện thoại được chọn "+i+" : "+arr1[i]);
            }
        });
    }
}