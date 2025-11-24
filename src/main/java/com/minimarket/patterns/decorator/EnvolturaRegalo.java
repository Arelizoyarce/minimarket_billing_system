package com.minimarket.patterns.decorator;

import com.minimarket.patterns.factory.Comprobante;

public class EnvolturaRegalo extends ServicioAdicional {
    private double costo = 5.00;

    public EnvolturaRegalo(Comprobante comprobante) {
        super(comprobante);
    }

    @Override
    public String obtenerDocumentoImpreso() {
        return super.obtenerDocumentoImpreso() + "\n+ Envoltura de Regalo: S/." + costo;
    }
}
