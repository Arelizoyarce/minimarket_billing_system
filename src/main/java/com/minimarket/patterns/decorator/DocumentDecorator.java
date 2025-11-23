package com.minimarket.patterns.decorator;

import com.minimarket.patterns.factory.SalesDocument;

public abstract class DocumentDecorator extends SalesDocument {
    protected SalesDocument wrappedDocument;

    public DocumentDecorator(SalesDocument document) {
        this.wrappedDocument = document;
        this.content = document.getPrintableDocument();
    }

    @Override
    public void generateHeader() { wrappedDocument.generateHeader(); }

    @Override
    public String getPrintableDocument() {
        return wrappedDocument.getPrintableDocument();
    }
}
