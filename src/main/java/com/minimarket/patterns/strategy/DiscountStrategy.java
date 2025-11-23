package com.minimarket.patterns.strategy;

public interface DiscountStrategy {
    double applyDiscount(double totalAmount);
    String getDescription();
}
