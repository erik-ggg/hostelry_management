package com.app.developer.hostelry_management.feature.com.app.activities.supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.dao.SupplierDao;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;
import com.google.gson.Gson;

public class SupplierNewActivity extends AppCompatActivity {

    private String stringSupplier;
    private Supplier supplier;

    private Button addSupplier;

    private EditText supplierEmail;
    private EditText supplierName;
    private EditText supplierPhonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_new);

        supplierEmail = findViewById(R.id.supplierNewEmailEditText);
        supplierName = findViewById(R.id.supplierNewNameEditText);
        supplierPhonenumber = findViewById(R.id.supplierNewPhonenumberEditText);
        addSupplier = findViewById(R.id.supplierNewAccept);
        addSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        addOrUpdateSupplier();
                    }
                }).start();
            }
        });

        stringSupplier = getIntent().getStringExtra("supplier");
        setUpEditText(stringSupplier);
    }

    private void addOrUpdateSupplier() {
        SupplierDao supplierDao = AppDatabase.getAppDatabase(getApplicationContext()).supplierDao();
        if (stringSupplier != null) {
            if (supplierName.getText().toString().length() > 0) {
                supplier.setName(supplierName.getText().toString().trim());
                supplier.setPhonenumber(Integer.valueOf(supplierPhonenumber.getText().toString()));
                supplier.setEmail(supplierEmail.getText().toString().trim());
                supplierDao.update(supplier);
            }
        } else {
            supplierDao.insert(new Supplier(supplierName.getText().toString().trim(),
                    Integer.valueOf(supplierPhonenumber.getText().toString())));
        }
        finishAndRefresh();
    }

    /**
     * Fills the edit text if the activity is in edit mode
     * The activity is in edit mode if the stringSupplier is not null
     */
    private void setUpEditText(String stringSupplier) {
        if (stringSupplier != null) {
            supplier = new Gson().fromJson(stringSupplier, Supplier.class);
            supplierName.setText(supplier.getName());
            supplierPhonenumber.setText(String.valueOf(supplier.getPhonenumber()));
            supplierEmail.setText(supplier.getEmail());
            // changes the create button text to update
            addSupplier.setText(getResources().getString(R.string.es_update));
        }
    }

    /**
     * Finish the activity and refresh the supplier list
     */
    private void finishAndRefresh() {
        startActivity(new Intent(SupplierNewActivity.this, SupplierListActivity.class));
        finish();
    }
}
