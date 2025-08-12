package com.example.ex06;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtHoTen, edtCCCD, edtBosung;
    CheckBox ckDocBao, ckDocSach, ckDocCode;
    Button btnGui;
    RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtHoTen = findViewById(R.id.edt_ho_ten);
        edtCCCD = findViewById(R.id.edt_cccd);
        edtBosung = findViewById(R.id.edt_bosung);
        ckDocBao = findViewById(R.id.ck_docbao);
        ckDocSach = findViewById(R.id.ck_docsach);
        ckDocCode = findViewById(R.id.ck_doccode);
        btnGui = findViewById(R.id.btn_gui);
        group = findViewById(R.id.group);

        btnGui.setOnClickListener(view -> doShowInformation());
    }
    private void doShowInformation() {
        String ten = edtHoTen.getText().toString();
        ten = ten.trim();
        if (ten.length() < 3) {
            edtHoTen.requestFocus();
            edtHoTen.selectAll();
            Toast.makeText(this, "Họ tên không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        String cccd = edtCCCD.getText().toString();
        cccd = cccd.trim();
        if (cccd.length() != 9) {
            edtCCCD.requestFocus();
            edtCCCD.selectAll();
            Toast.makeText(this, "CCCD không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        int checkedId = group.getCheckedRadioButtonId();  // Lấy ID của RadioButton được chọn
        String bang;
        if (checkedId != -1) {
            RadioButton radioButton = findViewById(checkedId);
            bang = radioButton.getText().toString();  // Lấy text của RadioButton
        } else {
            Toast.makeText(this, "Bạn chưa chọn bằng cấp", Toast.LENGTH_SHORT).show();
            return;  // Ngăn không cho tiếp tục nếu chưa chọn bằng cấp
        }

        String sothich = "";
        if (ckDocBao.isChecked()) {
            sothich += ckDocBao.getText().toString() + "\n";
        }
        if (ckDocSach.isChecked()) {
            sothich += ckDocSach.getText().toString() + "\n";
        }
        if (ckDocCode.isChecked()) {
            sothich += ckDocCode.getText().toString() + "\n";
        }
        String bosung = edtBosung.getText().toString();
        bosung = bosung.trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin cá nhân");
        builder.setPositiveButton("Đóng", (dialogInterface, i) -> dialogInterface.cancel());
        String msg = ten +"\n";
        msg += cccd + "\n";
        msg += bang + "\n";
        msg += sothich;
        msg += "----------------\n";
        msg += "THÔNG TIN BỔ SUNG: \n";
        msg += bosung+ "\n";
        builder.setMessage(msg);
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
        b.setTitle("Question");
        b.setMessage("Are you want exit?");
        b.setPositiveButton("yes", (dialogInterface, i) -> finish());
        b.setNegativeButton("no", (dialogInterface, i) -> dialogInterface.cancel());
        b.create().show();
    }

}