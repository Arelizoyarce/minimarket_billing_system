package com.minimarket.controller;

import com.minimarket.config.Conexion;
import com.minimarket.model.Product;
import com.minimarket.model.SalesRecord;
import com.minimarket.patterns.composite.ItemComponent;
import com.minimarket.patterns.composite.ProductBundle;
import com.minimarket.patterns.factory.*;
import com.minimarket.patterns.strategy.*;
import com.minimarket.patterns.decorator.*;
import com.minimarket.patterns.observer.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController {
    private Map<String, CartItem> shoppingCart;

    // Observer: F_Ventas (Sujeto) y F_Productos (Observador)
    private F_Ventas ventasSujeto;

    // Strategy: Contexto
    private VentaContext ventaContext;

    public MainController() {
        this.shoppingCart = new HashMap<>();

        // Configurar Observer
        this.ventasSujeto = new F_Ventas();
        this.ventasSujeto.agregar(new F_Productos());

        // Configurar Strategy
        this.ventaContext = new VentaContext();
    }

    public List<Product> getProducts() {
        // Usar nueva clase Conexion (Singleton)
        return Conexion.getInstance().getProducts();
    }

    public void addToCart(ItemComponent item) throws Exception {
        if (item instanceof Product) {
            Product p = (Product) item;
            int currentQtyInCart = 0;
            if (shoppingCart.containsKey(p.getName())) {
                currentQtyInCart = shoppingCart.get(p.getName()).getQuantity();
            }
            if (p.getStock() < (currentQtyInCart + 1)) {
                throw new Exception("Stock insuficiente: " + p.getStock());
            }
        }
        if (shoppingCart.containsKey(item.getName())) {
            shoppingCart.get(item.getName()).addQuantity(1);
        } else {
            shoppingCart.put(item.getName(), new CartItem(item, 1));
        }
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(shoppingCart.values());
    }

    public double calculateCurrentTotal() {
        return shoppingCart.values().stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    public void clearCart() { shoppingCart.clear(); }

    public String processSale(String docType, String discountType, boolean hasDelivery, boolean hasGiftWrap) throws Exception {
        if (shoppingCart.isEmpty()) throw new Exception("Carrito vacío.");

        double subtotal = calculateCurrentTotal();

        // 1. Aplicar STRATEGY
        EstrategiaDescuento estrategia;
        switch (discountType) {
            case "VIP": estrategia = new DescuentoClienteVIP(); break;
            case "SEASONAL": estrategia = new DescuentoPorcentaje(20.0); break;
            default: estrategia = new DescuentoFijo();
        }
        ventaContext.setEstrategia(estrategia);
        double total = ventaContext.calcularTotalFinal(subtotal);

        // 2. Usar FACTORY METHOD (CreadorVenta)
        Comprobante doc = CreadorVenta.crearComprobante(docType);
        doc.generarHeader();

        for (CartItem cartItem : shoppingCart.values()) {
            ItemComponent item = cartItem.getItem();
            int qty = cartItem.getQuantity();

            doc.agregarLinea(qty + " x " + item.getName() + " -> S/." + cartItem.getSubtotal());

            // 3. Notificar OBSERVER
            if (item instanceof Product) {
                ventasSujeto.notificar((Product) item, qty);
            }
        }

        doc.agregarLinea("----------------------");
        doc.agregarLinea("Subtotal: S/." + subtotal);
        doc.agregarLinea("Desc (" + estrategia.getDescripcion() + "): -S/." + (subtotal - total));
        doc.setTotal(total);

        // 4. Aplicar DECORATOR
        if (hasDelivery) {
            doc = new Delivery(doc);
        }
        if (hasGiftWrap) {
            doc = new EnvolturaRegalo(doc);
        }

        String finalDoc = doc.obtenerDocumentoImpreso();

        // Guardar Historial
        SalesRecord record = new SalesRecord(docType, total, finalDoc);
        Conexion.getInstance().addSaleRecord(record);

        shoppingCart.clear();
        return finalDoc;
    }

    public ProductBundle createSampleBundle() {
        ProductBundle bundle = new ProductBundle("Pack Desayuno");
        List<Product> db = getProducts();
        if(db.size() >= 2) {
            bundle.addItem(db.get(1));
            bundle.addItem(db.get(3));
        }
        return bundle;
    }

    public List<SalesRecord> getHistoryByDate(String dateQuery) {
        List<SalesRecord> all = Conexion.getInstance().getSalesHistory();
        if(dateQuery == null || dateQuery.isEmpty()) return all;
        return all.stream().filter(r -> r.getDate().equals(dateQuery)).collect(Collectors.toList());
    }
}
