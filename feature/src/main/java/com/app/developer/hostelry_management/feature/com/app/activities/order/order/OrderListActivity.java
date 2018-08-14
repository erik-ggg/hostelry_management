package com.app.developer.hostelry_management.feature.com.app.activities.order.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.activities.order.preorder.PreorderListActivity;
import com.app.developer.hostelry_management.feature.com.app.model.Order;
import com.app.developer.hostelry_management.feature.com.app.utils.DataClasses.OrderData;
import com.app.developer.hostelry_management.feature.com.app.utils.DataClasses.PreorderData;
import com.app.developer.hostelry_management.feature.com.app.utils.DataClasses.ProductQuantity;
import com.app.developer.hostelry_management.feature.com.app.utils.Printer.MailPrinter;
import com.app.developer.hostelry_management.feature.com.app.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {

    private ListView ordersListView;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        ordersListView = findViewById(R.id.orderListListView);
        ordersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                setUpPopupMenu(adapterView, view, i);
                return true;
            }
        });
        totalTextView = findViewById(R.id.orderListListTotalTextView);
        loadOrders();
    }

    private void setUpPopupMenu(AdapterView<?> adapterView, View view, int i) {
        PopupMenu popupMenu = new PopupMenu(OrderListActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.basic_popup, popupMenu.getMenu());
        MenuItem menuItem = popupMenu.getMenu().findItem(R.id.popup_firstOption);
        menuItem.setTitle(getResources().getString(R.string.es_sendMail));
        final int position = i;
        final OrderData orderData = (OrderData) adapterView.getItemAtPosition(i);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.popup_firstOption) {
                    AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                    String date = new SimpleDateFormat("dd/MM/yyyy").format(orderData.getOrder().getDate());
                    List<ProductQuantity> products = Utils.getProductsQuantityByOrder(
                            database.orderItemsDao().getByOrderId(orderData.getOrder().getId()), database);
                    MailPrinter mailPrinter = new MailPrinter(date, products);
                    System.out.println(mailPrinter.print());
                }
                return true;
            }
        });

        popupMenu.show();
    }



    private void loadOrders() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                List<Order> orders = database.orderDao().getAll();

                calculateOrderTotal(orders);

                ArrayAdapter adapter = new ArrayAdapter(OrderListActivity.this,
                        android.R.layout.simple_list_item_1, orders);
                ordersListView.setAdapter(adapter);
            }
        }).start();
    }

    private void calculateOrderTotal(List<Order> orders) {
        double total = 0;
        for (Order order : orders) {
            total += order.getTotal();
        }
        totalTextView.setText(String.valueOf(total));
    }
}
