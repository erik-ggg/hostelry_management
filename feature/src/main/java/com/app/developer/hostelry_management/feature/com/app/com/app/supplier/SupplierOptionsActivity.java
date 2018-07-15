package com.app.developer.hostelry_management.feature.com.app.com.app.supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.com.app.supplier.com.app.supplier.product.SupplierProductsActivity;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;
import com.google.gson.Gson;

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
    }
}
