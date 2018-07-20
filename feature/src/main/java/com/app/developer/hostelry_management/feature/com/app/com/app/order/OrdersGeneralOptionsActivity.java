package com.app.developer.hostelry_management.feature.com.app.com.app.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.com.app.order.order.OrderOptionsActivity;
import com.app.developer.hostelry_management.feature.com.app.com.app.order.preorder.PreorderOptionsActivity;

public class OrdersGeneralOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_general_options);

        Button preorders = findViewById(R.id.ordersGeneralPreorderButton);
        preorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrdersGeneralOptionsActivity.this, PreorderOptionsActivity.class));
            }
        });
        Button orders = findViewById(R.id.ordersGeneralOrderButton);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrdersGeneralOptionsActivity.this, OrderOptionsActivity.class));
            }
        });
    }
}
