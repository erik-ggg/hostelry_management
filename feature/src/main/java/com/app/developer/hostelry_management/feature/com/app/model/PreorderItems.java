package com.app.developer.hostelry_management.feature.com.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Preorder.class, parentColumns = "id", childColumns = "preorderId"),
        @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "productId")})
public class PreorderItems {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public Long preorderId;
    public Long productId;

    public PreorderItems(){}

    public PreorderItems(Long preorderId, Long productId) {
        this.preorderId = preorderId;
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "PreorderItems{" +
                "preorderId=" + preorderId +
                ", productId=" + productId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreorderId() {
        return preorderId;
    }

    public void setPreorderId(Long preorderId) {
        this.preorderId = preorderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
