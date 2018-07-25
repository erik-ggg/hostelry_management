package com.app.developer.hostelry_management.feature.com.app.activities.supplier.com.app.supplier.product;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;
import com.google.gson.Gson;

import java.util.Calendar;

public class SupplierProductUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_product_update);

        final Product product = new Gson()
                .fromJson(getIntent().getStringExtra("product"), Product.class);

        final EditText editTextPrice = findViewById(R.id.supplierProductPriceEditText);
        final Button buttonAdd = findViewById(R.id.productUpdateAccept);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: toast with alert (operation cannot be reverted)
                        double price = Double.valueOf(editTextPrice.getText().toString());
                        AppDatabase.getAppDatabase(getApplicationContext()).productEvolutionDao()
                                .insert(new ProductEvolution(product.getId(), Calendar.getInstance().getTime(), price));
                        finish();
                    }
                }).start();
            }
        });
    }
}
