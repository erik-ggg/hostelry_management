package com.app.developer.hostelry_management.feature.com.app.com.app.order.preorder;

import android.arch.persistence.room.RoomDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.dao.ProductDao;
import com.app.developer.hostelry_management.feature.com.app.dao.ProductEvolutionDao;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;
import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PreorderNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_new);

        final List<Supplier>[] suppliers = new List[1];
        final List<Product> productList = new ArrayList<>();

        final Spinner productSpinner = findViewById(R.id.preorderNewProductSpinner);
        Button addProduct = findViewById(R.id.preorderNewAddProductButton);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productList.add((Product) productSpinner.getSelectedItem());
            }
        });

        final Spinner supplierSpinner = findViewById(R.id.preorderNewSupplierSpinner);
        supplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final ArrayAdapter<Product> productsAdapter = new ArrayAdapter<>(
                                getApplicationContext(), android.R.layout.simple_list_item_1,
                                AppDatabase.getAppDatabase(getApplicationContext()).productDao().getProductsBySupplier(
                                        ((Supplier)supplierSpinner.getSelectedItem()).getId())
                        );
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                productSpinner.setAdapter(productsAdapter);
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                suppliers[0] = AppDatabase.getAppDatabase(getApplicationContext()).supplierDao().getAll();
                ArrayAdapter<Supplier> suppliersAdapter = new ArrayAdapter<Supplier>(getApplicationContext()
                        , android.R.layout.simple_list_item_1, suppliers[0]);
                supplierSpinner.setAdapter(suppliersAdapter);
            }
        }).start();

        final Button finishOrder = findViewById(R.id.preorderNewAddButton);
        finishOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.runInTransaction(new Runnable() {
                            @Override
                            public void run() {
                                // calculamos informacion preorder
                                List<PreorderItems> preorderItems = new ArrayList<>();
                                double total = 0;
                                for (Product product : productList) {
                                    total += appDatabase.productEvolutionDao().getLastModification(product.getId()).getPrice();
                                }
                                // insertar el preorder
                                Long preorderId = appDatabase.preorderDao().addPreorder(new Preorder(
                                        ((Supplier)supplierSpinner.getSelectedItem()).getId(),
                                        productList.size(),
                                        total,
                                        Calendar.getInstance().getTime()
                                ));
                                // insertar la lista de productos
                                for (Product product : productList) {
                                    preorderItems.add(new PreorderItems(preorderId, product.getId()));
                                }
                                appDatabase.preorderItemsDao().addAll(preorderItems);
                                finish();
                            }
                        });

                    }
                }).start();
            }
        });
    }

    private double getTotal(List<Product> products) {
        double total = 0;
        final ProductEvolutionDao productEvolutionDaoDao = AppDatabase.getAppDatabase(getApplicationContext()).productEvolutionDao();
        for (final Product product : products) {
            total += productEvolutionDaoDao.getLastModification(product.getId()).getPrice();
        }
        return total;
    }
}
