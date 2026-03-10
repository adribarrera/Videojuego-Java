package com.videojuego.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TiendaTest {

    @Test
    void obtenerInfoItems_devuelveInfoOEspacioVacio() {
        Tienda t = new Tienda("T");
        String info = t.obtenerInfoItems(0);
        assertTrue(info.contains("Precio"));

        String vacio = t.obtenerInfoItems(999);
        assertTrue(vacio.startsWith("Espacio"));
    }

    @Test
    void procesarCompra_indiceInvalido() {
        Tienda t = new Tienda("T");
        Personaje p = new Personaje("Ana", "guerrero");
        String res = t.procesarCompra(-1, p);
        assertTrue(res.startsWith("Selecci"));
    }

    @Test
    void procesarCompra_sinDinero() {
        Tienda t = new Tienda("T");
        Personaje p = new Personaje("Ana", "guerrero");
        p.setDinero(0);
        String res = t.procesarCompra(0, p);
        assertTrue(res.startsWith("Te faltan "));
    }

    @Test
    void procesarCompra_ok_descuentaYAgrega() {
        Tienda t = new Tienda("T");
        Personaje p = new Personaje("Ana", "guerrero");
        p.setDinero(1000);

        String res = t.procesarCompra(0, p);

        assertTrue(res.startsWith("Compra realizada."));
        assertEquals(1, p.getInventario().size());
        assertTrue(p.getDinero() < 1000);
    }

    @Test
    void procesarCompra_inventarioLleno() {
        Tienda t = new Tienda("T");
        Personaje p = new Personaje("Ana", "guerrero");
        p.setDinero(1000);

        p.recogerItem(new Item("I1", "", "/no-existe.png", 0, TipoEfecto.CURAR_PORCENTAJE, 1));
        p.recogerItem(new Item("I2", "", "/no-existe.png", 0, TipoEfecto.CURAR_PORCENTAJE, 1));

        String res = t.procesarCompra(0, p);
        assertTrue(res.startsWith("No puedes llevar"));
        assertEquals(2, p.getInventario().size());
    }
}
