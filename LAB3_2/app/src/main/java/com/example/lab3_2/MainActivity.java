package com.example.lab3_2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

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
        List<String> users = new ArrayList<String>();
        DatabaseHandler db = new DatabaseHandler(this);
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));
        // Reading all contacts
        Log.e("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getId()+" ,Name: " + cn.getName() + ",Phone: " + cn.getPhoneNumber();
        // Writing Contacts to log
            Log.e("Name: ", log);
            users.add(log);
        }
        ListView lvUser = (ListView) findViewById(R.id.lv_contact);
        assert users != null;
        ArrayAdapter<String> userAdapter = new
                ArrayAdapter<String>(MainActivity.this, R.layout.item_contact, users);
        lvUser.setAdapter(userAdapter);
        db.close();
        lvUser.setOnItemLongClickListener((parent, view, position, id) -> {
            // Lấy phần tử được chọn từ danh sách users
            String selectedUser = users.get(position);

            // Tách ra id từ selectedUser
            int colonIndex = selectedUser.indexOf(":");
            int commaIndex = selectedUser.indexOf(",");
            String idString = selectedUser.substring(colonIndex + 1, commaIndex).trim();
            int selectedUserId = Integer.parseInt(idString);
            // Xóa dòng từ SQLite
            db.deleteContact(selectedUserId);
            // Cập nhật danh sách users
            users.remove(position);
            userAdapter.notifyDataSetChanged();

            return true;
        });
    }
}