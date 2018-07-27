package com.app.developer.hostelry_management.feature.com.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "orderId"),
        @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "productId")})
public class OrderItems {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public Long orderId;
    public Long productId;

    public OrderItems(){}

    public OrderItems(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "PreorderItems{" +
                "preorderId=" + orderId +
                ", productId=" + productId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long preorderId) {
        this.orderId = preorderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
