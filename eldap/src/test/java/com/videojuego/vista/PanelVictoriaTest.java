package com.videojuego.vista;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PanelVictoriaTest {

    @Test
    void paintComponent_noLanza() {
        PanelVictoria panel = new PanelVictoria();
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        assertDoesNotThrow(() -> panel.paint(img.getGraphics()));
    }
}
