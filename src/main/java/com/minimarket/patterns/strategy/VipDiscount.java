package com.minimarket.patterns.strategy;

public class VipDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) { return totalAmount * 0.90; }
    @Override
    public String getDescription() { return "Descuento VIP (10%)"; }
}
