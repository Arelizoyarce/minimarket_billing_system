package com.minimarket.patterns.strategy;

public class VentaContext {
    private EstrategiaDescuento estrategia;

    public void setEstrategia(EstrategiaDescuento estrategia) {
        this.estrategia = estrategia;
    }

    public double calcularTotalFinal(double montoBase) {
        if(estrategia == null) return montoBase;
        return estrategia.aplicar(montoBase);
    }

    public String getDescripcionEstrategia() {
        return estrategia != null ? estrategia.getDescripcion() : "Ninguna";
    }
}
