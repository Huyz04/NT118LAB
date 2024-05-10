package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class list_view_th3 extends AppCompatActivity {
    RadioGroup rgType;
    EditText etId;
    EditText etName;
    ArrayList<String> names = new ArrayList<String>(0);
    Employee employee;
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayAdapter<String> adapter;
    public abstract static class Employee{
        private String id ;
        private String name;
        private boolean manager;
        public abstract String TinhLuong();
        public String ToString(){
            return this.id + " - "+this.name;
        }
        public boolean isManager() {
            return manager;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setManager(boolean b) {
            this.manager = b;
        }
    }
    public class EmployeeParttime extends Employee{
        public String TinhLuong(){
            return "150.0";
        }
        @Override
        public String ToString(){
            return super.ToString() + " -->PartTime=" +this.TinhLuong();
        }
    }
    public class EmployeeFulltime extends Employee {
        public String TinhLuong() {
            return "500.0";
        }

        @Override
        public String ToString() {
            return super.ToString() + " -->FullTime=" + this.TinhLuong();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view_th3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         rgType = findViewById(R.id.radio_group);
         etId =findViewById(R.id.edit_manv);
         etName = findViewById(R.id.edit_tennv);
         employees = new ArrayList<>();
        ListView listView3 = findViewById(R.id.lv_th3);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,names);
        listView3.setAdapter(adapter);
        Button btn_back = findViewById(R.id.th3_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ MainActivity sang Activity khác
                Intent intent = new Intent(list_view_th3.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnNhap = findViewById(R.id.btn_nhap);
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                addNewEmployee();
            }
        });

    }
    public void addNewEmployee() {
        int radId = rgType.getCheckedRadioButtonId();
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        if (radId == R.id.rd_chinhthuc) {
            employee = new EmployeeFulltime();
        } else {
            employee = new EmployeeParttime();
        }
        employee.setId(id);
        employee.setName(name);
        employees.add(employee);
        names.add(employee.ToString());
        adapter.notifyDataSetChanged();
    }
}