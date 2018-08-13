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

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;
import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;
import com.app.developer.hostelry_management.feature.com.app.utils.DataClasses.PreorderData;
import com.app.developer.hostelry_management.feature.com.app.utils.DataClasses.ProductQuantity;
import com.app.developer.hostelry_management.feature.com.app.utils.MenuItemsTextUpdater;
import com.app.developer.hostelry_management.feature.com.app.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PreorderListActivity extends AppCompatActivity {

    private ListView preorderListView;
    private ArrayAdapter adapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.toolbar_first_option) {
            startActivity(new Intent(PreorderListActivity.this, PreorderNewActivity.class));
            finish();
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



        preorderListView = findViewById(R.id.preorderListView);
        preorderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PreorderListActivity.this, PreorderSelectedActivity.class);
                PreorderData preorderData = (PreorderData) adapterView.getItemAtPosition(i);
                intent.putExtra("preorder", new Gson().toJson(preorderData.getPreorder()));
                startActivity(intent);
                finish();
            }
        });
        preorderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                setUpPopupMenu(adapterView, view, i);
                return true;
            }
        });

        setUpListView();
    }

    private void setUpPopupMenu(AdapterView<?> adapterView, View view, int i) {
        PopupMenu popupMenu = new PopupMenu(PreorderListActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.basic_popup, popupMenu.getMenu());
        MenuItem menuItem = popupMenu.getMenu().findItem(R.id.popup_firstOption);
        menuItem.setTitle(getResources().getString(R.string.es_delete));
        final int position = i;
        final PreorderData preorderData = (PreorderData) adapterView.getItemAtPosition(i);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.popup_firstOption) {
                    deleteSelectedPreorder(preorderData.getPreorder(), position);
                }
                return true;
            }
        });

        popupMenu.show();
    }

    private void deleteSelectedPreorder(final Preorder preorder, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                try {
                    database.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            List<PreorderItems> preorderItems = database.preorderItemsDao().getByPreorderId(preorder.getId());
                            for (PreorderItems item : preorderItems) {
                                database.preorderItemsDao().deletePreorderItem(item);
                            }
                            database.preorderDao().deletePreorder(preorder);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.remove(adapter.getItem(position));
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    Log.d("DB_exception", e.getMessage());
                }
            }
        }).start();
    }

    private void setUpListView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());
                List<PreorderData> preorderData = new ArrayList<>();
                List<Preorder> preorderList = database.preorderDao()
                        .getAll();
                for (Preorder preorder : preorderList) {
                    Supplier supplier = database.supplierDao().getById(preorder.getSupplierId());
                    preorderData.add(new PreorderData(supplier.getName(), preorder));
                }
                Utils.orderPreorderbyDate(preorderData);
                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
                        preorderData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        preorderListView.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }
}
