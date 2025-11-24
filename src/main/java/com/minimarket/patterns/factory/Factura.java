package com.minimarket.patterns.factory;

public class Factura extends ComprobanteBase {
    @Override
    public void generarHeader() {
        contenido += "=== FACTURA ELECTRÓNICA ===\nRUC: 20555555551\n----------------------\n";
    }

    @Override
    public String obtenerDocumentoImpreso() {
        double igv = total * 0.18;
        return super.obtenerDocumentoImpreso() + "\n(Incluye IGV: S/." + String.format("%.2f", igv) + ")";
    }
}
