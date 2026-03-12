package com.videojuego.vista;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PanelPortadaTest {

    @Test
    void reproducirYDetenerMusica_noLanza() {
        PanelPortada panel = new PanelPortada();
        assertDoesNotThrow(panel::reproducirMusica);
        assertDoesNotThrow(panel::detenerMusica);
    }

    @Test
    void paintComponent_noLanza() {
        PanelPortada panel = new PanelPortada();
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        assertDoesNotThrow(() -> panel.paint(img.getGraphics()));
    }
}
