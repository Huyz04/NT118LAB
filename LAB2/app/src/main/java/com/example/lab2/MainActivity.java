package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        Button button = findViewById(R.id.btn_list_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ MainActivity sang Activity khác
                Intent intent = new Intent(MainActivity.this, list_view.class);
                startActivity(intent);
            }
        });
        Button btn_Th3 = findViewById(R.id.btn_TH3);
        btn_Th3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ MainActivity sang Activity khác
                Intent intent = new Intent(MainActivity.this, list_view_th3.class);
                startActivity(intent);
            }
        });
        Button btn_TH4 = findViewById(R.id.btn_th4);
        btn_TH4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ MainActivity sang Activity khác
                Intent intent = new Intent(MainActivity.this, th4.class);
                startActivity(intent);
            }
        });
        Button btn_TH5 = findViewById(R.id.btn_th5);
        btn_TH5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ MainActivity sang Activity khác
                Intent intent = new Intent(MainActivity.this, th5.class);
                startActivity(intent);
            }
        });
        Button btn_TH6 = findViewById(R.id.btn_th6);
        btn_TH6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ MainActivity sang Activity khác
                Intent intent = new Intent(MainActivity.this, th6.class);
                startActivity(intent);
            }
        });
        Button btn_TH7 = findViewById(R.id.btn_th7);
        btn_TH7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ MainActivity sang Activity khác
                Intent intent = new Intent(MainActivity.this, th7.class);
                startActivity(intent);
            }
        });
    }
}