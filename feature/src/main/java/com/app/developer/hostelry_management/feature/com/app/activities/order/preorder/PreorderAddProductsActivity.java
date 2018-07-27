package com.app.developer.hostelry_management.feature.com.app.activities.order.preorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;
import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PreorderAddProductsActivity extends AppCompatActivity {

    private List<PreorderItems> preorderItemsList = new ArrayList<>();
    private Button confirmButton;
    private Button addProductButton;
    private Preorder preorder;
    private double total = 0;
    private Spinner productsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_add_products);

        preorder = new Gson().fromJson(getIntent().getStringExtra("preorder"), Preorder.class);
        total = preorder.getTotal();

        confirmButton = findViewById(R.id.preorderNewAddButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAndFinish();
            }
        });
        addProductButton = findViewById(R.id.preorderNewAddProductButton);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPreorderItem();
            }
        });
        productsSpinner = findViewById(R.id.preorderNewProductSpinner);
        loadSpinner();
    }

    private void addPreorderItem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Long productId = ((Product)productsSpinner.getSelectedItem()).getId();
                ProductEvolution productEvolution = AppDatabase.getAppDatabase(getApplicationContext()).productEvolutionDao()
                        .getLastModification(productId);
                // increment total value;
                total += productEvolution.getPrice();
                preorderItemsList.add(new PreorderItems(preorder.getId(), productEvolution.getId()));
            }
        }).start();
    }

    private void confirmAndFinish() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        database.preorderItemsDao().addAll(preorderItemsList);
                        preorder.setTotal(total);
                        preorder.setNumberOfItems(preorder.getNumberOfItems() + preorderItemsList.size());
                        database.preorderDao().updatePreorder(preorder);
                        // TODO: solo si funciona
                        Intent refresh = new Intent(PreorderAddProductsActivity.this, PreorderSelectedActivity.class);
                        refresh.putExtra("preorder", getIntent().getStringExtra("preorder"));
                        startActivity(refresh);
                        finish();
                    }
                });
            }
        }).start();
    }

    private void loadSpinner() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Product> products = AppDatabase.getAppDatabase(getApplicationContext()).productDao()
                    .getProductsBySupplier(preorder.getSupplierId());
                final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
                        products);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        productsSpinner.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }
}
