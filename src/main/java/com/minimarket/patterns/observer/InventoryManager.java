package com.minimarket.patterns.observer;

import com.minimarket.model.Product;

public class InventoryManager implements InventoryObserver {
    @Override
    public void update(Product product, int quantitySold) {
        product.reduceStock(quantitySold);
        System.out.println("[OBSERVER] Stock reducido para: " + product.getName() +
                           " | Nuevo Stock: " + product.getStock());
    }
}
