package com.minimarket.patterns.decorator;

import com.minimarket.patterns.factory.SalesDocument;

public class DeliveryDecorator extends DocumentDecorator {
    private double deliveryCost = 10.00;

    public DeliveryDecorator(SalesDocument document) {
        super(document);
    }

    @Override
    public String getPrintableDocument() {
        return super.getPrintableDocument() + "\n+ Servicio de Delivery: S/." + deliveryCost;
    }
}
