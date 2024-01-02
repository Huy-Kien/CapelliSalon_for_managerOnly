package com.example.capellisalon_app_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class booking extends AppCompatActivity {
    ListView listnoti;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);

        listnoti = findViewById(R.id.lv_booking);
        notificationList = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView, notificationList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckBox checkBox = view.findViewById(R.id.checkBox);
                return view;
            }
        };
        listnoti.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userBookingRef = FirebaseDatabase.getInstance().getReference().child("userID").child("5cQ62L3VHsgaiMTadSoRufikfhD3").child("Personal Information").child("email");
        userBookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("Firebase", "DataSnapshot exists: " + dataSnapshot.getValue());
                    Object data = dataSnapshot.getValue();
                    if (data != null) {
                        String convertedData = data.toString();
                        notificationList.add(convertedData);
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

        listnoti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // TODO: Xử lý sự kiện click tại đây
                Intent intent = new Intent(booking.this, detail_information_booking.class);
                startActivity(intent);

                Toast.makeText(booking.this, "Clicked item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
