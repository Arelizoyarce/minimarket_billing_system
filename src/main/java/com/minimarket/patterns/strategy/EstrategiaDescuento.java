package com.minimarket.patterns.strategy;

public interface EstrategiaDescuento {
    double aplicar(double montoBase);
    String getDescripcion();
}
