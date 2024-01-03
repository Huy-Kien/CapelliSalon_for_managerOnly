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

import androidx.activity.result.ActivityResultLauncher;
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
import java.util.List;

public class shopping extends AppCompatActivity {

    private ListView listView ;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private Button btn_del_sel;
    private Button btn_del_all;
    Intent intent = new Intent();
    private ArrayList<HashMap<String, String>> productList;
    private HashMap<String,String> product_info;
    String value_onItem, id_product_on_item, mail_onitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping);
        GenericTypeIndicator<HashMap<String,String>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String,String>>() {};
        listView = findViewById(R.id.lv_shopping);
        btn_del_sel = findViewById(R.id.btn_shopping_del_sel);
        btn_del_all = findViewById(R.id.btn_shopping_delALL);
        arrayList = new ArrayList<String>();
        productList = new ArrayList<HashMap<String, String>>();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                intent = new Intent(shopping.this, detail_information_shopping.class);
                for(HashMap<String,String> hashmap : productList) {

                    if ( hashmap.get("email") != null && hashmap.get("id")!= null) {
                        mail_onitem = hashmap.get("email").toString();
                        id_product_on_item = hashmap.get("id").toString();
                         value_onItem = "Email: " + mail_onitem + " || Product ID: " + id_product_on_item;
                        Toast.makeText(shopping.this, "OK Hashmap not null.", Toast.LENGTH_SHORT).show();
                    }
                    if (value_onItem.equals(selectedItem)) {
                            intent.putExtra("product_info", hashmap);
                    }
                }
                startActivity(intent);
                Toast.makeText(shopping.this, "client: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Orders");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("Firebase", "DataSnapshot exists: " + dataSnapshot.getValue().toString());
                    for(DataSnapshot snapshot_user_level : dataSnapshot.getChildren()) {
                        for(DataSnapshot snapshot_productID_level : snapshot_user_level.getChildren()) {

                            String clientmail = snapshot_productID_level.child("email").getValue(String.class);
                            String clientName = snapshot_productID_level.child("name").getValue(String.class);
                            String productID = snapshot_productID_level.child("id").getValue(String.class);
                            String value = "Email: " + clientmail + " || Product ID: " + productID;
                            arrayList.add(value);

                            product_info =  new HashMap<String,String>();
                            product_info = (HashMap<String,String>)snapshot_productID_level.getValue(genericTypeIndicator);

                            productList.add(product_info);
//                            Toast.makeText(shopping.this, productList.get(0).get("id").toString(), Toast.LENGTH_SHORT).show();

                        }
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
