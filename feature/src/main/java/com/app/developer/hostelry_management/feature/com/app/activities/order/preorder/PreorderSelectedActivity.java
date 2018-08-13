package com.app.developer.hostelry_management.feature.com.app.activities.order.preorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Order;
import com.app.developer.hostelry_management.feature.com.app.model.OrderItems;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;
import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;
import com.app.developer.hostelry_management.feature.com.app.utils.MenuItemsTextUpdater;
import com.app.developer.hostelry_management.feature.com.app.utils.DataClasses.ProductQuantity;
import com.app.developer.hostelry_management.feature.com.app.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreorderSelectedActivity extends AppCompatActivity {

    private final static  String ITEMS_STRING = "Cantidad productos: ";
    private final static String TOTAL_STRING = "TOTAL: ";
    private Preorder preorder;
    private List<PreorderItems> preorderItems;

    ListView preorderListView;
    ArrayAdapter adapter;

    TextView numberOfItemsTextView;
    TextView totalTextView;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PreorderSelectedActivity.this, PreorderListActivity.class);
        intent.putExtra("preorder", new Gson().toJson(preorder, Preorder.class));
        startActivity(intent);
        finish();
    }

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

        preorderListView = findViewById(R.id.preorderSelectedListView);
        preorderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                setUpPopupMenu(adapterView, view, i);
                return true;
            }
        });

        numberOfItemsTextView = findViewById(R.id.preorderSelectedNumberOfItemsTextView);

        totalTextView = findViewById(R.id.preorderSelectedTotalTextView);

        setUpViewItems(preorderListView, numberOfItemsTextView, totalTextView);
    }

    private void setUpPopupMenu(AdapterView<?> adapterView, View view, int i) {
        PopupMenu popupMenu = new PopupMenu(PreorderSelectedActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.basic_popup, popupMenu.getMenu());
        MenuItem menuItem = popupMenu.getMenu().findItem(R.id.popup_firstOption);
        menuItem.setTitle(getResources().getString(R.string.es_deletePreorderItem));
        final int position = i;
        final ProductQuantity product = (ProductQuantity) adapterView.getItemAtPosition(i);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.popup_firstOption) {
                    deleteSelectedProduct(product, position);
                }
                return true;
            }
        });

        popupMenu.show();
    }

    private void deleteSelectedProduct(final ProductQuantity product, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        database.preorderItemsDao().deleteItemByPreorderIdAndProductEvolutionId(preorder.getId(), product.getProductEvolutionId());
                        updatePreorder(database, product);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.remove(adapter.getItem(position));
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        }).start();
    }

    private void updatePreorder(AppDatabase database, ProductQuantity product) {
        preorder.setNumberOfItems(preorder.getNumberOfItems() - product.getQuantity());
        preorder.setTotal(preorder.getTotal() - product.getTotal());
        database.preorderDao().updatePreorder(preorder);
    }

    private void setUpViewItems(final ListView preorderListView, final TextView numberOfItemsTextView, final TextView totalTextView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                preorderItems = database.preorderItemsDao().getByPreorderId(preorder.getId());
                List<ProductQuantity> preorderProducts = getProductsQuantity(preorderItems, database);
                Utils.orderAlphabeticallyProductQuantity(preorderProducts);
                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
                        preorderProducts);
                preorderListView.setAdapter(adapter);
                numberOfItemsTextView.setText(ITEMS_STRING + preorder.getNumberOfItems());
                totalTextView.setText(TOTAL_STRING + new DecimalFormat("#.##").format(preorder.getTotal()));
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
                            Long orderId = database.orderDao().addOrder(new Order(preorder.getSupplierId(),
                                    preorder.getNumberOfItems(), preorder.getTotal(), preorder.getDate()));
                            // crear productos de dicha order
                            database.orderItemsDao().addAll(getOrderItems(orderId, preorderItems));
                            // borrar productos preorder
                            database.preorderItemsDao().deletePreorderItems(preorderItems);
                            // borrar preorder
                            database.preorderDao().deletePreorder(preorder);
                            closeAndRefresh();
                        }
                    });
                } catch (Exception e) {
                    Log.d("DB", e.getMessage());
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
     *
     * @param orderId
     * @param preorderItems
     * @return the order items
     */
    private List<OrderItems> getOrderItems(Long orderId, List<PreorderItems> preorderItems) {
        List<OrderItems> orderItems = new ArrayList<>();
        for (PreorderItems item : preorderItems) {
            orderItems.add(new OrderItems(orderId, item.getProductEvolutionId()));
        }
        return  orderItems;
    }

    /**
     * Obtains, converts and returns the information of the preorder items into a more suitable data structure
     * @param preorderItems
     * @param database
     * @return
     */
    private List<ProductQuantity> getProductsQuantity(List<PreorderItems> preorderItems, AppDatabase database) {
        List<ProductQuantity> products = new ArrayList<>();
        Map<Long, Integer> productsMap = new HashMap<>();
        for (PreorderItems items : preorderItems) {
            if (productsMap.containsKey(items.getProductEvolutionId())) {
                productsMap.put(items.getProductEvolutionId(), productsMap.get(items.getProductEvolutionId()) + 1);
            } else {
                productsMap.put(items.getProductEvolutionId(), 1);
            }
        }
        for (Long key : productsMap.keySet()) {
            int quantity = productsMap.get(key);
            double total = database.productEvolutionDao().getById(key).getPrice() * quantity;
            products.add(new ProductQuantity(database.productDao().getProductById(key), key, quantity, total));
        }
        return products;
    }
}
