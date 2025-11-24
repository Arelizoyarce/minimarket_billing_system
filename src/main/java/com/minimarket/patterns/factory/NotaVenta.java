package com.minimarket.patterns.factory;

public class NotaVenta extends ComprobanteBase {
    @Override
    public void generarHeader() {
        contenido += "=== NOTA DE VENTA (Interna) ===\nSin valor fiscal\n----------------------\n";
    }
}
