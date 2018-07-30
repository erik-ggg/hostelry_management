package com.app.developer.hostelry_management.feature.com.app.utils;

import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {

    public static void orderAlphabeticallySupplier(List<Supplier> list) {
        Collections.sort(list, new Comparator<Supplier>()
        {
            @Override
            public int compare(Supplier supplier1, Supplier supplier2) {
                return supplier1.getName().compareToIgnoreCase(supplier2.getName());
            }
        });
    }

    public static void orderAlphabeticallyProductQuantity(List<ProductQuantity> list) {
        Collections.sort(list, new Comparator<ProductQuantity>()
        {
            @Override
            public int compare(ProductQuantity productQuantity1, ProductQuantity productQuantity2) {
                return productQuantity1.getProduct().getName().compareToIgnoreCase(
                        productQuantity2.getProduct().getName()
                );
            }
        });
    }

    public static void orderAlphabeticallyProduct(List<Product> list) {
        Collections.sort(list, new Comparator<Product>()
        {
            @Override
            public int compare(Product product1, Product product2) {
                return product1.getName().compareToIgnoreCase(product2.getName());
            }
        });
    }

}
