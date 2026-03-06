package com.videojuego.modelo;

import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class BossEnMapa {
    public String nombre;
    public int posX, posY;
    public int ancho = 70, alto = 70; // Tamaño visual del boss en pantalla (ajústalo)
    public ImageIcon imagen;
    public Rectangle areaInteraccion;

    public BossEnMapa(String nombre, int posX, int posY, String rutaImagen, Rectangle areaInteraccion) {
        this.nombre = nombre;
        this.posX = posX;
        this.posY = posY;
        this.areaInteraccion = areaInteraccion;

        // Cargar el sprite del boss
        java.net.URL url = getClass().getResource(rutaImagen);
        if (url != null) {
            this.imagen = new ImageIcon(url);
        } else {
            System.err.println("Imagen de Boss no encontrada: " + rutaImagen);
        }
    }
}
