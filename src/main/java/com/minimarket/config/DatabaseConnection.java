package com.minimarket.config;

import com.minimarket.model.Product;
import com.minimarket.model.SalesRecord;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private List<Product> mockProducts;
    private List<SalesRecord> salesHistory;

    private DatabaseConnection() {
        mockProducts = new ArrayList<>();
        salesHistory = new ArrayList<>();

        // Datos Iniciales
        mockProducts.add(new Product("101", "Arroz Costeño 1kg", 4.50, 20, "Alicorp", "Arroz extra graneado"));
        mockProducts.add(new Product("102", "Azucar Rubia 1kg", 3.80, 15, "Casa Grande", "Azucar doméstica"));
        mockProducts.add(new Product("103", "Coca Cola 3L", 12.00, 50, "Arca Continental", "Gaseosa familiar"));
        mockProducts.add(new Product("104", "Leche Gloria Azul", 5.20, 10, "Gloria S.A.", "Leche evaporada entera"));
        mockProducts.add(new Product("105", "Aceite Primor", 11.50, 5, "Alicorp", "Aceite vegetal premium"));
        mockProducts.add(new Product("106", "Fideos Don Vittorio", 3.20, 30, "Alicorp", "Spaghetti grosso"));
        mockProducts.add(new Product("107", "Detergente Bolívar", 15.00, 8, "Alicorp", "Detergente matic 2kg"));
        mockProducts.add(new Product("108", "Galletas Oreo Paq.", 4.00, 4, "Mondelez", "Paquete x6 unidades"));

        System.out.println("[Singleton] Base de datos en memoria inicializada.");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public List<Product> getProducts() { return mockProducts; }
    public List<SalesRecord> getSalesHistory() { return salesHistory; }
    public void addSaleRecord(SalesRecord record) { salesHistory.add(record); }
}
