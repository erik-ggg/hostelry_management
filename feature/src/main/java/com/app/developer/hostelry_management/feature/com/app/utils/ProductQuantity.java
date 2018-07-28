package com.app.developer.hostelry_management.feature.com.app.utils;

import com.app.developer.hostelry_management.feature.com.app.model.Product;

public class ProductQuantity {

    private Product product;
    private int quantity;
    private double total;

    public ProductQuantity(Product product, int quantity, double total) {
        this.product = product;
        this.quantity = quantity;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Producto: " + product.toString() + ", cantidad: " + quantity + ", total: " + total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
