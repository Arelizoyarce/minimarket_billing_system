package com.minimarket.patterns.observer;

import com.minimarket.model.Product;

public interface InventoryObserver {
    void update(Product product, int quantitySold);
}
