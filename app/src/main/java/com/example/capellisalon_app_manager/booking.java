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
    Button btn_delete_selections, btn_delete_all;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notificationList;
    public void fetchDataFromFirebase(String userId) {
        DatabaseReference userBookingRef = FirebaseDatabase.getInstance().getReference().child("userID").child(userId).child("Personal Information").child("email");
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);
        btn_delete_selections= findViewById(R.id.btn_delete_selections);
        btn_delete_all = findViewById(R.id.btn_delete_all);
        listnoti = findViewById(R.id.lv_booking);
        notificationList = new ArrayList<>();
        fetchDataFromFirebase("5cQ62L3VHsgaiMTadSoRufikfhD3");
        fetchDataFromFirebase("NAKXsVLE1XWi3QlQcg6iBLqaHDZ2");
        fetchDataFromFirebase("vnoj7F2qi2fqQYwjq8iAiF7IccA3");

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



        listnoti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // TODO: Xử lý sự kiện click tại đây
                Intent intent = new Intent(booking.this, detail_information_booking.class);

                String userId;
                if (selectedItem.equals("a")) {
                    userId = "NAKXsVLE1XWi3QlQcg6iBLqaHDZ2";
                } else if (selectedItem.equals("huy@gmail.com")) {
                    userId = "vnoj7F2qi2fqQYwjq8iAiF7IccA3";
                } else if (selectedItem.equals("phamthanhnhut166@gmail.com")) {
                    userId = "5cQ62L3VHsgaiMTadSoRufikfhD3";
                } else {
                    userId = ""; // Hoặc bất kỳ giá trị mặc định nào bạn muốn
                }

                intent.putExtra("userId", userId);
                startActivity(intent);

                Toast.makeText(booking.this, "Clicked item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        btn_delete_selections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lặp qua tất cả các view trong ListView
                for (int i = 0; i < listnoti.getChildCount(); i++) {
                    // Lấy view hiện tại
                    View view = listnoti.getChildAt(i);
                    // Tìm checkbox trong view
                    CheckBox checkBox = view.findViewById(R.id.checkBox);
                    // Kiểm tra nếu checkbox được chọn
                    if (checkBox.isChecked()) {
                        // Xóa item tương ứng từ danh sách
                        notificationList.remove(i);
                        // Cập nhật adapter
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        btn_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa tất cả các mục từ danh sách
                notificationList.clear();
                // Cập nhật adapter
                adapter.notifyDataSetChanged();
            }
        });

    }
}
