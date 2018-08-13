package com.app.developer.hostelry_management.feature.com.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.app.developer.hostelry_management.feature.com.app.model.Preorder;

import java.util.List;

@Dao
public interface PreorderDao {
    @Query("SELECT * FROM preorder")
    List<Preorder> getAll();
    @Insert
    Long addPreorder(Preorder preorder);
    @Insert
    void addAll(List<Preorder> preorders);
    @Delete
    void deletePreorder(Preorder preorder);
    @Update
    void updatePreorder(Preorder preorder);
    @Query("DELETE FROM preorder WHERE id = :id")
    void deletePreorderById(Long id);
}
