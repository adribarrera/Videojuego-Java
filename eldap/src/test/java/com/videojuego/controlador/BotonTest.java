package com.videojuego.controlador;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class BotonTest {

    @Test
    void crearBotonImagen_siNoExisteRuta_seteaError() {
        JButton boton = Boton.crearBotonImagen("/no-existe.png", 10, 10);
        assertEquals("ERROR", boton.getText());
        assertEquals(Color.RED, boton.getBackground());
        assertTrue(boton.isOpaque());
    }
}
