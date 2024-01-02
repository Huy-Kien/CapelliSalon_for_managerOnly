package com.example.capellisalon_app_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String value = FirebaseDatabase.getInstance().getReference().child("userID").child("5cQ62L3VHsgaiMTadSoRufikfhD3").child("Personal Information").child("Address").getKey().toString();
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
}