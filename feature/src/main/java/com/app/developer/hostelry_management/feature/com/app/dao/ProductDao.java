package com.app.developer.hostelry_management.feature.com.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.app.developer.hostelry_management.feature.com.app.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();
    @Query("SELECT * FROM product WHERE supplierId = :supplierId")
    List<Product> getProductsBySupplier(Long supplierId);
    @Insert
    Long insert(Product product);
    @Query("SELECT * FROM product WHERE id = :id")
    Product getProductById(Long id);
}
