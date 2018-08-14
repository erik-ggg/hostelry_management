package com.app.developer.hostelry_management.feature.com.app.utils;

import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.model.OrderItems;
import com.app.developer.hostelry_management.feature.com.app.model.PreorderItems;
import com.app.developer.hostelry_management.feature.com.app.model.Product;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;
import com.app.developer.hostelry_management.feature.com.app.utils.DataClasses.PreorderData;
import com.app.developer.hostelry_management.feature.com.app.utils.DataClasses.ProductQuantity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void orderPreorderbyDate(List<PreorderData> list) {
        Collections.sort(list, new Comparator<PreorderData>()
        {
            @Override
            public int compare(PreorderData preorderData1, PreorderData preorderData2) {
                return preorderData1.getPreorder().getDate().compareTo(preorderData2.getPreorder().getDate());
            }
        });
    }

    /**
     * Obtains, converts and returns the information of the preorder items into a more suitable data structure
     * @param preorderItems
     * @param database
     * @return
     */
    public static List<ProductQuantity> getProductsQuantityByPreorder(List<PreorderItems> preorderItems, AppDatabase database) {
        List<ProductQuantity> products = new ArrayList<>();
        Map<Long, Integer> productsMap = new HashMap<>();
        for (PreorderItems items : preorderItems) {
            if (productsMap.containsKey(items.getProductEvolutionId())) {
                productsMap.put(items.getProductEvolutionId(), productsMap.get(items.getProductEvolutionId()) + 1);
            } else {
                productsMap.put(items.getProductEvolutionId(), 1);
            }
        }
        for (Long key : productsMap.keySet()) {
            int quantity = productsMap.get(key);
            double total = database.productEvolutionDao().getById(key).getPrice() * quantity;
            products.add(new ProductQuantity(database.productDao().getProductById(key), key, quantity, total));
        }
        return products;
    }

    public static List<ProductQuantity> getProductsQuantityByOrder(List<OrderItems> orderItems, AppDatabase database) {
        List<ProductQuantity> products = new ArrayList<>();
        Map<Long, Integer> productsMap = new HashMap<>();
        for (OrderItems items : orderItems) {
            if (productsMap.containsKey(items.getProductEvolutionId())) {
                productsMap.put(items.getProductEvolutionId(), productsMap.get(items.getProductEvolutionId()) + 1);
            } else {
                productsMap.put(items.getProductEvolutionId(), 1);
            }
        }
        for (Long key : productsMap.keySet()) {
            int quantity = productsMap.get(key);
            double total = database.productEvolutionDao().getById(key).getPrice() * quantity;
            products.add(new ProductQuantity(database.productDao().getProductById(key), key, quantity, total));
        }
        return products;
    }


}
