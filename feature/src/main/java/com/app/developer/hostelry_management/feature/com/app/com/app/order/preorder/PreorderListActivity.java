package com.app.developer.hostelry_management.feature.com.app.com.app.order.preorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;
import com.google.gson.Gson;

import java.util.List;

public class PreorderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_list);

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
