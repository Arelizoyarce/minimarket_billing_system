package com.minimarket.patterns.factory;

public class CreadorVenta {
    public static Comprobante crearComprobante(String tipo) {
        switch (tipo) {
            case "FACTURA": return new Factura();
            case "NOTA": return new NotaVenta();
            default: return new Boleta();
        }
    }
}
