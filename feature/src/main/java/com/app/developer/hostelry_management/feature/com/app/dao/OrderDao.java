package com.app.developer.hostelry_management.feature.com.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.app.developer.hostelry_management.feature.com.app.model.Order;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM `order`")
    List<Order> getAll();
    @Insert
    Long addOrder(Order order);
    @Insert
    void addAll(List<Order> orders);
}
