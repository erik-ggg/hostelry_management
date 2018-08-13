package com.app.developer.hostelry_management.feature.com.app.utils.DataClasses;

import com.app.developer.hostelry_management.feature.com.app.model.Preorder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class PreorderData {
    private String supplierName;
    private Preorder preorder;

    public PreorderData(String supplierName, Preorder preorder) {
        this.supplierName = supplierName;
        this.preorder = preorder;
    }

    @Override
    public String toString() {
        return "Pre-pedido de: " + supplierName + " del: " + new SimpleDateFormat("dd MMM yyyy").format(preorder.getDate())
                + ", nºproductos: " + preorder.getNumberOfItems()
                + ", TOTAL: " + new DecimalFormat("#.##").format(preorder.getTotal());
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
