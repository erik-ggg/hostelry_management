package com.app.developer.hostelry_management.feature.com.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "orderId"),
        @ForeignKey(entity = ProductEvolution.class, parentColumns = "id", childColumns = "productEvolutionId")})
public class OrderItems {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public Long orderId;
    public Long productEvolutionId;


    public OrderItems(){}

    public OrderItems(Long orderId, Long productEvolutionId) {
        this.orderId = orderId;
        this.productEvolutionId = productEvolutionId;
    }

    @Override
    public String toString() {
        return "PreorderItems{" +
                "preorderId=" + orderId +
                ", productId=" + productEvolutionId +
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

    public Long getProductEvolutionId() {
        return productEvolutionId;
    }

    public void setProductEvolutionId(Long productEvolutionId) {
        this.productEvolutionId = productEvolutionId;
    }
}
