package com.app.developer.hostelry_management.feature.com.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.app.developer.hostelry_management.feature.com.app.model.Supplier;

import java.util.List;

@Dao
public interface SupplierDao {
    @Query("SELECT * FROM supplier")
    List<Supplier> getAll();
    @Insert
    void insert(Supplier supplier);
    @Delete
    void delete(Supplier supplier);
    @Query("SELECT * FROM Supplier WHERE id = :id ")
    Supplier getById(Long id);
    @Update
    void update(Supplier supplier);
}
