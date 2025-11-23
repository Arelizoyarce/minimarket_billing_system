package com.minimarket.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SalesRecord {
    private String date;
    private String docType;
    private double total;
    private String details;

    public SalesRecord(String docType, double total, String details) {
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.docType = docType;
        this.total = total;
        this.details = details;
    }

    public String getDate() { return date; }
    public String getDocType() { return docType; }
    public double getTotal() { return total; }
    public String getDetails() { return details; }
}
