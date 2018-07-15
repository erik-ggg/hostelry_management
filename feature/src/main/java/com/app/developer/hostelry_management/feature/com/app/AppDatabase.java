package com.app.developer.hostelry_management.feature.com.app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.app.developer.hostelry_management.feature.com.app.dao.ProductDao;
import com.app.developer.hostelry_management.feature.com.app.dao.ProductEvolutionDao;
import com.app.developer.hostelry_management.feature.com.app.dao.SupplierDao;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;

@Database(entities = {Supplier.class, Product.class, ProductEvolution.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract SupplierDao supplierDao();
    public abstract ProductDao productDao();
    public abstract ProductEvolutionDao productEvolutionDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null ) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app-database").build();
        }
        return INSTANCE;
    }
}
