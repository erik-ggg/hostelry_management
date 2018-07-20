package com.app.developer.hostelry_management.feature.com.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.TypeConverters;

import com.app.developer.hostelry_management.feature.com.app.com.app.utils.Converters;

import java.util.Date;
import java.util.List;

@Entity(foreignKeys = @ForeignKey(entity = Supplier.class, parentColumns = "id", childColumns = "supplierId"))
@TypeConverters(Converters.class)
public class Preorder {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public Long supplierId;
    public double numberOfItems;
    public double total;
    public Date date;

    public Preorder(){}

    public Preorder(Long supplierId, double numberOfItems, double total, Date date) {
        this.supplierId = supplierId;
        this.numberOfItems = numberOfItems;
        this.total = total;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Preorder{" +
                "supplierId=" + supplierId +
                ", numberOfItems=" + numberOfItems +
                ", total=" + total +
                ", date=" + date +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public double getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(double numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
