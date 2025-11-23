package com.minimarket.patterns.factory;

public class InvoiceFactory extends DocumentFactory {
    @Override
    public SalesDocument createDocument() { return new Invoice(); }
}
