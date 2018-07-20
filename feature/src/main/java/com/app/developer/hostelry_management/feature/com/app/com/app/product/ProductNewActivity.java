package com.app.developer.hostelry_management.feature.com.app.com.app.product;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_new);

        final EditText productName = findViewById(R.id.productNewEditTextName);
        final EditText productPrice = findViewById(R.id.productNewEditTextPrice);
        final Spinner suppliersSpinner = findViewById(R.id.productNewSupplierSpinner);
        Button cancelButton = findViewById(R.id.productNewCancel);
        Button addButton = findViewById(R.id.productNewAccept);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: if insertion correct do the next if not rollback
                        Long productId = AppDatabase.getAppDatabase(getApplicationContext()).productDao()
                                .insert(new Product(
                                    productName.getText().toString(),
                                    ((Supplier)suppliersSpinner.getSelectedItem()).getId()
                                ));
                        if (productId > 0) {
                            AppDatabase.getAppDatabase(getApplicationContext()).productEvolutionDao()
                                    .insert(new ProductEvolution(productId, Calendar.getInstance().getTime(),
                                            ((Supplier)suppliersSpinner.getSelectedItem()).getId()));
                        }
                        finish();
                    }
                }).start();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                // cargar proveedores
                List<Supplier> suppliers = AppDatabase.getAppDatabase(getApplicationContext())
                        .supplierDao().getAll();
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_spinner_item, suppliers);
                suppliersSpinner.setAdapter(adapter);
            }
        }).start();
    }
}
