package com.minimarket.patterns.decorator;

import com.minimarket.patterns.factory.Comprobante;

public class Delivery extends ServicioAdicional {
    private double costo = 10.00;

    public Delivery(Comprobante comprobante) {
        super(comprobante);
    }

    @Override
    public String obtenerDocumentoImpreso() {
        return super.obtenerDocumentoImpreso() + "\n+ Delivery: S/." + costo;
    }
}
