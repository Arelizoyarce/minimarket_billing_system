package com.minimarket.patterns.factory;

public abstract class SalesDocument {
    protected String content = "";
    protected double total;

    public abstract void generateHeader();
    public void addLine(String line) { content += line + "\n"; }
    public void setTotal(double total) { this.total = total; }

    public String getPrintableDocument() {
        return content + "\nTOTAL A PAGAR: S/." + String.format("%.2f", total);
    }
}
