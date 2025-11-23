package com.minimarket.patterns.strategy;

public class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) { return totalAmount; }
    @Override
    public String getDescription() { return "Sin Descuento"; }
}
