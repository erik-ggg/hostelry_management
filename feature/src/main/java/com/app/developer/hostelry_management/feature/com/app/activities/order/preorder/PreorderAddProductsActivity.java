package com.app.developer.hostelry_management.feature.com.app.activities.order.preorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    private Button confirmButton;
    private Button addProductButton;
    private EditText productQuantityEditText;
    private Spinner productsSpinner;

    private List<PreorderItems> preorderItemsList = new ArrayList<>();
    private Preorder preorder;
    private double total = 0;
    private double numberOfOItems = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_add_products);

        preorder = new Gson().fromJson(getIntent().getStringExtra("preorder"), Preorder.class);

        productQuantityEditText = findViewById(R.id.preorderNewQuantityEditText);

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
                double quantity = Double.valueOf(productQuantityEditText.getText().toString());
                numberOfOItems += Double.valueOf(productQuantityEditText.getText().toString());
                Long productId = ((Product)productsSpinner.getSelectedItem()).getId();
                ProductEvolution productEvolution = AppDatabase.getAppDatabase(getApplicationContext()).productEvolutionDao()
                        .getLastModification(productId);
                quantity = checkAndAddDecimalProduct(quantity, productEvolution);
                // increment total value;
                total += productEvolution.getPrice();
                for (int i = 0; i < quantity; i++) {
                    preorderItemsList.add(new PreorderItems(preorder.getId(), productEvolution.getId()));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.es_productAdded), Toast.LENGTH_SHORT).show();
                    }
                });
                productQuantityEditText.setText("1");
            }
        }).start();
    }

    private double checkAndAddDecimalProduct(double quantity, ProductEvolution productEvolution) {
        if (quantity % 1 != 0) {
            double decimalProductValue = (quantity % 1) * productEvolution.getPrice();
            total += decimalProductValue;
            return quantity - 1;
        } else {
            return quantity;
        }
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
                        preorder.setTotal(preorder.getTotal() + total);
                        preorder.setNumberOfItems(preorder.getNumberOfItems() + numberOfOItems);
                        database.preorderDao().updatePreorder(preorder);
                        // TODO: solo si funciona
                        Intent refresh = new Intent(PreorderAddProductsActivity.this, PreorderSelectedActivity.class);
                        refresh.putExtra("preorder", new Gson().toJson(preorder, Preorder.class));
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
