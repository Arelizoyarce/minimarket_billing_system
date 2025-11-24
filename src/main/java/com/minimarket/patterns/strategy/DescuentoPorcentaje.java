package com.minimarket.patterns.strategy;

public class DescuentoPorcentaje implements EstrategiaDescuento {
    private double porcentaje;

    public DescuentoPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public double aplicar(double montoBase) {
        return montoBase * (1.0 - (porcentaje/100.0));
    }

    @Override
    public String getDescripcion() { return "Promoción Especial (" + porcentaje + "%)"; }
}
