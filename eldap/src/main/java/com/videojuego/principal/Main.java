package com.videojuego.principal;

import com.videojuego.vista.VentanaPrincipal;

/**
 * Punto de entrada principal de la aplicación.
 * Inicializa el marco de la ventana y lo hace visible para comenzar el juego.
 */
public class Main {
    public static void main(String[] args) {
        VentanaPrincipal frame = new VentanaPrincipal();
        frame.setVisible(true);
    }
}
