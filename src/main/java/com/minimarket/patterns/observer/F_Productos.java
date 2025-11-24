package com.minimarket.patterns.observer;

import com.minimarket.model.Product;

public class F_Productos implements Observador {
    @Override
    public void actualizar(Product producto, int cantidad) {
        producto.reduceStock(cantidad);
        System.out.println("[F_Productos] Stock actualizado para: " + producto.getName() +
                           " | Nuevo Stock: " + producto.getStock());
    }
}
