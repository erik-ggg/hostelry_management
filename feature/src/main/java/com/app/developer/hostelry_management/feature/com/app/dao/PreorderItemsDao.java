package com.app.developer.hostelry_management.feature.com.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;

import java.util.List;

@Dao
public interface PreorderItemsDao {
    @Query("SELECT * FROM preorderitems")
    List<PreorderItems> getAll();
    @Query("SELECT * FROM preorderitems WHERE preorderId = :id")
    List<PreorderItems> getByPreorderId(Long id);
    @Insert
    Long addOne(PreorderItems preorderItems);
    @Insert
    Long[] addAll(List<PreorderItems> preorderItems);
    @Delete
    void deletePreorderItems(List<PreorderItems> preorderItems);
}
