package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class list_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button button = findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ MainActivity sang Activity khác
                Intent intent = new Intent(list_view.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ListView listView = (ListView)
                findViewById(R.id.list_view);
        TextView tvselection = findViewById(R.id.tv_select);
        final String arr[]= {"Teo","Ty","Bin","Bo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        tvselection.setText("position: " + arg2 + "; value: " + arr[arg2]);
                    }
                }
        );
        ArrayList<String> names = new ArrayList<String>(0);
        Button btn_Nhap = findViewById(R.id.btn_nhap);
        EditText edit_text = findViewById(R.id.editnhap);
        TextView tvselection2 = findViewById(R.id.tv_select_2);
        ListView listView2 = (ListView)
                findViewById(R.id.list_view_2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,names);
        listView2.setAdapter(adapter2);
        btn_Nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                names.add(edit_text.getText().toString());
                adapter2.notifyDataSetChanged();
            }
        });
    //5. Xử lý sự kiện chọn một phần tử trong ListView
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                tvselection2.setText("position: " + arg2 + "; value: " + names.get(arg2));
            }
        });
    //6. xử lý sự kiện Long click
        listView2.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        // Xác định phần tử cần xóa trong danh sách dựa trên vị trí của nó trong ListView
                        String itemToRemove = names.get(position);

                        // Xóa phần tử đó khỏi danh sách
                        names.remove(itemToRemove);

                        // Cập nhật adapter để hiển thị danh sách mới
                        adapter2.notifyDataSetChanged();

                        // Trả về true để chỉ ra rằng sự kiện long click đã được xử lý
                        return true;
                    }
                }
        );
    }
}