package com.minimarket.patterns.factory;

public interface Comprobante {
    void generarHeader();
    void agregarLinea(String linea);
    void setTotal(double total);
    String obtenerDocumentoImpreso();
}
