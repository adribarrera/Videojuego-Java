package com.videojuego.vista;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.videojuego.controlador.Boton;

public class MenuEscape extends JDialog {

    // Pasamos el JFrame padre para centrarlo, y un "Runnable" que es la acción a
    // ejecutar al darle a seguir
    public MenuEscape(JFrame parent, Runnable accionContinuar) {
        super(parent, "Pausa", true); // true indica que es Modal (bloquea lo de atrás)

        this.setSize(400, 250);
        this.setLocationRelativeTo(parent); // Centrado respecto a la ventana padre
        this.setUndecorated(true); // Quitamos barra superior
        this.setBackground(new Color(0, 0, 0, 0)); // Fondo transparente

        JPanel panelFondoPausa = new JPanel() {
            ImageIcon iconFondo = new ImageIcon(getClass().getResource("/assets/imagenes/panelEscape.png"));

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (iconFondo != null) {
                    g.drawImage(iconFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelFondoPausa.setOpaque(false); // Transparente
        panelFondoPausa.setLayout(null); // Layout libre

        // --- BOTON SEGUIR ---
        JButton btnSeguir = Boton.crearBotonImagen("/assets/imagenes/botonContinuarEscape.png", 200, 60);
        btnSeguir.setBounds(100, 60, 200, 60);

        btnSeguir.addActionListener(e -> {
            this.dispose(); // Cerramos el menú
            if (accionContinuar != null) {
                accionContinuar.run(); // Ejecutamos la acción que nos haya pasado el panel (ej. quitar pausa)
            }
        });

        // --- BOTON SALIR DEL JUEGO ---
        JButton btnSalirPausa = Boton.crearBotonImagen("/assets/imagenes/botonSalirEscape.png", 200, 60);
        btnSalirPausa.setBounds(100, 130, 200, 60);

        btnSalirPausa.addActionListener(e -> System.exit(0)); // Cierra el juego entero

        // Añadimos los botones al panel
        panelFondoPausa.add(btnSeguir);
        panelFondoPausa.add(btnSalirPausa);

        this.setContentPane(panelFondoPausa);
    }
}