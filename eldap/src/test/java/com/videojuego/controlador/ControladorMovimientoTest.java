package com.videojuego.controlador;

import com.videojuego.modelo.Personaje;
import com.videojuego.vista.PanelMapa;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControladorMovimientoTest {

    @Test
    void moverConLimites_muevePersonajeEnZonaLibre() throws Exception {
        Personaje p = new Personaje("zeio", "guerrero");
        p.setPosX(600);
        p.setPosY(400);

        PanelMapa panel = new PanelMapa();
        Colisiones col = new Colisiones();
        ControladorMovimiento cm = new ControladorMovimiento(p, panel, col, 10, 10);

        Method mover = ControladorMovimiento.class.getDeclaredMethod("moverConLimites", String.class, int.class,
                int.class);
        mover.setAccessible(true);

        mover.invoke(cm, "w", 2000, 2000);
        assertEquals(600, p.getPosX());
        assertEquals(395, p.getPosY());

        mover.invoke(cm, "d", 2000, 2000);
        assertEquals(605, p.getPosX());
    }

    @Test
    void moverConLimites_respetaLimitesMaximos() throws Exception {
        Personaje p = new Personaje("zeio", "guerrero");
        p.setPosX(1995);
        p.setPosY(400);

        PanelMapa panel = new PanelMapa();
        Colisiones col = new Colisiones();
        ControladorMovimiento cm = new ControladorMovimiento(p, panel, col, 10, 10);

        Method mover = ControladorMovimiento.class.getDeclaredMethod("moverConLimites", String.class, int.class,
                int.class);
        mover.setAccessible(true);

        mover.invoke(cm, "d", 2000, 2000);
        assertEquals(1990, p.getPosX());
    }
}
