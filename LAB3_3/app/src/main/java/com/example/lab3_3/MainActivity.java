package com.example.lab3_3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText EditMSSV, EditHoten, EditLop;
    Button BtnInsert, BtnDelete, BtnUpdate, BtnQuery;
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    String DATABASE_NAME = "qlsv.db";
    //Khai báo ListView
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BtnDelete = findViewById(R.id.delete);
        BtnInsert = findViewById(R.id.insert);
        BtnUpdate = findViewById(R.id.update);
        BtnQuery = findViewById(R.id.query);
        EditHoten = findViewById(R.id.edit_hoten);
        EditLop = findViewById(R.id.edit_lop);
        EditMSSV = findViewById(R.id.edit_mssv);
        // Tạo ListView
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);
        //Ham Copy CSDL từ assets vào thư mục Databases
        processCopy();
        //Mo CSDL trong ung dung len
        database = openOrCreateDatabase("qlsv.db", MODE_PRIVATE, null);
        BtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mssv = EditMSSV.getText().toString();
                String lop = EditLop.getText().toString();
                String hoten = EditHoten.getText().toString();
                ContentValues myvalue = new ContentValues();
                myvalue.put("MSSV", mssv);
                myvalue.put("Lop", lop);
                myvalue.put("Hoten", hoten);
                String msg = "";
                if (database.insert("qlsv", null, myvalue) == -1) {
                    msg = "Fail to insert";
                } else {
                    msg = "Insert successfully";
                    reload();
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mssv = EditMSSV.getText().toString();
                int n = database.delete("qlsv", "MSSV = ?", new String[]{mssv});
                String msg = "";
                if (n == 0) {
                    msg = "No record to Delete";
                } else {
                    msg = n + "record is deleted";
                    reload();
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mssv = EditMSSV.getText().toString();
                String lop = EditLop.getText().toString();
                String hoten = EditHoten.getText().toString();
                ContentValues myvalue = new ContentValues();
                myvalue.put("Lop", lop);
                myvalue.put("Hoten", hoten);
                int n = database.update("qlsv", myvalue, "MSSV = ?", new String[]{mssv});
                String msg = "";
                if (n == 0) {
                    msg = "No record to Update";
                } else {
                    msg = n + "record is Update";
                    reload();
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        BtnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.clear();
                Cursor c =
                        database.query("qlsv", null, null, null, null, null, null);
                c.moveToFirst();
                String data = "";
                while (c.isAfterLast() == false) {
                    data = c.getString(0) + "-" + c.getString(1) + "-" + c.getString(2);
                    mylist.add(data);
                    c.moveToNext();
                }
                c.close();
                myadapter.notifyDataSetChanged();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedString = parent.getItemAtPosition(position).toString();
                String result = selectedString.substring(0, selectedString.indexOf("-"));
                String[] columns = {"MSSV", "Lop", "Hoten"};
                Cursor cursor = database.query("qlsv",columns , "MSSV = ?", new String[]{result}, null, null, null);
                // Kiểm tra và hiển thị kết quả trên các EditText
                if (cursor != null && cursor.moveToFirst()) {
                    // Lấy dữ liệu từ Cursor
                    @SuppressLint("Range") String mssv = cursor.getString(cursor.getColumnIndex("MSSV"));
                    @SuppressLint("Range") String lop = cursor.getString(cursor.getColumnIndex("Lop"));
                    @SuppressLint("Range") String hoten = cursor.getString(cursor.getColumnIndex("Hoten"));
                    EditMSSV.setText(mssv);
                    EditLop.setText(lop);
                    EditHoten.setText(hoten);
                    cursor.close();
                }
            }
        });
    }
    public void reload() {
        mylist.clear();
        Cursor c =
                database.query("qlsv", null, null, null, null, null, null);
        c.moveToFirst();
        String data = "";
        while (c.isAfterLast() == false) {
            data = c.getString(0) + "-" + c.getString(1) + "-" + c.getString(2);
            mylist.add(data);
            c.moveToNext();
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }
    private void processCopy() {
    //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder",
                        Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+
                DATABASE_NAME;
    }
    // Ham copy file DB tu thu muc Asset vao file DB moi tao ra trong ung dung
    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            // Kiem tra neu duong dan khong co, thi tao moi file
            File f = new File(getApplicationInfo().dataDir +
                    DB_PATH_SUFFIX);
            if (!f.exists()) f.mkdir();
            // Mo empty db su dung output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // Sao chep du lieu bytes tu input toi ouput
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}