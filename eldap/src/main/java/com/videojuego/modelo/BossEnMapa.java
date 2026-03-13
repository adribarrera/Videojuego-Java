package com.videojuego.modelo;

import javax.swing.ImageIcon;
import java.awt.Rectangle;

/**
 * Representación simplificada de un jefe tal como aparece en el mapa antes del combate.
 * Define su posición visual y su área de interacción.
 */
public class BossEnMapa {
    public String nombre;
    public int posX, posY;
    public int ancho = 70, alto = 70; // Tamaño visual del boss en pantalla (ajústalo)
    public ImageIcon imagen;
    public Rectangle areaInteraccion;

    /**
     * Crea un jefe ubicado en el mapa con su correspondiente área de interacción.
     * @param nombre Identificador del jefe.
     * @param posX Coordenada X inicial.
     * @param posY Coordenada Y inicial.
     * @param rutaImagen Sprite a mostrar.
     * @param areaInteraccion Rectángulo que define el rango de activación.
     */
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
