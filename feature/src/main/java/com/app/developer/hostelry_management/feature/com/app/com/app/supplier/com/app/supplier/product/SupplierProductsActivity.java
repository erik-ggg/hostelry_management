package com.app.developer.hostelry_management.feature.com.app.com.app.supplier.com.app.supplier.product;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.com.app.supplier.SupplierListActivity;
import com.app.developer.hostelry_management.feature.com.app.com.app.supplier.SupplierOptionsActivity;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;
import com.google.gson.Gson;

import java.util.List;

public class SupplierProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_products);

        final Supplier supplier = new Gson().fromJson(getIntent().getStringExtra("supplier"),
                Supplier.class);


        final ListView productsListView = findViewById(R.id.supplierProducts);
        final Context context = getApplicationContext();

        final List<Product>[] products = new List[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Product> products = AppDatabase.getAppDatabase(
                        getApplicationContext())
                        .productDao().getProductsBySupplier(supplier.getId());

                ArrayAdapter adapter = new ArrayAdapter(
                        context, android.R.layout.simple_list_item_1, products);
                productsListView.setAdapter(adapter);
            }
        }).start();
        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = (Product) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(SupplierProductsActivity.this,
                        SupplierProductOptionsActivity.class);
                intent.putExtra("product", new Gson().toJson(product));
                startActivity(intent);
            }
        });
    }
}
