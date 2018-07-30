package com.app.developer.hostelry_management.feature.com.app.activities.supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.activities.order.preorder.PreorderListActivity;
import com.app.developer.hostelry_management.feature.com.app.activities.order.preorder.PreorderNewActivity;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;
import com.app.developer.hostelry_management.feature.com.app.utils.MenuItemsTextUpdater;
import com.app.developer.hostelry_management.feature.com.app.utils.Utils;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SupplierListActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.toolbar_first_option) {
            startActivity(new Intent(SupplierListActivity.this, SupplierNewActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basic_toolbar, menu);
        MenuItemsTextUpdater.updateTitle(menu.findItem(R.id.toolbar_first_option), getResources().getString(R.string.es_toolbar_createSupplier));
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Supplier> supplierList = null;
        try {
            supplierList = new SupplierAsyncTask(this).execute().get();
            Utils.orderAlphabeticallySupplier(supplierList);
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
                finish();
            }
        });
    }
}
