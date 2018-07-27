package com.app.developer.hostelry_management.feature.com.app.activities.order.preorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Order;
import com.app.developer.hostelry_management.feature.com.app.model.OrderItems;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;
import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.utils.MenuItemsTextUpdater;
import com.app.developer.hostelry_management.feature.com.app.utils.ProductQuantity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PreorderSelectedActivity extends AppCompatActivity {

    private final static  String ITEMS_STRING = "Cantidad productos: ";
    private final static String TOTAL_STRING = "TOTAL: ";
    private Preorder preorder;
    private List<PreorderItems> preorderItems;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.toolbar_first_option) {
            closePreorder();
            return true;
        }
        if (i == R.id.toolbar_second_option) {
            addMoreProducts();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basic_toolbar, menu);
        MenuItemsTextUpdater.updateTitle(menu.findItem(R.id.toolbar_first_option), getResources().getString(R.string.es_toolbar_closePreorder));
        // set enable and visible the menu second option
        MenuItem secondMenuItem = menu.findItem(R.id.toolbar_second_option);
        secondMenuItem.setEnabled(true);
        secondMenuItem.setVisible(true);
        MenuItemsTextUpdater.updateTitle(secondMenuItem, getResources().getString(R.string.es_toolbar_preorder_addProducts));
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_selected);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preorder = new Gson().fromJson(getIntent().getStringExtra("preorder"), Preorder.class);

        final ListView preorderListView = findViewById(R.id.preorderSelectedListView);

        final TextView numberOfItemsTextView = findViewById(R.id.preorderSelectedNumberOfItemsTextView);

        final TextView totalTextView = findViewById(R.id.preorderSelectedTotalTextView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                preorderItems = database.preorderItemsDao().getByPreorderId(preorder.getId());
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
                        getProductsQuantity(preorderItems, database));
                preorderListView.setAdapter(adapter);
                numberOfItemsTextView.setText(ITEMS_STRING + preorder.getNumberOfItems());
                totalTextView.setText(TOTAL_STRING + preorder.getTotal());
            }
        }).start();
    }

    private void addMoreProducts() {
        Intent intent = new Intent(PreorderSelectedActivity.this, PreorderAddProductsActivity.class);
        intent.putExtra("preorder", getIntent().getStringExtra("preorder"));
        startActivity(intent);
    }

    private void closePreorder() {
        final AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    database.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            // crear order
                            database.orderDao().addOrder(new Order(preorder.getSupplierId(),
                                    preorder.getNumberOfItems(), preorder.getTotal(), preorder.getDate()));
                            // crear productos de dicha order
                            database.orderItemsDao().addAll(getOrderItems(preorderItems));
                            // borrar productos preorder
                            database.preorderItemsDao().deletePreorderItems(preorderItems);
                            // borrar preorder
                            database.preorderDao().deletePreorder(preorder);
                            closeAndRefresh();
                        }
                    });
                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.es_toast_database_error), Toast.LENGTH_LONG).show();
                }
            }
        }).start();
    }

    private void closeAndRefresh() {
        Intent refresh = new Intent(this, PreorderListActivity.class);
        startActivity(refresh);
        finish();
    }

    /**
     * Converts the preorder items into order items
     * @param preorderItems
     * @return the order items
     */
    private List<OrderItems> getOrderItems(List<PreorderItems> preorderItems) {
        List<OrderItems> orderItems = new ArrayList<>();
        for (PreorderItems item : preorderItems) {
            orderItems.add(new OrderItems(item.preorderId, item.getproductEvolutionId()));
        }
        return  orderItems;
    }

    private List<ProductQuantity> getProductsQuantity(List<PreorderItems> preorderItems, AppDatabase database) {
        List<ProductQuantity> products = new ArrayList<>();
        Map<Long, Integer> productsMap = new HashMap<>();
        for (PreorderItems items : preorderItems) {
            if (productsMap.containsKey(items.getproductEvolutionId())) {
                productsMap.put(items.getproductEvolutionId(), productsMap.get(items.getproductEvolutionId()) + 1);
            } else {
                productsMap.put(items.getproductEvolutionId(), 1);
            }
        }
        for (Long key : productsMap.keySet()) {
            products.add(new ProductQuantity(database.productDao().getProductById(key), productsMap.get(key)));
        }
        return products;
    }
}
