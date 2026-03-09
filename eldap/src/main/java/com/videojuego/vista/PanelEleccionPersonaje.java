package com.videojuego.vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.BorderFactory; // Añadido para poder usar el borde
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.videojuego.controlador.Boton;
import com.videojuego.modelo.Personaje;

public class PanelEleccionPersonaje extends JPanel {
    private ImageIcon imagenFondo;
    private JTextArea areaEstadisticas;

    // Aquí guardaremos la elección final del jugador
    private Personaje personajeSeleccionado = null;

    // Personajes de muestra para leer sus estadísticas
    private Personaje muestraGuerrero;
    private Personaje muestraMago;
    private Personaje muestraAsesino;

    public PanelEleccionPersonaje() {
        setLayout(null); // Layout Libre

        cargarFondo();
        inicializaPersonaje();
        configurarUI();
    }

    public void cargarFondo() {
        // Asegúrate de tener esta imagen o cambiarle el nombre
        URL urlFondo = getClass().getResource("/assets/imagenes/fondoEleccion.jpg");
        if (urlFondo != null) {
            imagenFondo = new ImageIcon(urlFondo);
        } else {
            this.setBackground(Color.decode("#123038")); // Color de fondo por defecto
        }
    }

    public void inicializaPersonaje() {
        muestraGuerrero = new Personaje("Pablo", "Guerrero");
        muestraAsesino = new Personaje("Dani", "Asesino");
        muestraMago = new Personaje("Adrian", "Mago");
    }

    public void configurarUI() {
        int anchoBtn = 200;
        int altoBtn = 80;

        // --- BOTONES DE SELECCIÓN ---
        // Asegúrate de tener estas imágenes en tu carpeta assets
        JButton btnGuerrero = Boton.crearBotonImagen("/assets/imagenes/botonGuerrero.png", anchoBtn, altoBtn);
        btnGuerrero.setBounds(100, 100, anchoBtn, altoBtn);

        JButton btnMago = Boton.crearBotonImagen("/assets/imagenes/botonMago.png", anchoBtn, altoBtn);
        btnMago.setBounds(100, 220, anchoBtn, altoBtn);

        JButton btnAsesino = Boton.crearBotonImagen("/assets/imagenes/botonAsesino.png", anchoBtn, altoBtn);
        btnAsesino.setBounds(100, 340, anchoBtn, altoBtn);

        // --- ACCIONES DE LOS BOTONES ---
        btnGuerrero.addActionListener(e -> seleccionarPersonaje(muestraGuerrero));
        btnMago.addActionListener(e -> seleccionarPersonaje(muestraMago));
        btnAsesino.addActionListener(e -> seleccionarPersonaje(muestraAsesino));

        // --- NUEVO: PANEL DE ESTADÍSTICAS (ESTILO COMBATE) ---
        JPanel panelEstadisticas = new JPanel();
        panelEstadisticas.setBounds(500, 100, 400, 320); // Mismos bounds que tenía antes tu area de texto
        panelEstadisticas.setBackground(Color.decode("#123038")); // Color de fondo sólido
        panelEstadisticas.setLayout(null); // Para posicionar el texto libremente dentro de este panel
        panelEstadisticas.setBorder(BorderFactory.createLineBorder(Color.decode("#00CBD1"), 3)); // El borde cyan

        // --- ÁREA DE ESTADÍSTICAS ---
        areaEstadisticas = new JTextArea("\n\n   Selecciona una clase para ver sus estadísticas...");
        areaEstadisticas.setFont(new Font("Monospaced", Font.BOLD, 18));
        areaEstadisticas.setBackground(Color.decode("#123038")); // Mismo fondo que el panel para que se fusione
        areaEstadisticas.setForeground(Color.WHITE);
        areaEstadisticas.setEditable(false);
        areaEstadisticas.setLineWrap(true); // Añadido para que se comporte como en combate

        // Posicionamos el texto dentro del panelEstadisticas (dejando un margen)
        areaEstadisticas.setBounds(20, 20, 360, 280);

        // Metemos el area de texto DENTRO de la caja que hace de borde
        panelEstadisticas.add(areaEstadisticas);

        // --- BOTÓN CONFIRMAR Y JUGAR ---
        JButton btnConfirmar = Boton.crearBotonImagen("/assets/imagenes/botonComenzar.png", 200, 120);
        btnConfirmar.setBounds(575, 450, 250, 80);

        btnConfirmar.addActionListener(e -> {
            if (personajeSeleccionado != null) {
                // Obtenemos la ventana principal
                VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);

                System.out.println("¡Has elegido jugar como: " + personajeSeleccionado.getClaseElegida() + "!");

                // Usamos el método puente que acabamos de crear en VentanaPrincipal
                ventana.iniciarJuegoConPersonaje(personajeSeleccionado);

            } else {
                areaEstadisticas.setText("\n\n  ¡ERROR!\n  Debes seleccionar un personaje primero.");
            }
        });

        this.add(btnGuerrero);
        this.add(btnMago);
        this.add(btnAsesino);

        // Ahora añadimos el PANEL que contiene el texto, en lugar del texto suelto
        this.add(panelEstadisticas);
        this.add(btnConfirmar);

    }

    public void seleccionarPersonaje(Personaje p) {
        this.personajeSeleccionado = p;

        // Formateamos el texto leyendo los getters de tu clase Personaje
        String texto = "\n" +
                "  Clase: " + p.getClaseElegida().toUpperCase() + "\n" +
                "  ==============================\n" +
                "  Vida Max:  " + p.getVidaMaxima() + "\n" +
                "  Ataque:    " + p.getAtaque() + "\n" +
                "  Defensa:   " + p.getDefensa() + "\n" +
                "  Crítico:   " + (int) (p.getProbCritico() * 100) + "%\n" +
                "  Velocidad: " + p.getVelocidad();

        areaEstadisticas.setText(texto);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}