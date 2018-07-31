package com.app.developer.hostelry_management.feature.com.app.activities.order.preorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
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

    private List<Product> productList;
    private double numberOfItems = 0;
    private double total = 0;

    private Button addProduct;
    private Button finishOrder;
    private EditText productQuantityEditText;
    private Spinner productSpinner;
    private Spinner supplierSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_new);

        final List<Supplier>[] suppliers = new List[1];
        productList = new ArrayList<>();

        productSpinner = findViewById(R.id.preorderNewProductSpinner);
        addProduct = findViewById(R.id.preorderNewAddProductButton);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToList(productList, productSpinner);
            }
        });

        productQuantityEditText = findViewById(R.id.preorderNewQuantityEditText);

        supplierSpinner = findViewById(R.id.preorderNewSupplierSpinner);
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

        fillSuppliersSpinner(suppliers, supplierSpinner);

        finishOrder = findViewById(R.id.preorderNewAddButton);
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
                                finishPreorder(productList, appDatabase, supplierSpinner);

                            }
                        });

                    }
                }).start();
            }
        });
    }

    private void addProductToList(List<Product> productList, Spinner productSpinner) {
        // TODO: update code to preorderAddProductsActivityMethod
        double quantity = Double.valueOf(productQuantityEditText.getText().toString());
        Product selectedProduct = (Product) productSpinner.getSelectedItem();
        quantity = checkAndAddDecimalProduct(quantity, selectedProduct);
        for (int i = 0; i < quantity; i++) {
            productList.add(selectedProduct);
        }
    }

    private double checkAndAddDecimalProduct(final double quantity, final Product selectedProduct) {
        if (quantity % 1 != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    double decimalProductValue = (quantity % 1) * AppDatabase.getAppDatabase(getApplicationContext())
                            .productEvolutionDao().getLastModification(selectedProduct.getId()).getPrice();
                    total += decimalProductValue;
                    numberOfItems += quantity % 1;
                }
            }).start();
            return quantity - 1;
        } else {
            return quantity;
        }
    }

    private void finishPreorder(List<Product> productList, AppDatabase appDatabase, Spinner supplierSpinner) {
        // calculamos informacion preorder
        List<PreorderItems> preorderItems = new ArrayList<>();
        List<ProductEvolution> productEvolutions = new ArrayList<>();
        for (Product product : productList) {
            // anadimos su estado actual a la lista
            ProductEvolution productEvolution = appDatabase.productEvolutionDao().getLastModification(product.getId());
            productEvolutions.add(productEvolution);
            total += productEvolution.getPrice();
        }
        // insertar el preorder
        Long preorderId = appDatabase.preorderDao().addPreorder(new Preorder(
                ((Supplier)supplierSpinner.getSelectedItem()).getId(),
                numberOfItems + productList.size(),
                total,
                Calendar.getInstance().getTime()
        ));
        // insertar la lista de productos
        for (ProductEvolution productEvolution : productEvolutions) {
            preorderItems.add(new PreorderItems(preorderId, productEvolution.getId()));
        }
        appDatabase.preorderItemsDao().addAll(preorderItems);
        closeAndRefresh();
    }

    private void fillSuppliersSpinner(final List<Supplier>[] suppliers, final Spinner supplierSpinner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                suppliers[0] = AppDatabase.getAppDatabase(getApplicationContext()).supplierDao().getAll();
                ArrayAdapter<Supplier> suppliersAdapter = new ArrayAdapter<Supplier>(getApplicationContext()
                        , android.R.layout.simple_list_item_1, suppliers[0]);
                supplierSpinner.setAdapter(suppliersAdapter);
            }
        }).start();
    }

    private void closeAndRefresh() {
        Intent refresh = new Intent(this, PreorderListActivity.class);
        startActivity(refresh);
        finish();
    }

    /**
     * Obtains the total value from the preorder products
     * @param products
     * @return
     */
    private double getTotal(List<Product> products) {
        double total = 0;
        final ProductEvolutionDao productEvolutionDaoDao = AppDatabase.getAppDatabase(getApplicationContext()).productEvolutionDao();
        for (final Product product : products) {
            total += productEvolutionDaoDao.getLastModification(product.getId()).getPrice();
        }
        return total;
    }
}
