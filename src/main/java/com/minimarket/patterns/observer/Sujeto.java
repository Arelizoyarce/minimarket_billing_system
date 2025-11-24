package com.minimarket.patterns.observer;

import com.minimarket.model.Product;

public interface Sujeto {
    void agregar(Observador o);
    void eliminar(Observador o);
    void notificar(Product producto, int cantidad);
}
