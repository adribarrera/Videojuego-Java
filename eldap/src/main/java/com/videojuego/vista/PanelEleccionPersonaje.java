package com.videojuego.vista;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import com.videojuego.controlador.Boton;
import com.videojuego.modelo.Personaje;

public class PanelEleccionPersonaje extends JPanel {
    private ImageIcon imagenFondo;
    private JLabel labelFotoPersonaje;
    
    // Labels para las estadísticas
    private JLabel lblClase;
    private JLabel lblVida;
    private JLabel lblAtaque;
    private JLabel lblDefensa;
    private JLabel lblCritico;
    private JLabel lblVelocidad;

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

        // --- BOTONES DE SELECCIÓN (IZQUIERDA) ---
        JButton btnGuerrero = Boton.crearBotonImagen("/assets/imagenes/BotonPablo.png", anchoBtn, altoBtn);
        btnGuerrero.setBounds(100, 150, anchoBtn, altoBtn);
        btnGuerrero.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnMago = Boton.crearBotonImagen("/assets/imagenes/BotonAdri.png", anchoBtn, altoBtn);
        btnMago.setBounds(100, 290, anchoBtn, altoBtn);
        btnMago.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnAsesino = Boton.crearBotonImagen("/assets/imagenes/BotonDani.png", anchoBtn, altoBtn);
        btnAsesino.setBounds(100, 430, anchoBtn, altoBtn);
        btnAsesino.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- ACCIONES DE LOS BOTONES ---
        btnGuerrero.addActionListener(e -> seleccionarPersonaje(muestraGuerrero));
        btnMago.addActionListener(e -> seleccionarPersonaje(muestraMago));
        btnAsesino.addActionListener(e -> seleccionarPersonaje(muestraAsesino));

        // --- PANEL DERECHO (ESTILO TIENDA) ---
        JPanel panelDerecho = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(18, 48, 56, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        panelDerecho.setBounds(635, 0, 645, 720); // Posición y dimensiones similares a Tienda
        panelDerecho.setBackground(new Color(18, 48, 56, 200));
        panelDerecho.setLayout(null);
        panelDerecho.setOpaque(false);

        // Panel Contenedor de la Foto
        JPanel panelContenedorFoto = new JPanel();
        panelContenedorFoto.setBounds(172, 70, 300, 250); // centrado aprox (645 ancho panel -> (645-300)/2 = 172.5)
        panelContenedorFoto.setLayout(new BorderLayout());
        panelContenedorFoto.setOpaque(false); // Fondo transparente
        
        labelFotoPersonaje = new JLabel("", SwingConstants.CENTER); // inicializa vacío
        panelContenedorFoto.add(labelFotoPersonaje, BorderLayout.CENTER);
        panelDerecho.add(panelContenedorFoto);

        // Area Texto
        JPanel panelContenedorTexto = new JPanel();
        panelContenedorTexto.setBounds(50, 340, 540, 210);
        panelContenedorTexto.setLayout(new BorderLayout());
        panelContenedorTexto.setBorder(BorderFactory.createLineBorder(Color.decode("#00CBD1"), 2));
        panelContenedorTexto.setBackground(Color.decode("#123038"));

        lblClase = new JLabel("Selecciona una clase...");
        lblClase.setFont(new Font("Monospaced", Font.BOLD, 18));
        lblClase.setForeground(Color.WHITE);
        lblClase.setHorizontalAlignment(SwingConstants.CENTER);
        lblClase.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        JPanel panelStats = new JPanel();
        panelStats.setLayout(new GridLayout(3, 2, 5, 5));
        panelStats.setOpaque(false);
        panelStats.setBorder(BorderFactory.createEmptyBorder(5, 40, 10, 10));

        lblVida = crearLabelStat("/assets/imagenes/Iconos/Vida.png", "Vida Max: -");
        lblAtaque = crearLabelStat("/assets/imagenes/Iconos/Ataque.png", "Ataque: -");
        lblDefensa = crearLabelStat("/assets/imagenes/Iconos/Escudo.png", "Defensa: -");
        lblCritico = crearLabelStat("/assets/imagenes/Iconos/Critico.png", "Crítico: -");
        lblVelocidad = crearLabelStat("/assets/imagenes/Iconos/Velocidad.png", "Velocidad: -");

        panelStats.add(lblVida);
        panelStats.add(lblAtaque);
        panelStats.add(lblDefensa);
        panelStats.add(lblCritico);
        panelStats.add(lblVelocidad);
        panelStats.add(new JLabel("")); // Empty label para completar el grid de 3x2

        panelContenedorTexto.add(lblClase, BorderLayout.NORTH);
        panelContenedorTexto.add(panelStats, BorderLayout.CENTER);
        panelDerecho.add(panelContenedorTexto);

        // --- BOTÓN CONFIRMAR Y JUGAR ---
        JButton btnConfirmar = Boton.crearBotonImagen("/assets/imagenes/botonComenzar.png", 200, 80);
        btnConfirmar.setBounds(222, 570, 200, 80); // Centrado en panel derecho (645/2 - 200/2)
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnConfirmar.addActionListener(e -> {
            com.videojuego.controlador.UtilidadesAudio.reproducirSonido("audioItem.wav");
            if (personajeSeleccionado != null) {
                // Instanciamos un Personaje NUEVO basado en la elección para resetear stats
                Personaje nuevoPersonaje = new Personaje(personajeSeleccionado.getNombre(),
                        personajeSeleccionado.getClaseElegida());

                // Obtenemos la ventana principal
                VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);

                System.out.println("¡Has elegido jugar como: " + nuevoPersonaje.getClaseElegida() + "!");

                // Usamos el método puente que acabamos de crear en VentanaPrincipal
                ventana.iniciarJuegoConPersonaje(nuevoPersonaje);

            } else {
                lblClase.setText("¡ERROR! Selecciona un personaje primero.");
                lblClase.setForeground(Color.RED);
            }
        });

