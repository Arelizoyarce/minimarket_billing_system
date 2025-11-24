package com.minimarket.patterns.observer;

import com.minimarket.model.Product;

public interface Observador {
    void actualizar(Product producto, int cantidad);
}
