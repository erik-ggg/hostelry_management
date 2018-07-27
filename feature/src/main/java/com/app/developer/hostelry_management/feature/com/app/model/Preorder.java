package com.app.developer.hostelry_management.feature.com.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.app.developer.hostelry_management.feature.com.app.utils.Converters;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Supplier.class, parentColumns = "id", childColumns = "supplierId"))
@TypeConverters(Converters.class)
public class Preorder {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long supplierId;
    private double numberOfItems;
    private double total;
    private Date date;

    public Preorder(){}

    public Preorder(Long supplierId, double numberOfItems, double total, Date date) {
        this.supplierId = supplierId;
        this.numberOfItems = numberOfItems;
        this.total = total;
        this.date = date;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        return "Pre-pedido del: " + simpleDateFormat.format(date) + ", nºproductos: " + numberOfItems + ", TOTAL: " + total;
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
