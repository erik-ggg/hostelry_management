package com.app.developer.hostelry_management.feature.com.app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Supplier {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "phonenumber")
    private int phonenumber;
    private String email;

    public Supplier(){}

    public Supplier(String name, int phonenumber) {
        this.name = name;
        this.phonenumber = phonenumber;
    }

    public Supplier(Long id, String name, int phonenumber) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.id = id;
    }

    public Supplier(Long id, String name, int phonenumber, String email) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + ", Tlf: " + phonenumber;
    }
}
