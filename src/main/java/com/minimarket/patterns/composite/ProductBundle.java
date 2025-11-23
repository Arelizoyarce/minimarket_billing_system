package com.minimarket.patterns.composite;

import java.util.ArrayList;
import java.util.List;

public class ProductBundle implements ItemComponent {
    private String bundleName;
    private List<ItemComponent> items = new ArrayList<>();

    public ProductBundle(String bundleName) {
        this.bundleName = bundleName;
    }

    public void addItem(ItemComponent item) {
        items.add(item);
    }

    @Override
    public String getName() { return bundleName; }

    @Override
    public double getPrice() {
        return items.stream().mapToDouble(ItemComponent::getPrice).sum();
    }

    @Override
    public String display() {
        return "COMBO: " + bundleName + " (Total: S/." + getPrice() + ")";
    }
}
