package com.minimarket.patterns.factory;

public class Invoice extends SalesDocument {
    @Override
    public void generateHeader() {
        content += "=== FACTURA ELECTRÓNICA ===\nRUC: 20123456789\nCliente: Empresa S.A.\n----------------------\n";
    }

    @Override
    public String getPrintableDocument() {
        double igv = total * 0.18;
        return super.getPrintableDocument() + "\n(Incluye IGV: S/." + String.format("%.2f", igv) + ")";
    }
}
