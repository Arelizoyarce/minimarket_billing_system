package com.minimarket.patterns.factory;

public abstract class ComprobanteBase implements Comprobante {
    protected String contenido = "";
    protected double total;

    @Override
    public void agregarLinea(String linea) { contenido += linea + "\n"; }

    @Override
    public void setTotal(double total) { this.total = total; }

    @Override
    public String obtenerDocumentoImpreso() {
        return contenido + "\nTOTAL A PAGAR: S/." + String.format("%.2f", total);
    }
}
