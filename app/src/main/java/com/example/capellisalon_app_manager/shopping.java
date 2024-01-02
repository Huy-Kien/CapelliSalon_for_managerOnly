package com.example.capellisalon_app_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.List;

public class shopping extends AppCompatActivity {

    private ListView listView ;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private Button btn_del_sel;
    private Button btn_del_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping);
        listView = findViewById(R.id.lv_shopping);
        btn_del_sel = findViewById(R.id.btn_shopping_del_sel);
        btn_del_all = findViewById(R.id.btn_shopping_delALL);
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView, arrayList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckBox checkBox = view.findViewById(R.id.checkBox);
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
        DatabaseReference userShoppingRef = FirebaseDatabase.getInstance().getReference().child("Orders").child("5cQ62L3VHsgaiMTadSoRufikfhD3").child("7FIY6NFITT").child("email");
        userShoppingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("Firebase", "DataSnapshot exists: " + dataSnapshot.getValue().toString());
                    arrayList.add(dataSnapshot.getValue(String.class));
//                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String value = snapshot.getValue(String.class).toString();
//                        arrayList.add(value);
//                        Log.d("Firebase", "onDataChange: "+ value);
//                    }
                    arrayAdapter.notifyDataSetChanged();
                } else {
                    Log.w("Firebase", "No data found at this path");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "onCancelled called: " + databaseError.getMessage());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // TODO: Xử lý sự kiện click tại đây
                Intent intent = new Intent(shopping.this, detail_information_shopping.class);
                startActivity(intent);

                Toast.makeText(shopping.this, "Clicked item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
