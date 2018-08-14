package com.app.developer.hostelry_management.feature.com.app.utils.DataClasses;

import com.app.developer.hostelry_management.feature.com.app.model.Order;
import com.app.developer.hostelry_management.feature.com.app.model.Preorder;

public class OrderData {
    private String supplierName;
    private Order order;

    public OrderData(String supplierName, Order order) {
        this.supplierName = supplierName;
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "supplierName='" + supplierName + '\'' +
                ", preorder=" + order +
                '}';
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
