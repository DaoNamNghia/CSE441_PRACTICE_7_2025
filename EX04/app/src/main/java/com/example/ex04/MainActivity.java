package com.example.ex04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText edtdoC, edtdoF;
    Button btncf, btnfc, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        edtdoC = findViewById(R.id.edtdoC);
        edtdoF = findViewById(R.id.edtdoF);
        btncf = findViewById(R.id.btncf);
        btnfc = findViewById(R.id.btnfc);
        btnClear = findViewById(R.id.btnClear);

        // Nút chuyển từ Celsius sang Fahrenheit
        btncf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String doC = edtdoC.getText().toString().trim();
                    if (!doC.isEmpty()) {
                        // Chuyển đổi và hiển thị kết quả
                        DecimalFormat dcf = new DecimalFormat("#.00");
                        double C = Double.parseDouble(doC);
                        edtdoF.setText(dcf.format(C * 1.8 + 32));
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter Celsius", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Nút chuyển từ Fahrenheit sang Celsius
        btnfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String doF = edtdoF.getText().toString().trim();
                    if (!doF.isEmpty()) {
                        DecimalFormat dcf = new DecimalFormat("#.00");
                        double F = Double.parseDouble(doF);
                        edtdoC.setText(dcf.format((F - 32) / 1.8));
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter Fahrenheit", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Nút "Clear" để xóa các trường nhập liệu
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtdoC.setText("");
                edtdoF.setText("");
            }
        });
    }
}
