package com.minimarket.patterns.observer;

import com.minimarket.model.Product;
import java.util.ArrayList;
import java.util.List;

public class SalesSubject {
    private List<InventoryObserver> observers = new ArrayList<>();

    public void attach(InventoryObserver observer) {
        observers.add(observer);
    }

    public void notifySale(Product product, int quantity) {
        for (InventoryObserver observer : observers) {
            observer.update(product, quantity);
        }
    }
}
