package com.app.developer.hostelry_management.feature.com.app.utils;

import com.app.developer.hostelry_management.feature.com.app.model.Product;

public class ProductQuantity {

    private Product product;
    private int quantity;

    public ProductQuantity(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Producto: " + product.toString() + ", cantidad: " + quantity;
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
