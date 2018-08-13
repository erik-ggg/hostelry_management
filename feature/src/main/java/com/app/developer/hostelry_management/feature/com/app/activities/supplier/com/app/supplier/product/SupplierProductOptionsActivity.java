package com.app.developer.hostelry_management.feature.com.app.activities.supplier.com.app.supplier.product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class SupplierProductOptionsActivity extends AppCompatActivity {

    private TextView productTitleTextView;

    private double price;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_product_options);

        product = new Gson().fromJson(getIntent().getStringExtra("product"), Product.class);

        productTitleTextView = findViewById(R.id.supplierProductOptionsTitleTextView);
        try {
            getPrice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        productTitleTextView.setText(String.format("Producto: %s, precio: %s", product.getName()
                , new DecimalFormat("#.##").format( price)));

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

    private void getPrice() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                price = AppDatabase.getAppDatabase(getApplicationContext()).productEvolutionDao()
                        .getLastModification(product.getId()).getPrice();
            }
        });
        t.start();
        t.join();
    }
}