        panelDerecho.add(btnConfirmar);

        this.add(btnGuerrero);
        this.add(btnMago);
        this.add(btnAsesino);

        // Añadimos el panel derecho al frame general
        this.add(panelDerecho);
    }

    public void seleccionarPersonaje(Personaje p) {
        com.videojuego.controlador.UtilidadesAudio.reproducirSonido("audioItem.wav");
        this.personajeSeleccionado = p;

        lblClase.setForeground(Color.WHITE);
        lblClase.setText("Clase: " + p.getClaseElegida().toUpperCase());
        lblVida.setText("Vida Max: " + p.getVidaMaxima());
        lblAtaque.setText("Ataque: " + p.getAtaque());
        lblDefensa.setText("Defensa: " + p.getDefensa() + "%");
        lblCritico.setText("Crítico: " + (int) (p.getProbCritico() * 100) + "%");
        lblVelocidad.setText("Velocidad: " + p.getVelocidad());

        // Actualizar foto
        String rutaFoto = "";
        if (p.getClaseElegida().equalsIgnoreCase("Guerrero")) {
            rutaFoto = "/assets/imagenes/seleccionarPablo.png";
        } else if (p.getClaseElegida().equalsIgnoreCase("Mago")) {
            rutaFoto = "/assets/imagenes/seleccionarAdri.png";
        } else if (p.getClaseElegida().equalsIgnoreCase("Asesino")) {
            rutaFoto = "/assets/imagenes/seleccionarDani.png";
        }

        try {
            URL url = getClass().getResource(rutaFoto);
            if (url != null) {
                Image imagen = new ImageIcon(url).getImage();
                Image imagenEscalada = imagen.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                labelFotoPersonaje.setIcon(new ImageIcon(imagenEscalada));
            } else {
                labelFotoPersonaje.setIcon(null);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la foto: " + rutaFoto);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JLabel crearLabelStat(String rutaIcono, String textoInicial) {
        JLabel label = new JLabel(textoInicial);
        label.setFont(new Font("Monospaced", Font.BOLD, 18));
        label.setForeground(Color.WHITE);

        if (rutaIcono != null) {
            try {
                URL url = getClass().getResource(rutaIcono);
                if (url != null) {
                    ImageIcon iconoOriginal = new ImageIcon(url);
                    Image imgEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(imgEscalada));
                    label.setIconTextGap(10);
                }
            } catch (Exception e) {
                System.err.println("Error cargando el icono: " + rutaIcono);
            }
        }
        return label;
    }
}