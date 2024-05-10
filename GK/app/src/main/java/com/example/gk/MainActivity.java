package com.example.gk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editTen, editChieuCao, editCanNang;
    Button btnBMI, btnXoa, btnDong;
    TextView tv;
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
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển từ Activity1 sang Activity2
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                // Chuyển sang Activity2
                startActivity(intent);
            }
        });
        editTen = findViewById(R.id.edit_Ten);
        editChieuCao = findViewById(R.id.edit_chieucao);
        editCanNang = findViewById(R.id.edit_cannang);
        btnBMI = findViewById(R.id.bmi);
        btnXoa = findViewById(R.id.xoa);
        btnDong = findViewById(R.id.dong);
        tv = findViewById(R.id.tv);

        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy chiều cao và cân nặng từ EditText
                double chieuCao = Double.parseDouble(editChieuCao.getText().toString());
                double canNang = Double.parseDouble(editCanNang.getText().toString());

                // Tính toán BMI
                double bmi = canNang / (chieuCao * chieuCao);

                // Xác định phân loại BMI
                String thongTin = "";
                if (bmi < 18.5) {
                    thongTin = "người gầy";
                } else if (bmi < 24.9) {
                    thongTin = "người bình thường";
                } else if (bmi < 29.9) {
                    thongTin = "người béo phì độ I";
                } else if (bmi < 34.9) {
                    thongTin = "người béo phì độ II";
                } else {
                    thongTin = "người béo phì độ III";
                }

                // Hiển thị kết quả
                tv.setText("BMI hiện tại là: " + bmi + "\nChuẩn đoán bạn " + editTen.getText().toString() + " là " + thongTin);
            }
        });

        // Xử lý sự kiện cho button "Xóa"
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTen.setText("");
                editChieuCao.setText("");
                editCanNang.setText("");
                tv.setText("");
            }
        });

        // Xử lý sự kiện cho button "Đóng"
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}