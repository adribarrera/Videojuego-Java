package com.videojuego.vista;

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;
import com.videojuego.controlador.Boton;

public class PanelTienda extends JPanel {
    private Clip musicaFondo;
    private ImageIcon imagenFondo;

    private JPanel panelMenu;
    private JTextArea areaDescripcion;
    private JButton botonComprar;
    private JButton botonVolver;
    private JButton botonesItems[] = new JButton[10];

    public PanelTienda() {
        cargarRecursos();
    }

    public void cargarRecursos() {
            URL urlImagen = getClass().getResource("/assets/imagenes/fondoTienda.jpg");
            if (urlImagen != null) {
                imagenFondo = new ImageIcon(urlImagen);
            } else {
                System.err.println("ERROR: No se encontró la imagen de la tienda");
            }

        try {
            URL urlMusica = getClass().getResource("/assets/audio/mapaInst.wav");
            if (urlMusica != null) {
                AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica);
                musicaFondo = AudioSystem.getClip();
                musicaFondo.open(audioInst);
        } else {
            System.err.println("ERROR: No se encontró el audio de la tienda.");
        }
        } catch(Exception e) {
        e.printStackTrace();
        }
        
    }

    public void configurarMenuDerecho() {
        // Panel Contenedor (Mitad derecha)
        panelMenu = new JPanel();
        panelMenu.setBounds(640, 0, 640, 720);
        panelMenu.setBackground(new Color(18, 48, 56, 200));
        panelMenu.setLayout(null);
        panelMenu.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.decode("#00CBD1")));

        // Cuadrícula Items
        JPanel panelGridItems = new JPanel();
        panelGridItems.setBounds(50, 50, 540, 250);
        panelGridItems.setLayout(new GridLayout(2, 5, 10 ,10)); // 2 Filas, 5 columnas, 10 px de gap
        panelGridItems.setOpaque(false);

        String[] rutasImagenes = {
            "/assets/imagenes/vaper.png",        // Item 0
            "/assets/imagenes/mantequilla.png",  // Item 1
            "/assets/imagenes/gabardina.png",    // Item 2
            "/assets/imagenes/chatto.png",       // Item 3
            "/assets/imagenes/gafas.png",        // Item 4
            "/assets/imagenes/guanton.png"       // Item 5
        };

        for (int i = 0; i < 10; i++) {
            final int indiceItem = i; // Se necesita para el Accion Listener
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (imagenFondo != null) {
            g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

}