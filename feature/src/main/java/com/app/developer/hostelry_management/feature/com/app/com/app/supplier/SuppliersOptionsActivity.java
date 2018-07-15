package com.app.developer.hostelry_management.feature.com.app.com.app.supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.developer.hostelry_management.feature.R;

public class SuppliersOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers_options);

        Button suppliersList = findViewById(R.id.suppliersOptionsList);
        suppliersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuppliersOptionsActivity.this, SupplierListActivity.class));
            }
        });
        Button suppliersNew = findViewById(R.id.suppliersOptionsNew);
        suppliersNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuppliersOptionsActivity.this, SupplierNewActivity.class));
            }
        });
    }
}