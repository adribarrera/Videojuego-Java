package com.videojuego.controlador;

import java.awt.Cursor;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Boton {
    // --- Boton VentanaPrincipal
    // --- CREACIÓN DEL BOTÓN INCIO ---
    // Creamos la imagen original y obtener su objeto 'Image'
    public static JButton crearBotonImagen(String rutaImagen, int ancho, int alto) {

        JButton boton = new JButton();

        URL ruta = Boton.class.getResource(rutaImagen);
        if (ruta != null) {
            Image imgOriginal = new ImageIcon(ruta).getImage();
            Image imgEscalada = imgOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imgEscalada));
        } else {
            System.err.println("Error: No se encontró la imagen del botón: " + rutaImagen);
        }

        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Añadido el cursor de la mano

        return boton;
    }
}
