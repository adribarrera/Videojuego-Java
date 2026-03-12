package com.videojuego.controlador;

import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

class ColisionesTest {

    @Test
    void verificarMovimiento_detectaColision() {
        Colisiones col = new Colisiones();
        boolean puedeMover = col.verificarMovimiento(0, 0, 10, 10);
        assertFalse(puedeMover);
    }

    @Test
    void verificarMovimiento_permitaZonaLibre() {
        Colisiones col = new Colisiones();
        boolean puedeMover = col.verificarMovimiento(600, 400, 10, 10);
        assertTrue(puedeMover);
    }

    @Test
    void eliminarMuroBoss_eliminaRectangulo() {
        Colisiones col = new Colisiones();
        Rectangle muroSoraya = new Rectangle(115, 355, 20, 60);
        assertTrue(col.getMuros().contains(muroSoraya));

        col.eliminarMuroBoss("soraya");

        assertFalse(col.getMuros().contains(muroSoraya));
    }
}
