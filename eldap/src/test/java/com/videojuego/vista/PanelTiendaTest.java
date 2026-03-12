package com.videojuego.vista;

import com.videojuego.modelo.Personaje;
import org.junit.jupiter.api.Test;

import javax.swing.JTextArea;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PanelTiendaTest {

    @Test
    void seleccionarYComprar_actualizaEstado() throws Exception {
        PanelTienda panel = new PanelTienda();
        Personaje p = new Personaje("zeio", "guerrero");
        p.setDinero(1000);
        panel.setJugadorActivo(p);

        Method seleccionar = PanelTienda.class.getDeclaredMethod("seleccionarItem", int.class);
        seleccionar.setAccessible(true);
        seleccionar.invoke(panel, 0);

        Field areaField = PanelTienda.class.getDeclaredField("areaDescripcion");
        areaField.setAccessible(true);
        JTextArea area = (JTextArea) areaField.get(panel);
        assertTrue(area.getText().contains("Precio"));

        Method comprar = PanelTienda.class.getDeclaredMethod("accionComprar");
        comprar.setAccessible(true);
        comprar.invoke(panel);

        assertEquals(1, p.getInventario().size());

        Method volver = PanelTienda.class.getDeclaredMethod("accionVolver");
        volver.setAccessible(true);
        volver.invoke(panel);

        Field selField = PanelTienda.class.getDeclaredField("itemSeleccionado");
        selField.setAccessible(true);
        assertEquals(-1, selField.getInt(panel));
    }
}
