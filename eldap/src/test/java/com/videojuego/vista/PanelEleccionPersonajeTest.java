package com.videojuego.vista;

import com.videojuego.modelo.Personaje;
import org.junit.jupiter.api.Test;

import javax.swing.JLabel;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PanelEleccionPersonajeTest {

    @Test
    void seleccionarPersonaje_actualizaSeleccionYLabels() throws Exception {
        PanelEleccionPersonaje panel = new PanelEleccionPersonaje();
        Personaje p = new Personaje("zeio", "Guerrero");

        panel.seleccionarPersonaje(p);

        Field sel = PanelEleccionPersonaje.class.getDeclaredField("personajeSeleccionado");
        sel.setAccessible(true);
        assertSame(p, sel.get(panel));

        Field lblClase = PanelEleccionPersonaje.class.getDeclaredField("lblClase");
        lblClase.setAccessible(true);
        JLabel label = (JLabel) lblClase.get(panel);
        assertTrue(label.getText().startsWith("Clase:"));
    }
}
