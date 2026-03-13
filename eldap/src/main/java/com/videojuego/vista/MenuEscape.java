package com.videojuego.vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.function.Consumer; // IMPORTANTE PARA EL SLIDER

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider; // IMPORTANTE PARA EL SLIDER

import com.videojuego.controlador.Boton;

/**
 * Menú de pausa que permite ajustar el volumen y salir del juego.
 * Implementa dos constructores para mayor versatilidad en diferentes pantallas.
 */
public class MenuEscape extends JDialog {

    // --- CONSTRUCTOR 1: EL ORIGINAL (Sin volumen) ---
    // Mantiene a salvo otras pantallas que no tengan música regulable
    public MenuEscape(JFrame parent, Runnable accionContinuar) {
        super(parent, "Pausa", true);
        configurarUI(parent, accionContinuar, 100, null, false);
    }

    // --- CONSTRUCTOR 2: EL NUEVO (Con Slider de Volumen) ---
    // Usamos Consumer<Integer> para devolverle el valor del slider (0 a 100) al
    // mapa
    public MenuEscape(JFrame parent, int volumenActual, Runnable accionContinuar,
            Consumer<Integer> accionCambiarVolumen) {
        super(parent, "Pausa", true);
        configurarUI(parent, accionContinuar, volumenActual, accionCambiarVolumen, true);
    }

    private void configurarUI(JFrame parent, Runnable accionContinuar, int volumenActual,
            Consumer<Integer> accionCambiarVolumen, boolean mostrarVolumen) {

        this.setSize(400, 300);
        this.setLocationRelativeTo(parent);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));

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
        panelFondoPausa.setOpaque(false);
        panelFondoPausa.setLayout(null);

        // --- NUEVO: SLIDER DE MÚSICA ---
        if (mostrarVolumen) {
            // Textito encima de la barra
            JLabel lblVolumen = new JLabel("Volumen");
            lblVolumen.setForeground(Color.WHITE);
            lblVolumen.setFont(new Font("Arial", Font.BOLD, 16));
            lblVolumen.setBounds(165, 40, 100, 20);
            panelFondoPausa.add(lblVolumen);

            // Creamos el Slider (Mínimo: 0, Máximo: 100, Valor Inicial: volumenActual)
            JSlider sliderVolumen = new JSlider(0, 100, volumenActual);
            sliderVolumen.setOpaque(false); // Transparente
            sliderVolumen.setBounds(100, 50, 200, 30);

            // Listener para detectar cuándo arrastras el circulito
            sliderVolumen.addChangeListener(e -> {
                if (accionCambiarVolumen != null) {
                    // Enviamos el valor actual de la barra de vuelta al panel
                    accionCambiarVolumen.accept(sliderVolumen.getValue());
                }
            });

            panelFondoPausa.add(sliderVolumen);
        }

        // --- BOTON SEGUIR ---
        JButton btnSeguir = Boton.crearBotonImagen("/assets/imagenes/botonContinuarEscape.png", 200, 60);
        btnSeguir.setBounds(100, 85, 200, 60); // Ajuste la posición Y para dejar sitio al slider

        btnSeguir.addActionListener(e -> {
            this.dispose();
            if (accionContinuar != null) {
                accionContinuar.run();
            }
        });

        // --- BOTON SALIR DEL JUEGO ---
        JButton btnSalirPausa = Boton.crearBotonImagen("/assets/imagenes/botonSalirEscape.png", 200, 60);
        btnSalirPausa.setBounds(100, 165, 200, 60);

        btnSalirPausa.addActionListener(e -> System.exit(0));

        panelFondoPausa.add(btnSeguir);
        panelFondoPausa.add(btnSalirPausa);

        this.setContentPane(panelFondoPausa);
    }
}