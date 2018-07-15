package com.app.developer.hostelry_management.feature.com.app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.app.developer.hostelry_management.feature.com.app.com.app.utils.Converters;

import java.sql.Timestamp;
import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Product.class
        , parentColumns = "id", childColumns = "productId"))
@TypeConverters(Converters.class)
public class ProductEvolution {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "productId")
    private Long productId;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "price")
    private double price;

    public ProductEvolution(){}

    public ProductEvolution(Long product, Date date, double price) {
        this.productId = product;
        this.date = date;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
