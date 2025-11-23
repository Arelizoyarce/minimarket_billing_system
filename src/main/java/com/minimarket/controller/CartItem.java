package com.minimarket.controller;

import com.minimarket.patterns.composite.ItemComponent;

public class CartItem {
    private ItemComponent item;
    private int quantity;

    public CartItem(ItemComponent item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public void addQuantity(int q) { this.quantity += q; }
    public int getQuantity() { return quantity; }
    public ItemComponent getItem() { return item; }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }
}
