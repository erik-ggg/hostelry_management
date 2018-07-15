package com.app.developer.hostelry_management.feature.com.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;

import java.util.List;

@Dao
public interface ProductEvolutionDao {
    @Insert
    void insert(ProductEvolution productEvolution);

    @Query("SELECT * FROM ProductEvolution WHERE productId = :productId")
    List<ProductEvolution> getAllByProduct(Long productId);
}
