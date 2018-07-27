package com.app.developer.hostelry_management.feature.com.app.activities.order.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Order;

import java.util.List;

public class OrderListActivity extends AppCompatActivity {

    private ListView ordersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        ordersListView = findViewById(R.id.orderListListView);
        loadOrders();
    }

    private void loadOrders() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                List<Order> orders = database.orderDao().getAll();

                ArrayAdapter adapter = new ArrayAdapter(OrderListActivity.this,
                        android.R.layout.simple_list_item_1, orders);
                ordersListView.setAdapter(adapter);
            }
        }).start();
    }
}
