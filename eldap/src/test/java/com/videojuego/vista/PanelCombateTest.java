package com.videojuego.vista;

import com.videojuego.modelo.Personaje;
import org.junit.jupiter.api.Test;

import javax.swing.JTextArea;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PanelCombateTest {

    @Test
    void cambiarVolumenMusica_noLanzaExcepcion() {
        PanelCombate panel = new PanelCombate(new Personaje("zeio", "guerrero"), "jessica");
        assertDoesNotThrow(() -> panel.cambiarVolumenMusica(100));
        assertDoesNotThrow(() -> panel.cambiarVolumenMusica(0));
    }

    @Test
    void zeiodirLinea_mantieneMaximoCuatroLineas() throws Exception {
        PanelCombate panel = new PanelCombate(new Personaje("zeio", "guerrero"), "jessica");

        Method addLine = Arrays.stream(PanelCombate.class.getDeclaredMethods())
                .filter(m -> m.getParameterCount() == 1)
                .filter(m -> m.getParameterTypes()[0] == String.class)
                .filter(m -> m.getName().toLowerCase().contains("adirlinea"))
                .findFirst()
                .orElseThrow();
        addLine.setAccessible(true);

        addLine.invoke(panel, "L1");
        addLine.invoke(panel, "L2");
        addLine.invoke(panel, "L3");
        addLine.invoke(panel, "L4");
        addLine.invoke(panel, "L5");

        Field areaField = PanelCombate.class.getDeclaredField("areaTexto");
        areaField.setAccessible(true);
        JTextArea area = (JTextArea) areaField.get(panel);

        String[] lineas = area.getText().split("\n", -1);
        assertEquals(4, lineas.length);
        assertEquals("L2", lineas[0]);
        assertEquals("L5", lineas[3]);
    }
}
