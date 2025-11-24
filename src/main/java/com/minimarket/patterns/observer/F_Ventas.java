package com.minimarket.patterns.observer;

import com.minimarket.model.Product;
import java.util.ArrayList;
import java.util.List;

public class F_Ventas implements Sujeto {
    private List<Observador> observadores = new ArrayList<>();

    @Override
    public void agregar(Observador o) { observadores.add(o); }

    @Override
    public void eliminar(Observador o) { observadores.remove(o); }

    @Override
    public void notificar(Product producto, int cantidad) {
        for (Observador o : observadores) {
            o.actualizar(producto, cantidad);
        }
    }
}
