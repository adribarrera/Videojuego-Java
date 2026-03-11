package com.videojuego.vista;

import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;

import com.videojuego.modelo.Personaje;

public class VentanaPrincipal extends JFrame {

    public PanelPortada portada = new PanelPortada();
    public PanelMapa mapa = new PanelMapa();
    public PanelEleccionPersonaje seleccion = new PanelEleccionPersonaje();

    private CardLayout gestorPantallas = new CardLayout();
    private JPanel panelContenedor = new JPanel(gestorPantallas);

    // --- VARIABLES PARA LA TRANSICIÓN ---
    private JPanel panelTransicion;
    private float opacidadTransicion = 0.0f;
    private Timer timerTransicion;

    public VentanaPrincipal() {
        this.setTitle("ELDAP");

        URL rutaContenedor = getClass().getResource("/assets/imagenes/Iconos/IconoJuego.png");
        if (rutaContenedor != null) {
            Image iconoPantalla = Toolkit.getDefaultToolkit().getImage(rutaContenedor);
            this.setIconImage(iconoPantalla);
        }

        this.setSize(new Dimension(1280, 720));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelContenedor.add(portada, "Menu Principal");
        panelContenedor.add(mapa, "Pantalla Juego");
        panelContenedor.add(seleccion, "Seleccion de Personaje");

        add(panelContenedor);

        // --- CONFIGURACIÓN DEL PANEL CRISTAL (TRANSICIONES) ---
        panelTransicion = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Aplicamos la opacidad actual al "pincel"
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidadTransicion)); // Controla
                                                                                                           // la
                                                                                                           // transparencia
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelTransicion.setOpaque(false);
        this.setGlassPane(panelTransicion); // Lo ponemos por encima de toda la ventana

        portada.reproducirMusica();
    }

    // --- TRANSICIÓN ---
    // Recibe el nombre del panel al que vamos, y un "Runnable" (un bloque de
    // código) para ejecutar cuando la pantalla esté 100% negra.
    private void cambiarPanelConTransicion(String nombrePanelDestino, Runnable accionIntermedia) {
        panelTransicion.setVisible(true); // Encendemos el cristal

        // El Timer se ejecuta cada 20 milisegundos para crear la animación
        timerTransicion = new Timer(20, new ActionListener() {
            boolean oscureciendo = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (oscureciendo) {
                    opacidadTransicion = opacidadTransicion + 0.05f; // Aumentamos la oscuridad poco a poco
                    if (opacidadTransicion >= 1.0f) {
                        opacidadTransicion = 1.0f;
                        oscureciendo = false; // Empieza a aclarar

                        // --- ¡MOMENTO DE CAMBIAR LAS COSAS A OSCURAS! ---
                        gestorPantallas.show(panelContenedor, nombrePanelDestino);
                        if (accionIntermedia != null) {
                            accionIntermedia.run(); // Reproducir música, dar foco, etc.
                        }
                    }
                } else {
                    opacidadTransicion -= 0.05f; // Quitamos oscuridad
                    if (opacidadTransicion <= 0.0f) {
                        opacidadTransicion = 0.0f;
                        panelTransicion.setVisible(false); // Apagamos el cristal
                        timerTransicion.stop(); // Terminamos la animación
                    }
                }
                panelTransicion.repaint(); // Pedimos que redibuje el cristal con la nueva opacidad
            }
        });
        timerTransicion.start();
    }

    // --- MÉTODOS DE CAMBIO DE PANTALLA ACTUALIZADOS ---

    public void irEleccionPersonaje() {
        cambiarPanelConTransicion("Seleccion de Personaje", null);
    }

    public void iniciarJuegoConPersonaje(Personaje elegido) {
        cambiarPanelConTransicion("Pantalla Juego", () -> { // De esta manera, queda que voy a Pantalla Juego y que se
                                                            // ejecute: setPersonaje, reproducirMusica...
            mapa.setPersonajeJugador(elegido);
            mapa.reproducirMusica();
            mapa.requestFocus();
        });
    }

    public void iniciarCombate(String nombreBossEnemigo) {
        PanelCombate combate = new PanelCombate(mapa.getPersonaje(), nombreBossEnemigo);
        panelContenedor.add(combate, "Pantalla Combate");
        cambiarPanelConTransicion("Pantalla Combate", () -> {
            combate.requestFocus();
        });
    }

    public void volverAMapaDesdeCombate() {
        cambiarPanelConTransicion("Pantalla Juego", () -> {
            mapa.requestFocus();
            mapa.reproducirMusica();
        });
    }

    public void volverAMapaYEliminarBoss(String nombreBoss) {
        mapa.eliminarBoss(nombreBoss);
        cambiarPanelConTransicion("Pantalla Juego", () -> {
            mapa.requestFocus();
            mapa.reproducirMusica();
        });
    }

    public void cambiarAMenu() {
        cambiarPanelConTransicion("Menu Principal", () -> {
            mapa.reiniciarMapa();
            portada.reproducirMusica();
        });
    }

    public void abrirTienda(String tienda, Personaje jugadorActivo) {
        PanelTienda delikia = new PanelTienda();
        delikia.setJugadorActivo(jugadorActivo);
        panelContenedor.add(delikia, "Delik.IA");
        gestorPantallas.show(panelContenedor, "Delik.IA");
        delikia.requestFocus();
    }

    public void volverAMapaDesdeTienda() {
        gestorPantallas.show(panelContenedor, "Pantalla Juego");
        mapa.requestFocus();
    }
}