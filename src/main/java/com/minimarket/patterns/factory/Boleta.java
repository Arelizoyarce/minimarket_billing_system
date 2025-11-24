package com.minimarket.patterns.factory;

public class Boleta extends ComprobanteBase {
    @Override
    public void generarHeader() {
        contenido += "=== BOLETA DE VENTA ===\nMinimarket La Esquina\n----------------------\n";
    }
}
