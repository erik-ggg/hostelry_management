package com.app.developer.hostelry_management.feature.com.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Preorder.class, parentColumns = "id", childColumns = "preorderId"),
        @ForeignKey(entity = ProductEvolution.class, parentColumns = "id", childColumns = "productEvolutionId")})
public class PreorderItems {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public Long preorderId;
    public Long productEvolutionId;

    public PreorderItems(){}

    public PreorderItems(Long preorderId, Long productEvolutionId) {
        this.preorderId = preorderId;
        this.productEvolutionId = productEvolutionId;
    }

    @Override
    public String toString() {
        return "PreorderItems{" +
                "preorderId=" + preorderId +
                ", productId=" + productEvolutionId +
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

    public Long getProductEvolutionId() {
        return productEvolutionId;
    }

    public void setproductEvolutionId(Long productEvolutionId) {
        this.productEvolutionId = productEvolutionId;
    }
}
