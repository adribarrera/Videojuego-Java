package com.videojuego.controlador;

import com.videojuego.modelo.*;
import com.videojuego.vista.PanelMapa;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class ControladorMovimiento {

    private Personaje personaje;
    private PanelMapa panel;
    private Colisiones colisiones;
    private int anchoPersonaje;
    private int altoPersonaje;

    // Variables para controlar la animación
    private int ticAnimacion = 0;

    // Lista para mantener el orden de las teclas pulsadas (prioridad a la última)
    private List<String> teclasPulsadas = new ArrayList<>();
    private Timer timerMovimiento;

    // AÑADIMOS Colisiones al constructor
    public ControladorMovimiento(Personaje personaje, PanelMapa panel, Colisiones colisiones, int anchoPersonaje,
            int altoPersonaje) {
        this.personaje = personaje;
        this.panel = panel;
        this.colisiones = colisiones;
        this.anchoPersonaje = anchoPersonaje;
        this.altoPersonaje = altoPersonaje;

        configurarControles();
        iniciarBucleMovimiento();
    }

    private void iniciarBucleMovimiento() {
        // Ejecución cada ~33ms que equivale a unos 30FPS (velocidad de repetición
        // nativa OS)
        timerMovimiento = new Timer(33, e -> {
            if (!teclasPulsadas.isEmpty()) {
                // Siempre nos movemos en la ÚLTIMA dirección añadida a la lista
                String direccionActual = teclasPulsadas.get(teclasPulsadas.size() - 1);
                int anchoMapa = panel.getWidth();
                int altoMapa = panel.getHeight();

                moverConLimites(direccionActual, anchoMapa, altoMapa);
                panel.repaint();
            }
        });
        timerMovimiento.start();
    }

    private void configurarControles() {
        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        String[] teclas = { "W", "S", "A", "D" };

        for (String t : teclas) {
            String dir = t.toLowerCase();

            // Presionar tecla
            inputMap.put(KeyStroke.getKeyStroke("pressed " + t), "press" + dir);
            actionMap.put("press" + dir, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!teclasPulsadas.contains(dir)) {
                        teclasPulsadas.add(dir); // Añade al final para darle la prioridad máxima
                    }
                }
            });

            // Soltar tecla
            inputMap.put(KeyStroke.getKeyStroke("released " + t), "release" + dir);
            actionMap.put("release" + dir, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    teclasPulsadas.remove(dir); // Elimina de la lista al soltar
                    if (teclasPulsadas.isEmpty()) {
                        UtilidadesAudio.resetCooldownPasos();
                    }
                }
            });
        }

        // --- TECLA DE INTERACCIÓN 'E' ---
        inputMap.put(KeyStroke.getKeyStroke("released E"), "interact");
        actionMap.put("interact", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.interactuar(); // Llama al método que creamos en PanelMapa
            }
        });
    }

    private void moverConLimites(String dir, int limiteX, int limiteY) {
        int vel = personaje.getVelocidad();
        int actualX = personaje.getPosX();
        int actualY = personaje.getPosY();

        int nuevaX = actualX;
        int nuevaY = actualY;

        switch (dir.toLowerCase()) {
            case "w":
                nuevaY -= vel;
                break;
            case "s":
                nuevaY += vel;
                break;
            case "a":
                nuevaX -= vel;
                break;
            case "d":
                nuevaX += vel;
                break;
        }

        // LÍMITES DE PANTALLA
        if (nuevaX < 0)
            nuevaX = 0;
        if (nuevaY < 0)
            nuevaY = 0;
        if (nuevaX > limiteX - anchoPersonaje)
            nuevaX = limiteX - anchoPersonaje;
        if (nuevaY > limiteY - altoPersonaje)
            nuevaY = limiteY - altoPersonaje;

        // --- LA MAGIA: Preguntamos DIRECTAMENTE a la clase Colisiones ---
        if (colisiones.verificarMovimiento(nuevaX, nuevaY, anchoPersonaje, altoPersonaje)) {
            personaje.setPosX(nuevaX);
            personaje.setPosY(nuevaY);

            // Actualizamos la Animación
            ticAnimacion++;
            if (ticAnimacion > 4) {
                ticAnimacion = 1;
            }
            UtilidadesAudio.reproducirSonido("pasos.wav");
            panel.actualizarAnimacion(dir, ticAnimacion);
        }
    }
}