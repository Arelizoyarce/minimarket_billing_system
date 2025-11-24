package com.minimarket.patterns.strategy;

public class DescuentoClienteVIP implements EstrategiaDescuento {
    @Override
    public double aplicar(double montoBase) { return montoBase * 0.90; } // 10%

    @Override
    public String getDescripcion() { return "Cliente VIP (10%)"; }
}
