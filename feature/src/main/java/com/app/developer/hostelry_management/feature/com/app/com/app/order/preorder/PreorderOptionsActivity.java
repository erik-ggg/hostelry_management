package com.app.developer.hostelry_management.feature.com.app.com.app.order.preorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.developer.hostelry_management.feature.R;

public class PreorderOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_options);

        Button preorderList = findViewById(R.id.preorderOptionsListButton);
        preorderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreorderOptionsActivity.this, PreorderListActivity.class));
            }
        });
        Button preorderNew = findViewById(R.id.preorderOptionsNewButton);
        preorderNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreorderOptionsActivity.this, PreorderNewActivity.class));
            }
        });
    }
}
