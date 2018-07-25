package com.app.developer.hostelry_management.feature.com.app.activities.supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.activities.supplier.com.app.supplier.product.SupplierProductsActivity;

public class SupplierOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_options);

        final String stringSupplier = getIntent().getStringExtra("supplier");

        Button productsButton = findViewById(R.id.supplierOptionsButton);
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupplierOptionsActivity.this, SupplierProductsActivity.class);
                intent.putExtra("supplier", stringSupplier);
                startActivity(intent);
            }
        });

        Button editButton = findViewById(R.id.supplierOptionsEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupplierOptionsActivity.this, SupplierProductsActivity.class);
                intent.putExtra("supplier", stringSupplier);
                startActivity(intent);
            }
        });
    }
}
