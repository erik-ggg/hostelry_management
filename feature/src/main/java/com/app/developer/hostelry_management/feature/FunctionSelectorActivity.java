package com.app.developer.hostelry_management.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.activities.order.OrdersGeneralOptionsActivity;
import com.app.developer.hostelry_management.feature.com.app.activities.product.ProductOptionsActivity;
import com.app.developer.hostelry_management.feature.com.app.activities.supplier.SupplierListActivity;
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
                Intent intent = new Intent(FunctionSelectorActivity.this, SupplierListActivity.class);
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

//      ***** DEVELOPING ONLY *****
        loadTestData();
    }

    private void loadTestData() {
        final SupplierDao supplierDao = AppDatabase.getAppDatabase(this).supplierDao();
        final ProductDao productDao = AppDatabase.getAppDatabase(this).productDao();
        final ProductEvolutionDao productEvolutionDao = AppDatabase.getAppDatabase(this).productEvolutionDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(getApplicationContext()).runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        Supplier supplier = new Supplier((long) 1, "Friocarne", 111111111);
                        Product product = new Product((long) 1, "Entrecot", supplier.getId());
                        ProductEvolution p1 = new ProductEvolution(product.getId(), new Date(), 5);
                        ProductEvolution p2 = new ProductEvolution(product.getId(), new Date(), 10);
                        ProductEvolution p3 = new ProductEvolution(product.getId(), new Date(), 15);
                        supplierDao.insert(supplier);
                        productDao.insert(product);
                        productEvolutionDao.insert(p1);

//                        productEvolutionDao.delete(p3);
//                        productEvolutionDao.delete(p2);
//                        productEvolutionDao.delete(p1);
//
//                        productDao.delete(product);
//                        supplierDao.delete(supplier);

                    }
                });

            }
        }).start();
    }
}
