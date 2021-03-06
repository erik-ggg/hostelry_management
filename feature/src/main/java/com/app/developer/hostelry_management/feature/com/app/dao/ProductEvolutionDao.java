package com.app.developer.hostelry_management.feature.com.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
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
    @Query("SELECT * FROM productevolution WHERE productId = :id")
    ProductEvolution getLastModification(Long id);
    @Query("SELECT * FROM productevolution WHERE id = :id order by date desc LIMIT 1")
    ProductEvolution getById(Long id);
    @Delete
    void delete(ProductEvolution productEvolution);
}
