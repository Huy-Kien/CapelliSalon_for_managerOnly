package com.example.capellisalon_app_manager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class detail_information_booking extends AppCompatActivity {
    ListView dtbooking;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dtbookingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_infomation_booking);

        dtbooking = findViewById(R.id.lv_detail_booking);
        dtbookingList = new ArrayList<>(); // Sửa ở đây

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dtbookingList);
        dtbooking.setAdapter(adapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userBookingRef = FirebaseDatabase.getInstance().getReference().child("userID").child("5cQ62L3VHsgaiMTadSoRufikfhD3").child("Personal Information");
        userBookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("Firebase", "DataSnapshot exists: " + dataSnapshot.getValue());
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Object data = snapshot.getValue();
                        if (data != null) {
                            String convertedData = data.toString();
                            dtbookingList.add(convertedData);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("Firebase", "No data found at this path");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "onCancelled called: " + databaseError.getMessage());
            }
        });
    }
}
