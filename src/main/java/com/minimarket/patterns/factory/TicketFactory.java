package com.minimarket.patterns.factory;

public class TicketFactory extends DocumentFactory {
    @Override
    public SalesDocument createDocument() { return new Ticket(); }
}
