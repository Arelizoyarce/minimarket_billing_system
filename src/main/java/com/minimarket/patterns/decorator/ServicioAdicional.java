package com.minimarket.patterns.decorator;

import com.minimarket.patterns.factory.Comprobante;
import com.minimarket.patterns.factory.ComprobanteBase;

public abstract class ServicioAdicional extends ComprobanteBase {
    protected Comprobante comprobanteDecorado;

    public ServicioAdicional(Comprobante comprobante) {
        this.comprobanteDecorado = comprobante;
        this.contenido = comprobante.obtenerDocumentoImpreso();
    }

    @Override
    public void generarHeader() { comprobanteDecorado.generarHeader(); }

    @Override
    public String obtenerDocumentoImpreso() {
        return comprobanteDecorado.obtenerDocumentoImpreso();
    }
}
