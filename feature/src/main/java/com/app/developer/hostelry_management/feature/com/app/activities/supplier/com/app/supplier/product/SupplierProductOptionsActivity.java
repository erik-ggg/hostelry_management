package com.app.developer.hostelry_management.feature.com.app.activities.supplier.com.app.supplier.product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.developer.hostelry_management.feature.R;

public class SupplierProductOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_product_options);

        Button graphic = findViewById(R.id.supplierProductOptionsGraphic);
        graphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupplierProductOptionsActivity.this,
                        SupplierProductGraphicActivity.class);
                intent.putExtra("product", getIntent().getStringExtra("product"));
                startActivity(intent);
            }
        });

        Button updateButton = findViewById(R.id.supplierProductOptionsUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupplierProductOptionsActivity.this,
                        SupplierProductUpdateActivity.class);
                intent.putExtra("product", getIntent().getStringExtra("product"));
                startActivity(intent);
            }
        });
    }
}
