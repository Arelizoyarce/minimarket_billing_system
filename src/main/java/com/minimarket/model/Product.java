package com.minimarket.model;

import com.minimarket.patterns.composite.ItemComponent;

public class Product implements ItemComponent {
    private String id;
    private String name;
    private double price;
    private int stock;
    private String supplier;
    private String description;

    public Product(String id, String name, double price, int stock, String supplier, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.supplier = supplier;
        this.description = description;
    }

    public void reduceStock(int quantity) { this.stock -= quantity; }
    public int getStock() { return stock; }
    public String getId() { return id; }
    public String getSupplier() { return supplier; }
    public String getDescription() { return description; }

    @Override
    public String getName() { return name; }
    @Override
    public double getPrice() { return price; }
    @Override
    public String display() { return name + " - S/." + price; }

    @Override
    public String toString() { return name; }
}
