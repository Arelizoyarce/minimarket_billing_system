package com.minimarket.patterns.strategy;

public class SeasonalDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) { return totalAmount * 0.80; }
    @Override
    public String getDescription() { return "Descuento de Temporada (20%)"; }
}
