package com.app.developer.hostelry_management.feature.com.app.utils.DataClasses;

import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.ProductEvolution;

import java.text.DecimalFormat;

public class ProductQuantity {

    private Product product;
    private Long productEvolutionId;
    private int quantity;
    private double total;

    public ProductQuantity(Product product, Long productEvolutionId, int quantity, double total) {
        this.product = product;
        this.productEvolutionId = productEvolutionId;
        this.quantity = quantity;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Producto: " + product.toString() + ", cantidad: " + quantity
                + ", total: " + new DecimalFormat("#.##").format(total);
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

    public Long getProductEvolutionId() {
        return productEvolutionId;
    }

    public void setProductEvolutionId(Long productEvolutionId) {
        this.productEvolutionId = productEvolutionId;
    }
}
