package vista;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    // 1. Instanciamos TODAS las pantallas de tu juego
    public PanelImagen portada = new PanelImagen();
    public PanelMapa mapa = new PanelMapa();

    // 2. Creamos el CardLayout y el panel que servirá como "mesa" para las cartas
    private CardLayout gestorPantallas = new CardLayout();
    private JPanel panelContenedor = new JPanel(gestorPantallas);

    public VentanaPrincipal() {
        this.setTitle("ELDAP");
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
    }

    public void cambiarPanel() { // Metodo para cambiar el panel
        gestorPantallas.show(panelContenedor, "Pantalla Juego");

        mapa.requestFocus();

    }
}