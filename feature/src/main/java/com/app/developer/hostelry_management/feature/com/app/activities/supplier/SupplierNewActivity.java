package com.app.developer.hostelry_management.feature.com.app.activities.supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;

public class SupplierNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_new);

        final EditText supplierName = findViewById(R.id.supplierNewNameEditText);
        final EditText supplierPhonenumber = findViewById(R.id.supplierNewPhonenumberEditText);
        Button addSupplier = findViewById(R.id.supplierNewAccept);
        addSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getAppDatabase(getApplicationContext()).supplierDao()
                                .insert(new Supplier(supplierName.getText().toString().trim(),
                                        Integer.valueOf(supplierPhonenumber.getText().toString())));
                        finishAndRefresh();
                    }
                }).start();
            }
        });
    }

    /**
     * Finish the activity and refresh the supplier list
     */
    private void finishAndRefresh() {
        startActivity(new Intent(SupplierNewActivity.this, SupplierListActivity.class));
        finish();
    }
}
