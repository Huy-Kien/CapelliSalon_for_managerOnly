package com.example.capellisalon_app_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class detail_information_shopping extends AppCompatActivity {
    private ListView listView ;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping);

        Intent intent = getIntent();
        listView = findViewById(R.id.lv_shopping);
        arrayList = new ArrayList<String>();
        hashMap = new HashMap<>();

        hashMap = (HashMap<String, String>) intent.getSerializableExtra("product_info");
        for (String value : hashMap.values()) {
            arrayList.add(value);
        }

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrayList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckBox checkBox = view.findViewById(R.id.checkBox);
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        // Thực hiện xử lý khi nút "Back" được nhấn
        // Ví dụ: Đóng Activity hiện tại
        super.onBackPressed();
        Toast.makeText(this, "Backpressed", Toast.LENGTH_SHORT).show();
        finish();
    }
}
