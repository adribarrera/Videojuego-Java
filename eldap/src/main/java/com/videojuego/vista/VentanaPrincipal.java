package com.videojuego.vista;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    // 1. Instanciamos TODAS las pantallas de tu juego
    public PanelPortada portada = new PanelPortada();
    public PanelMapa mapa = new PanelMapa();

    // 2. Creamos el CardLayout y el panel que servirá como "mesa" para las cartas
    private CardLayout gestorPantallas = new CardLayout();
    private JPanel panelContenedor = new JPanel(gestorPantallas);

    public VentanaPrincipal() {
        this.setTitle("ELDAP"); // Título Ventana

        URL rutaContenedor = getClass().getResource("/assets/imagenes/Pablete.png");

        if (rutaContenedor != null) {
            // Usamos Toolkit para cargar la imagen en un formato que la Ventana entienda
            // como Icono
            Image iconoPantalla = Toolkit.getDefaultToolkit().getImage(rutaContenedor);
            // Se lo asignamos a este JFrame
            this.setIconImage(iconoPantalla);
        } else {
            System.err.println("No se ha encontrado el icono de la ventana.");
        }

        this.setSize(new Dimension(1280, 720));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 3. Añadimos las pantallas al contenedor y les ponemos una "Etiqueta" (un
        // nombre en texto)
        panelContenedor.add(portada, "Menu Principal");
        panelContenedor.add(mapa, "Pantalla Juego");

        // 4. En lugar de añadir solo la portada, añadimos el contenedor a la ventana
        add(panelContenedor);
        portada.reproducirMusica(); // Reproduzco su musica
    }

    public void cambiarPanel() { // Metodo para cambiar el panel
        portada.detenerMusica();
        gestorPantallas.show(panelContenedor, "Pantalla Juego");

        mapa.requestFocus();
        mapa.reproducirMusica();
    }

    public void cambiarAMenu() { // Metodo para cambiar a Menu
        gestorPantallas.show(panelContenedor, "Menu Principal");
    }

    public void iniciarCombate(String nombreBossEnemigo) {
        mapa.detenerMusica(); // Quitamos la musiquita pacifista del mapa

        // Instanciamos un Panel de combate NUEVO cada vez que peleemos
        // (para que no te se te guarde la vida baja de un combate anterior)
        PanelCombate combate = new PanelCombate();

        // Lo "pegamos" en la baraja de tu CardLayout
        panelContenedor.add(combate, "Pantalla Combate");

        // Hacemos que se muestre en primer plano
        gestorPantallas.show(panelContenedor, "Pantalla Combate");

        // Le pasamos el foco a esa pantalla por si hiciera falta escuchar teclas
        combate.requestFocus();

        // TODO para después: modificar el PanelCombate para que se entere que se
        // está peleando exactamente contra 'nombreBossEnemigo' e inicialice al Enemigo
        // correcto.
    }

}