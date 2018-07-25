package com.app.developer.hostelry_management.feature.com.app.activities.product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.developer.hostelry_management.feature.R;

public class ProductOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_options);

        Button newProductButton = findViewById(R.id.buttonProductAddProduct);
        newProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductOptionsActivity.this, ProductNewActivity.class));
            }
        });
    }
}
