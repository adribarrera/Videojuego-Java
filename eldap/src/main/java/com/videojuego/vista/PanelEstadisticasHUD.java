package com.videojuego.vista;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import com.videojuego.modelo.Personaje;

/**
 * Componente visual de la interfaz de usuario (HUD) que muestra el estado vital
 * y las estadísticas actuales del jugador en tiempo real.
 */
public class PanelEstadisticasHUD extends JPanel {
    private JLabel labelVida;
    private JLabel labelAtaque;
    private JLabel labelDefensa;
    private JLabel labelCritico;
    private JLabel labelMonedas;

    public PanelEstadisticasHUD() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10)); // Alineación horizontal con separación
        setOpaque(false);

        // Inicializar los Labels con sus Iconos (Escalados a 30x30)
        labelVida = crearLabelConIcono("/assets/imagenes/Iconos/Vida.png", "0/0");
        labelAtaque = crearLabelConIcono("/assets/imagenes/Iconos/Ataque.png", "0");
        labelDefensa = crearLabelConIcono("/assets/imagenes/Iconos/Escudo.png", "0%");
        labelCritico = crearLabelConIcono("/assets/imagenes/Iconos/Critico.png", "0%");
        labelMonedas = crearLabelConIcono("/assets/imagenes/Iconos/Moneda.png", "0");

        // Añadirlos al panel
        this.add(labelVida);
        this.add(labelAtaque);
        this.add(labelDefensa);
        this.add(labelCritico);
        this.add(labelMonedas);
    }

    private JLabel crearLabelConIcono(String rutaIcono, String textoInicial) {
        JLabel label = new JLabel(textoInicial);
        label.setFont(new Font("Monospaced", Font.BOLD, 18));
        label.setForeground(Color.WHITE);

        try {
            URL url = getClass().getResource(rutaIcono);
            if (url != null) {
                ImageIcon iconoOriginal = new ImageIcon(url);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(imgEscalada));
                label.setIconTextGap(10); // Espacio entre icono y texto
            } else {
                System.err.println("No se encontró el icono del HUD: " + rutaIcono);
            }
        } catch (Exception e) {
            System.err.println("Error cargando el icono del HUD: " + rutaIcono);
        }

        return label;
    }

    /**
     * Sincroniza los valores del HUD con las estadísticas actuales de una entidad.
     * @param personaje El personaje cuyos datos queremos visualizar.
     */
    public void actualizarEstadisticas(Personaje personaje) {
        if (personaje != null) {
            labelVida.setText(personaje.getVidaActual() + "/" + personaje.getVidaMaxima());
            labelAtaque.setText(String.valueOf(personaje.getAtaque()));
            labelDefensa.setText(personaje.getDefensa() + "%");
            labelCritico.setText((int)(personaje.getProbCritico() * 100) + "%");
            labelMonedas.setText(String.valueOf(personaje.getDinero()));
        }
    }
}
