package com.example.ex05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextYear;
    private Button btnConvert;
    private TextView textViewResult;

    // Mảng lưu tên 12 con giáp
    private String[] zodiacAnimals = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};
    // Mảng lưu tên 10 can
    private String[] heavenlyStems = {"Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các view
        editTextYear = findViewById(R.id.editTextYear);
        btnConvert = findViewById(R.id.btnConvert);
        textViewResult = findViewById(R.id.textViewResult);

        // Xử lý khi nhấn nút chuyển đổi
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yearString = editTextYear.getText().toString();
                if (!yearString.isEmpty()) {
                    int year = Integer.parseInt(yearString);
                    String lunarYear = convertSolarToLunar(year);
                    textViewResult.setText("Năm âm lịch: " + lunarYear);
                }
            }
        });
    }

    // Hàm chuyển đổi năm dương lịch sang năm âm lịch
    private String convertSolarToLunar(int year) {
        int stemIndex = (year + 6) % 10; // Tính can
        int animalIndex = (year + 8) % 12; // Tính chi

        String stem = heavenlyStems[stemIndex];
        String animal = zodiacAnimals[animalIndex];

        return stem + " " + animal;
    }
}
