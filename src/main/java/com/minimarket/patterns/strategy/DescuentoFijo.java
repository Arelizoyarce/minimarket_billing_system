package com.minimarket.patterns.strategy;

// Simula "Sin Descuento" o descuento base
public class DescuentoFijo implements EstrategiaDescuento {
    @Override
    public double aplicar(double montoBase) { return montoBase; }

    @Override
    public String getDescripcion() { return "Precio Regular"; }
}
