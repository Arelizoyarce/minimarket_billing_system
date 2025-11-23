package com.minimarket.patterns.factory;

public class Ticket extends SalesDocument {
    @Override
    public void generateHeader() {
        content += "=== BOLETA DE VENTA ===\nMinimarket La Esquina\nCliente: Final\n----------------------\n";
    }
}
