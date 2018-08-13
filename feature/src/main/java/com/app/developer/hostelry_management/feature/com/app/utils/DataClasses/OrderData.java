package com.app.developer.hostelry_management.feature.com.app.utils.DataClasses;

import com.app.developer.hostelry_management.feature.com.app.model.Preorder;

public class OrderData {
    private String supplierName;
    private Preorder preorder;

    public OrderData(String supplierName, Preorder preorder) {
        this.supplierName = supplierName;
        this.preorder = preorder;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "supplierName='" + supplierName + '\'' +
                ", preorder=" + preorder +
                '}';
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Preorder getPreorder() {
        return preorder;
    }

    public void setPreorder(Preorder preorder) {
        this.preorder = preorder;
    }
}
