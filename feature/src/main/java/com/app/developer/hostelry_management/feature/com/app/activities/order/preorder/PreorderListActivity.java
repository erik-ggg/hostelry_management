package com.app.developer.hostelry_management.feature.com.app.activities.order.preorder;

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
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;
import com.app.developer.hostelry_management.feature.com.app.utils.MenuItemsTextUpdater;
import com.google.gson.Gson;

import java.util.List;

public class PreorderListActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.toolbar_first_option) {
            startActivity(new Intent(PreorderListActivity.this, PreorderNewActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basic_toolbar, menu);
        MenuItemsTextUpdater.updateTitle(menu.findItem(R.id.toolbar_first_option), getResources().getString(R.string.es_toolbar_createPreorder));
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final ListView preorderListView = findViewById(R.id.preorderListView);
        preorderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PreorderListActivity.this, PreorderSelectedActivity.class);
                intent.putExtra("preorder", new Gson().toJson(adapterView.getItemAtPosition(i)));
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Preorder> preorderList = AppDatabase.getAppDatabase(getApplicationContext()).preorderDao()
                        .getAll();
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
                        preorderList);
                preorderListView.setAdapter(adapter);
            }
        }).start();
    }
}
