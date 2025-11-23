package com.minimarket.controller;

import com.minimarket.config.DatabaseConnection;
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
    private SalesSubject salesSubject;

    public MainController() {
        this.shoppingCart = new HashMap<>();
        this.salesSubject = new SalesSubject();
        this.salesSubject.attach(new InventoryManager());
    }

    public List<Product> getProducts() {
        return DatabaseConnection.getInstance().getProducts();
    }

    public void addToCart(ItemComponent item) throws Exception {
        if (item instanceof Product) {
            Product p = (Product) item;
            int currentQtyInCart = 0;
            if (shoppingCart.containsKey(p.getName())) {
                currentQtyInCart = shoppingCart.get(p.getName()).getQuantity();
            }

            if (p.getStock() < (currentQtyInCart + 1)) {
                throw new Exception("¡STOCK INSUFICIENTE! Solo quedan " + p.getStock() + " unidades.");
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
        return shoppingCart.values().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public void clearCart() {
        shoppingCart.clear();
    }

    public String processSale(String docType, String discountType, boolean hasDelivery) throws Exception {
        if (shoppingCart.isEmpty()) throw new Exception("El carrito está vacío.");

        double subtotal = calculateCurrentTotal();

        DiscountStrategy strategy;
        switch (discountType) {
            case "VIP": strategy = new VipDiscount(); break;
            case "SEASONAL": strategy = new SeasonalDiscount(); break;
            default: strategy = new NoDiscount();
        }
        double total = strategy.applyDiscount(subtotal);

        DocumentFactory factory;
        if (docType.equals("FACTURA")) factory = new InvoiceFactory();
        else factory = new TicketFactory();

        SalesDocument doc = factory.createDocument();
        doc.generateHeader();

        for (CartItem cartItem : shoppingCart.values()) {
            ItemComponent item = cartItem.getItem();
            int qty = cartItem.getQuantity();

            doc.addLine(qty + " x " + item.getName() + " (Unit: S/." + item.getPrice() + ") -> S/." + cartItem.getSubtotal());

            if (item instanceof Product) {
                salesSubject.notifySale((Product) item, qty);
            }
        }

        doc.addLine("----------------------");
        doc.addLine("Subtotal: S/." + subtotal);
        doc.addLine("Descuento (" + strategy.getDescription() + "): -S/." + (subtotal - total));
        doc.setTotal(total);

        if (hasDelivery) {
            doc = new DeliveryDecorator(doc);
        }

        String finalDoc = doc.getPrintableDocument();

        SalesRecord record = new SalesRecord(docType, total, finalDoc);
        DatabaseConnection.getInstance().addSaleRecord(record);

        shoppingCart.clear();
        return finalDoc;
    }

    public ProductBundle createSampleBundle() {
        ProductBundle bundle = new ProductBundle("Pack Desayuno");
        List<Product> db = getProducts();
        if(db.size() >= 4) {
            bundle.addItem(db.get(1));
            bundle.addItem(db.get(3));
        }
        return bundle;
    }

    public List<SalesRecord> getHistoryByDate(String dateQuery) {
        List<SalesRecord> all = DatabaseConnection.getInstance().getSalesHistory();
        if(dateQuery == null || dateQuery.isEmpty()) return all;
        return all.stream().filter(r -> r.getDate().equals(dateQuery)).collect(Collectors.toList());
    }
}
