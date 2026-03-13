package com.videojuego.controlador;

import java.awt.Color; // Añadido para el color rojo de error
import java.awt.Cursor;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Factoría de utilidad para la creación y personalización de botones Swing con imágenes.
 * Centraliza la lógica de escalado de iconos y estilos visuales.
 */
public class Boton {
    /**
     * Crea un JButton configurado con una imagen escalada a dimensiones específicas.
     * @param rutaImagen Ruta al recurso de imagen dentro del classpath.
     * @param ancho Ancho deseado del botón.
     * @param alto Alto deseado del botón.
     * @return Instancia de JButton con el icono cargado y estilos predefinidos.
     */
    public static JButton crearBotonImagen(String rutaImagen, int ancho, int alto) {

        JButton boton = new JButton();

        URL ruta = Boton.class.getResource(rutaImagen);
        if (ruta != null) {
            Image imgOriginal = new ImageIcon(ruta).getImage();
            Image imgEscalada = imgOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imgEscalada));

            // Si la imagen carga bien, aplicamos la transparencia
            boton.setContentAreaFilled(false);
            boton.setBorderPainted(false);
            boton.setFocusPainted(false);
            boton.setOpaque(false);

            // Si el botón se deshabilita, evitamos que pinte un fondo raro
            boton.setDisabledIcon(new ImageIcon(imgEscalada));
        } else {
            System.err.println("Error: No se encontró la imagen del botón: " + rutaImagen);
            // Mantenemos tu lógica de seguridad original por si la imagen falla
            boton.setText("ERROR");
            boton.setBackground(Color.RED);
            boton.setOpaque(true); // Lo hacemos opaco para que se vea el rojo
        }

        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Añadido el cursor de la mano

        return boton;
    }
}