package com.minimarket.config;

import com.minimarket.model.Product;
import com.minimarket.model.SalesRecord;
import java.util.ArrayList;
import java.util.List;

public class Conexion {
    private static Conexion instance;
    private String url;
    private String user;
    private String pass;

    // Simulación de tablas en memoria
    private List<Product> mockProducts;
    private List<SalesRecord> salesHistory;

    private Conexion() {
        this.url = "jdbc:mysql://localhost:3306/la_esquina";
        this.user = "admin";
        this.pass = "123456";

        mockProducts = new ArrayList<>();
        salesHistory = new ArrayList<>();
        inicializarDatos();
        System.out.println("[Singleton] Objeto Conexion creado: " + url);
    }

    public static synchronized Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public void conectar() {
        System.out.println("Conectando a " + url + " con usuario " + user);
    }

    private void inicializarDatos() {
        mockProducts.add(new Product("101", "Arroz Costeño 1kg", 4.50, 20, "Alicorp", "Arroz extra"));
        mockProducts.add(new Product("102", "Azucar Rubia 1kg", 3.80, 15, "Casa Grande", "Azucar"));
        mockProducts.add(new Product("103", "Coca Cola 3L", 12.00, 50, "Arca", "Gaseosa"));
        mockProducts.add(new Product("104", "Leche Gloria", 5.20, 10, "Gloria", "Leche"));
        mockProducts.add(new Product("108", "Galletas Oreo", 4.00, 4, "Mondelez", "Paquete x6"));
    }

    public List<Product> getProducts() { return mockProducts; }
    public List<SalesRecord> getSalesHistory() { return salesHistory; }
    public void addSaleRecord(SalesRecord record) { salesHistory.add(record); }
}
