package com.app.developer.hostelry_management.feature.com.app.activities.product;

import android.app.Activity;
import android.arch.persistence.room.util.StringUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;
import com.google.gson.Gson;

import java.util.Calendar;

public class ProductNewBySupplierActivity extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productPriceEditText;
    private TextView supplierNameTextView;
    private Supplier supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_new_by_supplier);

        supplier = new Gson().fromJson(getIntent().getStringExtra("supplier"), Supplier.class);

        productNameEditText = findViewById(R.id.productNewEditTextName);
        productPriceEditText = findViewById(R.id.productNewEditTextPrice);
        supplierNameTextView = findViewById(R.id.productNewBySupplierSupplier);
        supplierNameTextView.setText(supplier.getName());

        Button addButton = findViewById(R.id.productNewAccept);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isEmptyOrWhitespace(productNameEditText.getText().toString())
                            || isEmptyOrWhitespace(productPriceEditText.getText().toString())) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ProductNewBySupplierActivity.this,
                                            getResources().getString(R.string.es_emptyOrWhitespace), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            final AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                            database.runInTransaction(new Runnable() {
                                @Override
                                public void run() {
                                    Long productId = database.productDao().insert(new Product(productNameEditText.getText().toString(),
                                            supplier.getId()));
                                    database.productEvolutionDao().insert(new ProductEvolution(productId,
                                            Calendar.getInstance().getTime(), Double.valueOf(productPriceEditText.getText().toString())));
                                }
                            });
                            finish();
                        }

                    }
                }).start();
            }
        });
    }

    /**
     * Returns true if is empty or whitespace and returns false if not
     * @param text
     * @return
     */
    private boolean isEmptyOrWhitespace(String text) {
        if (text.isEmpty() || text.trim().isEmpty()) return true;
        return false;
    }
}
