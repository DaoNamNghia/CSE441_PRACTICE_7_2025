package com.example.ex041;

import java.text.DecimalFormat;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button btnChandoan;
    EditText editTen, editChieucao, editCannang, editBMI, editChandoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        btnChandoan = findViewById(R.id.btnBMI);  // Sửa lại ID Button
        editTen = findViewById(R.id.edtten);
        editChieucao = findViewById(R.id.edtchieucao);
        editCannang = findViewById(R.id.edtcannang);
        editBMI = findViewById(R.id.edtBMI);
        editChandoan = findViewById(R.id.edtChuanDoan);

        // Sự kiện click tính BMI
        btnChandoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra nếu người dùng chưa nhập chiều cao hoặc cân nặng
                if (editChieucao.getText().toString().isEmpty() || editCannang.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ chiều cao và cân nặng", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Lấy giá trị từ các trường nhập
                    double H = Double.parseDouble(editChieucao.getText().toString());
                    double W = Double.parseDouble(editCannang.getText().toString());

                    // Kiểm tra giá trị đầu vào có hợp lệ không
                    if (H <= 0 || W <= 0) {
                        Toast.makeText(MainActivity.this, "Chiều cao và cân nặng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Tính BMI
                    double BMI = W / Math.pow(H, 2);

                    // Chẩn đoán theo giá trị BMI
                    String chandoan = "";
                    if (BMI < 18) {
                        chandoan = "Bạn gầy";
                    } else if (BMI <= 24.9) {
                        chandoan = "Bạn bình thường";
                    } else if (BMI <= 29.9) {
                        chandoan = "Bạn béo phì độ 1";
                    } else if (BMI <= 34.9) {
                        chandoan = "Bạn béo phì độ 2";
                    } else {
                        chandoan = "Bạn béo phì độ 3";
                    }

                    // Định dạng BMI với 1 chữ số thập phân
                    DecimalFormat dcf = new DecimalFormat("#.0");
                    editBMI.setText(dcf.format(BMI));
                    editChandoan.setText(chandoan);

                } catch (NumberFormatException e) {
                    // Bắt lỗi nếu nhập sai định dạng
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
