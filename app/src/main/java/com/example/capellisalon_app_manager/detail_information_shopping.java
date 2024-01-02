package com.example.capellisalon_app_manager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class detail_information_shopping extends AppCompatActivity {
    private ListView listView ;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping);
        listView = findViewById(R.id.lv_shopping);
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
        DatabaseReference userShoppingRef = FirebaseDatabase.getInstance().getReference().child("Orders").child("5cQ62L3VHsgaiMTadSoRufikfhD3").child("7FIY6NFITT");
        userShoppingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("Firebase", "DataSnapshot exists: " + dataSnapshot.getValue().toString());
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String value = snapshot.getValue(String.class).toString();
                        arrayList.add(value);
                        Log.d("Firebase", "onDataChange: "+ value);
                    }
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


    }
}
