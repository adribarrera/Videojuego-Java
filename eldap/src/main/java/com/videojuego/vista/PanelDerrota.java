package com.videojuego.vista;

import java.awt.Graphics;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PanelDerrota extends JPanel {

    private ImageIcon imagenFondo;

    public PanelDerrota() {
        URL url = getClass().getResource("/assets/imagenes/pantallaDerrota.png");
        if (url != null) {
            imagenFondo = new ImageIcon(url);
        } else {
            System.err.println("ERROR: No se encontró pantallaDerrota.png");
        }

        // Tras 4 segundos, volvemos al menú principal automáticamente
        Timer timer = new Timer(4000, e -> {
            VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
            if (ventana != null) {
                ventana.cambiarAMenu();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), null);
        }
    }
}
