package com.videojuego.vista;

import com.videojuego.modelo.Personaje;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PanelEstadisticasHUDTest {

    @Test
    void actualizarEstadisticas_reflejaValores() {
        PanelEstadisticasHUD hud = new PanelEstadisticasHUD();
        Personaje p = new Personaje("zeio", "guerrero");
        p.setDinero(123);

        hud.actualizarEstadisticas(p);

        List<String> textos = Arrays.stream(hud.getComponents())
                .filter(c -> c instanceof JLabel)
                .map(c -> ((JLabel) c).getText())
                .collect(Collectors.toList());

        assertTrue(textos.contains("600/600"));
        assertTrue(textos.contains("75"));
        assertTrue(textos.contains("40%"));
        assertTrue(textos.contains("4%"));
        assertTrue(textos.contains("123"));
    }
}
