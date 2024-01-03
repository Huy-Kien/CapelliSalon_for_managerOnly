
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
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;


public class booking extends AppCompatActivity {

    ListView listnoti ;
    ArrayAdapter<String> arrayAdapter1;
    ArrayList<String> arrayList1;
    Button btn_delele_selections;
    Button btn_delete_all;
    Intent intent = new Intent();
    ArrayList<HashMap<String, String>> bookingList;
    HashMap<String,String> booking_info;

    private DataSnapshot dataSnapshot_for_button_del1;
    String value_onItem, id_product_on_item1, mail_onitem1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);
        GenericTypeIndicator<HashMap<String, String>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, String>>() {
        };
        listnoti = findViewById(R.id.lv_booking);
        btn_delele_selections = findViewById(R.id.btn_delete_selections);
        btn_delete_all = findViewById(R.id.btn_delete_all);
        arrayList1 = new ArrayList<String>();
        bookingList = new ArrayList<HashMap<String, String>>();
        arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView, arrayList1) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckBox checkBox = view.findViewById(R.id.checkBox);
                return view;
            }
        };
        listnoti.setAdapter(arrayAdapter1);

        listnoti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                intent = new Intent(booking.this, detail_information_booking.class);
                for (DataSnapshot snapshot_user_level : dataSnapshot_for_button_del1.getChildren()) {
                    // Personal Information
                    String clientmail = snapshot_user_level.child("Personal Information").child("email").getValue(String.class);
                    String clientName = snapshot_user_level.child("Personal Information").child("name").getValue(String.class);
                    String dob = snapshot_user_level.child("Personal Information").child("dob").getValue(String.class);
                    String mobile = snapshot_user_level.child("Personal Information").child("mobile").getValue(String.class);
                    String address = snapshot_user_level.child("Personal Information").child("address").getValue(String.class);

                    // InfoBooking
                    String locationName = snapshot_user_level.child("InfoBooking").child("locationName").getValue(String.class);
                    String time = snapshot_user_level.child("InfoBooking").child("time").getValue(String.class);
                    String staffName = snapshot_user_level.child("InfoBooking").child("staffName").getValue(String.class);
                    String phone = snapshot_user_level.child("InfoBooking").child("phone").getValue(String.class);
                    String bookingAddress = snapshot_user_level.child("InfoBooking").child("address").getValue(String.class);

                    String value_onItem = "Email: " + clientmail + " || Name: " + clientName + " || DOB: " + dob + " || Mobile: " + mobile + " || Address: " + address
                            + " || Location Name: " + locationName + " || Time: " + time + " || Staff Name: " + staffName + " || Phone: " + phone + " || Booking Address: " + bookingAddress;

                    if (selectedItem.equals(value_onItem)) {
                            HashMap<String, String> hashmap = new HashMap<>();
                            for (DataSnapshot child : snapshot_user_level.child("InfoBooking").getChildren()) {
                                hashmap.put(child.getKey(), child.getValue(String.class));
                            }
                            intent.putExtra("booking_info", hashmap);
                            Toast.makeText(booking.this, "Query successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                startActivity(intent);
                Toast.makeText(booking.this, "client: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("userID");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("Firebase", "DataSnapshot exists: " + dataSnapshot.getValue().toString());
                    dataSnapshot_for_button_del1 = dataSnapshot;
                    for (DataSnapshot snapshot_user_level : dataSnapshot.getChildren()) {
                        // Personal Information
                        String clientmail = snapshot_user_level.child("Personal Information").child("email").getValue(String.class);
                        String clientName = snapshot_user_level.child("Personal Information").child("name").getValue(String.class);
                        String dob = snapshot_user_level.child("Personal Information").child("dob").getValue(String.class);
                        String mobile = snapshot_user_level.child("Personal Information").child("mobile").getValue(String.class);
                        String address = snapshot_user_level.child("Personal Information").child("address").getValue(String.class);

                        // InfoBooking
                        String locationName = snapshot_user_level.child("InfoBooking").child("locationName").getValue(String.class);
                        String time = snapshot_user_level.child("InfoBooking").child("time").getValue(String.class);
                        String staffName = snapshot_user_level.child("InfoBooking").child("staffName").getValue(String.class);
                        String phone = snapshot_user_level.child("InfoBooking").child("phone").getValue(String.class);
                        String bookingAddress = snapshot_user_level.child("InfoBooking").child("address").getValue(String.class);

                        String value = "Email: " + clientmail + " || Name: " + clientName + " || DOB: " + dob + " || Mobile: " + mobile + " || Address: " + address
                                + " || Location Name: " + locationName + " || Time: " + time + " || Staff Name: " + staffName + " || Phone: " + phone + " || Booking Address: " + bookingAddress;
                        arrayList1.add(value);

                        booking_info = new HashMap<String, String>();
                        booking_info = (HashMap<String, String>) snapshot_user_level.child("InfoBooking").getValue(genericTypeIndicator);

                        bookingList.add(booking_info);
                    }
                    arrayAdapter1.notifyDataSetChanged();
                }
                    else {
                    Log.w("Firebase", "No data found at this path");
                }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.e("Firebase", "onCancelled called: " + databaseError.getMessage());
    }
});
        btn_delele_selections.setOnClickListener(new View.OnClickListener() {
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
                    // Xoá trên firebase
                    TextView textView = view.findViewById(R.id.textView); // R.id.textView là id của TextView trong layout của hàng ListView
                    // Lấy giá trị của TextView
                    String text = textView.getText().toString();
                    for(DataSnapshot snapshot_user_level : dataSnapshot_for_button_del1.getChildren()) {
                        // Personal Information
                        String clientmail = snapshot_user_level.child("Personal Information").child("email").getValue(String.class);
                        String clientName = snapshot_user_level.child("Personal Information").child("name").getValue(String.class);

                        // InfoBooking
                        for(DataSnapshot snapshot_booking_level : snapshot_user_level.child("InfoBooking").getChildren()) {
                            String locationName = snapshot_booking_level.child("locationName").getValue(String.class);
                            String time = snapshot_booking_level.child("time").getValue(String.class);
                            String staffName = snapshot_booking_level.child("staffName").getValue(String.class);
                            String phone = snapshot_booking_level.child("phone").getValue(String.class);
                            String bookingAddress = snapshot_booking_level.child("address").getValue(String.class);

                            String value = "Email: " + clientmail + " || Name: " + clientName + " || Location Name: " + locationName + " || Time: " + time + " || Staff Name: " + staffName + " || Phone: " + phone + " || Booking Address: " + bookingAddress;

                            if(text.equals(value)){
                                FirebaseDatabase.getInstance().getReference().child("userID").child(snapshot_user_level.getKey()).child("InfoBooking").child(snapshot_booking_level.getKey()).removeValue();
                            }
                        }
                    }
                    // Xóa item tương ứng từ danh sách
                    arrayList1.remove(i);
                    // Cập nhật adapter
                    arrayAdapter1.notifyDataSetChanged();
                    Toast.makeText(booking.this, "Selected order was deleted successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });


        btn_delete_all.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Xóa tất cả các mục từ danh sách
            for(DataSnapshot snapshot_user_level : dataSnapshot_for_button_del1.getChildren()) {
                FirebaseDatabase.getInstance().getReference().child("userID").child(snapshot_user_level.getKey()).child("InfoBooking").removeValue();
            arrayList1.clear();
            // Cập nhật adapter
            arrayAdapter1.notifyDataSetChanged();
            Toast.makeText(booking.this, "Delete all orders successfully.", Toast.LENGTH_SHORT).show();
        }
    }

   });
}
}
