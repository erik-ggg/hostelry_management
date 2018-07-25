package com.app.developer.hostelry_management.feature.com.app.activities.order.preorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;
import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_selected);

        final Preorder preorder = new Gson().fromJson(getIntent().getStringExtra("preorder"), Preorder.class);

        final ListView preorderListView = findViewById(R.id.preorderSelectedListView);

        final TextView numberOfItemsTextView = findViewById(R.id.preorderSelectedNumberOfItemsTextView);

        final TextView totalTextView = findViewById(R.id.preorderSelectedTotalTextView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                List<PreorderItems> preorderItems = database.preorderItemsDao().getByPreorderId(preorder.getId());
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
                        getProductsQuantity(preorderItems, database));
                preorderListView.setAdapter(adapter);
                numberOfItemsTextView.setText(ITEMS_STRING + preorder.getNumberOfItems());
                totalTextView.setText(TOTAL_STRING + preorder.getTotal());
            }
        }).start();
    }

    private List<ProductQuantity> getProductsQuantity(List<PreorderItems> preorderItems, AppDatabase database) {
        List<ProductQuantity> products = new ArrayList<>();
        Map<Long, Integer> productsMap = new HashMap<>();
        for (PreorderItems items : preorderItems) {
            if (productsMap.containsKey(items.productId)) {
                productsMap.put(items.productId, productsMap.get(items.productId) + 1);
            } else {
                productsMap.put(items.productId, 1);
            }
        }
        for (Long key : productsMap.keySet()) {
            products.add(new ProductQuantity(database.productDao().getProductById(key), productsMap.get(key)));
        }
        return products;
    }
}
