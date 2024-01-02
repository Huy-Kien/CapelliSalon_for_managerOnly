package com.example.capellisalon_app_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button btn_booking = findViewById(R.id.home_btn_booking);
        Button btn_shopping = findViewById(R.id.home_btn_shopping);
        View.OnClickListener listenerBooking = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), booking.class);
                startActivity(intent);
            }
        };
        View.OnClickListener listenerShopping = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), shopping.class);
                startActivity(intent);
            }
        };
        btn_booking.setOnClickListener(listenerBooking);
        btn_shopping.setOnClickListener(listenerShopping);
    }
}
