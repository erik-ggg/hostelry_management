package com.app.developer.hostelry_management.feature.com.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.app.developer.hostelry_management.feature.com.app.model.Order;
import com.app.developer.hostelry_management.feature.com.app.model.OrderItems;
import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;

import java.util.List;

@Dao
public interface OrderItemsDao {
    @Query("SELECT * FROM orderitems")
    List<OrderItems> getAll();
    @Query("SELECT * FROM orderitems WHERE orderId = :id")
    List<OrderItems> getByOrderId(Long id);
    @Insert
    Long addOne(OrderItems orderItems);
    @Insert
    Long[] addAll(List<OrderItems> orderItems);
}
