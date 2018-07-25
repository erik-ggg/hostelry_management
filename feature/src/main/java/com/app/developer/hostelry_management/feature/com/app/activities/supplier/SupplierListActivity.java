package com.app.developer.hostelry_management.feature.com.app.activities.supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SupplierListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_list);

        List<Supplier> supplierList = null;
        try {
            supplierList = new SupplierAsyncTask(this).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<Supplier> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, supplierList);

        ListView listView = findViewById(R.id.listViewSupplier);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Supplier supplier = (Supplier) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(SupplierListActivity.this,
                        SupplierOptionsActivity.class);
                intent.putExtra("supplier", new Gson().toJson(supplier));
                startActivity(intent);
            }
        });
    }
}
