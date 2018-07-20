package com.app.developer.hostelry_management.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.com.app.order.OrdersGeneralOptionsActivity;
import com.app.developer.hostelry_management.feature.com.app.com.app.product.ProductOptionsActivity;
import com.app.developer.hostelry_management.feature.com.app.com.app.supplier.SuppliersOptionsActivity;
import com.app.developer.hostelry_management.feature.com.app.dao.ProductDao;
import com.app.developer.hostelry_management.feature.com.app.dao.ProductEvolutionDao;
import com.app.developer.hostelry_management.feature.com.app.dao.SupplierDao;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;

import java.util.Date;

public class FunctionSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_selector);

        final Button supplierButton = findViewById(R.id.supplierButton);
        supplierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FunctionSelectorActivity.this, SuppliersOptionsActivity.class);
                startActivity(intent);
            }
        });

        Button productButton = findViewById(R.id.productsButton);
        productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FunctionSelectorActivity.this, ProductOptionsActivity.class));
            }
        });

        Button orderButton = findViewById(R.id.ordersButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FunctionSelectorActivity.this, OrdersGeneralOptionsActivity.class));
            }
        });

        // ***** DEVELOPING ONLY *****
        loadTestData();
    }

    private void loadTestData() {
        final SupplierDao supplierDao = AppDatabase.getAppDatabase(this).supplierDao();
        final ProductDao productDao = AppDatabase.getAppDatabase(this).productDao();
        final ProductEvolutionDao productEvolutionDao = AppDatabase.getAppDatabase(this).productEvolutionDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Supplier supplier = new Supplier((long) 1, "Friocarne", 111111111);
                Product product = new Product((long) 1, "Entrecot", supplier.getId());
                supplierDao.insert(supplier);
                productDao.insert(product);
                productEvolutionDao.insert(new ProductEvolution(product.getId(), new Date(), 5));
                productEvolutionDao.insert(new ProductEvolution(product.getId(), new Date(), 10));
                productEvolutionDao.insert(new ProductEvolution(product.getId(), new Date(), 15));

            }
        }).start();
    }
}
