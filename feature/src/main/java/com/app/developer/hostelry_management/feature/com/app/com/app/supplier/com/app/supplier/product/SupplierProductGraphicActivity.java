package com.app.developer.hostelry_management.feature.com.app.com.app.supplier.com.app.supplier.product;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.developer.hostelry_management.feature.R;
import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class SupplierProductGraphicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_product_graphic);

        final Context thisContext = getApplicationContext();
        final Product product = new Gson()
                .fromJson(getIntent().getStringExtra("product"), Product.class);
        final List<ProductEvolution>[] productEvolutionList = new List[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                productEvolutionList[0] = AppDatabase.getAppDatabase(thisContext)
                        .productEvolutionDao().getAllByProduct(product.getId());
                DataPoint[] dataPoints = new DataPoint[productEvolutionList[0].size()];
                for (int i = 0; i < productEvolutionList[0].size(); i++) {
                    ProductEvolution productEvolution = productEvolutionList[0].get(i);
                    dataPoints[i] = new DataPoint(productEvolution.getDate(), productEvolution.getPrice());
                }

                GraphView graphView = findViewById(R.id.graph);
                graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(thisContext));
                graphView.getGridLabelRenderer().setNumHorizontalLabels(3);
                graphView.getGridLabelRenderer().setHumanRounding(false);

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                graphView.addSeries(series);
            }
        }).start();

    }
}
