package eldap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    PanelImagen portada = new PanelImagen();

    public VentanaPrincipal() {
        this.setTitle("ELDAP"); // Título ventana
        this.setSize(new Dimension(1280, 720)); // Tamaño ventana
        this.setLocationRelativeTo(null);   // Centro ventana
        this.setResizable(false); // Evito que se redimensione
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la ventana implica terminar el programa

        this.add(portada, BorderLayout.CENTER); // Añado el panel de la portada
    }

    public static void main(String[] args) {
        VentanaPrincipal frame = new VentanaPrincipal();
        frame.setVisible(true);
    }
}