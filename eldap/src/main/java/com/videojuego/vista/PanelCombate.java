package com.videojuego.vista;

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;
import com.videojuego.controlador.Boton;
import com.videojuego.modelo.Enemigo;
import com.videojuego.modelo.Personaje;

public class PanelCombate extends JPanel {
    private Clip musicaCombate;
    private ImageIcon imagenFondo;
    private JTextArea areaTexto;

    private JButton botonAtacar;
    private JButton botonUsarObjeto;
    private JButton botonSalir;

    private Personaje jugador;
    private Enemigo enemigo;
    private ImageIcon imagenEnemigo;
    private PanelEstadisticasHUD hudEstadisticas; // Añadimos el HUD

    public PanelCombate(Personaje jugador, String nombreBossEnemigo) {

        this.jugador = jugador;
        this.enemigo = new Enemigo(nombreBossEnemigo);

        this.setLayout(new BorderLayout());

        cargarRecursos();

        JPanel panelInferior = crearPanelInferior(); // Llamo al metodo para crear el panel inferior

        this.add(panelInferior, BorderLayout.SOUTH); // Añado el panelInferior a PanelCombate y lo pongo abajo.

        // Añadimos el HUD en la zona superior, forzado a la izquierda usando un
        // FlowLayout
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        panelSuperior.setOpaque(false); // Transparente para que el fondo de combate no se tape
        hudEstadisticas = new PanelEstadisticasHUD();
        hudEstadisticas.actualizarEstadisticas(jugador); // Se carga con los stats vivos del jugador al empezar
        panelSuperior.add(hudEstadisticas);

        this.add(panelSuperior, BorderLayout.NORTH); // Lo ponemos en el norte
    }

    // Metodo para cargar los recursos en el constructor
    private void cargarRecursos() {
        // Fondo
        URL urlFondo = getClass().getResource("/assets/imagenes/fondoCombate.jpg");

        if (urlFondo != null) {
            imagenFondo = new ImageIcon(urlFondo);
        } else {
            System.err.println("ERROR: No se encuentra fondoCombate.jpg");
            ;
        }

        // Música
        try {
            URL urlMusica = getClass().getResource("/assets/audio/minibossInst.wav");

            if (urlMusica != null) {
                AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica);
                musicaCombate = AudioSystem.getClip();
                musicaCombate.open(audioInst);
                musicaCombate.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.err.println("ERROR: No se encuentra minibossInst.wav");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Imagen del enemigo

        String nombreBossEnemigo = this.enemigo.getNombre();
        nombreBossEnemigo = nombreBossEnemigo.replace(" ", "");

        String rutaImagenEnemigo = "/assets/imagenes/" + nombreBossEnemigo + ".png";
        URL urlEnemigo = getClass().getResource(rutaImagenEnemigo);
        if (urlEnemigo != null) {
            imagenEnemigo = new ImageIcon(urlEnemigo);
        } else {
            System.err.println("ERROR: No se encuentra la imagen del enemigo: " + rutaImagenEnemigo);
        }
    }

    // Creación de otro JPanel (para cuadro de texto)
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1280, 200)); // Tamaño
        panel.setBackground(Color.decode("#123038")); // Color personalizado
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.decode("#00CBD1"), 3)); // Borde para la caja de texto

        configurarAreaTexto(); // Se crea y configura el area de texto a insertar en este panel
        panel.add(areaTexto); // Se añade a este panel

        configurarBotones(panel);

        return panel;
    }

    // Creación del área de texto.
    private void configurarAreaTexto() {
        areaTexto = new JTextArea("Comienza el combate"); // Texto introductorio
        areaTexto.setFont(new Font("Monospaced", Font.BOLD, 22)); // Fuente
        areaTexto.setBackground(Color.decode("#123038")); // Color del fondo del area de texto
        areaTexto.setForeground(Color.WHITE); // Color de la fuente del area de texto
        areaTexto.setEditable(false); // No editable
        areaTexto.setLineWrap(true); // Para que el texto corte y no se salga del cuadro
        areaTexto.setBounds(30, 30, 600, 140); // Posicion y dimensiones // Se añade al JPanel inferior
    }

    private void configurarBotones(JPanel panel) {
        int ancho = 200;
        int alto = 80;
        int separacion = 10;

        int xInicio = 800;
        int xCentro = xInicio + (ancho / 2) + (separacion / 2); // Formula para determinar el centro debajo de dos
                                                                // botones
        int yFila1 = 15;
        int yFila2 = yFila1 + alto + separacion;

        // Aquí usamos la clase Boton en lugar del método interno que hemos borrado
        botonAtacar = Boton.crearBotonImagen("/assets/imagenes/botonAtacar.png", ancho, alto);

        botonUsarObjeto = Boton.crearBotonImagen("/assets/imagenes/botonObjeto.png", ancho, alto);
        botonAtacar.setBounds(xInicio, yFila1, ancho, alto);
        botonUsarObjeto.setBounds(xCentro, yFila2, ancho, alto);

        botonAtacar.addActionListener(e -> {

            // 1. El jugador ataca al enemigo
            this.jugador.atacar(this.enemigo);

            // 2. Comprobamos si el enemigo ha muerto (¡HAS GANADO!)
            if (!this.enemigo.estaVivo()) {
                areaTexto.setText("¡Has derrotado a " + this.enemigo.getNombre()
                        + "!\nHas ganado 100 monedas.\n¡Tus estadísticas han aumentado y te has curado!");

                // Bonificación (Ejemplo: damos 100 monedas)
                this.jugador.setDinero(this.jugador.getDinero() + 100);

                // Escalado de estadísticas tras derrotar al boss
                this.jugador.mejorarAtributosAlDerrotarBoss();

                // Actualizar el HUD al ganar
                if (hudEstadisticas != null) {
                    hudEstadisticas.actualizarEstadisticas(jugador);
                }

                // Deshabilitamos y ocultamos los botones de pelea
                botonAtacar.setVisible(false);
                botonUsarObjeto.setVisible(false);

                // Revelamos el botón para salir
                botonSalir.setVisible(true);

                // Salimos de la comprobación para evitar contraataque
                return;
            }

            // 3. Si el enemigo sigue vivo, es su turno. Ataca al jugador.
            this.enemigo.atacar(this.jugador);

            // Actualizamos el HUD porque el jugador ha recibido daño
            if (hudEstadisticas != null) {
                hudEstadisticas.actualizarEstadisticas(jugador);
            }

            // 4. Actualizamos el texto para mostrar cómo ha quedado la cosa
            areaTexto.setText("Has atacado a " + this.enemigo.getNombre() + ". Le queda " + this.enemigo.getVidaActual()
                    + " de vida.\n" +
                    this.enemigo.getNombre() + " contraataca! Te queda " + this.jugador.getVidaActual() + " de vida.");

            // 5. Comprobamos si ha ganado el enemigo (¡GAME OVER!)
            if (!this.jugador.estaVivo()) {
                areaTexto.setText("¡Has sido derrotado! Fin del juego...");
                botonAtacar.setVisible(false);
                botonUsarObjeto.setVisible(false);

                // Aunque pierdas, tienes que poder salir (mandarlo a la portada después en la
                // lógica)
                botonSalir.setVisible(true);
            }
        });

        panel.add(botonAtacar);
        panel.add(botonUsarObjeto);

        // Calculamos la posición del botón de Salir (A la derecha de Atacar)
        int xSalir = xInicio + ancho + separacion;

        // Creamos el botón (puedes usar la imagen que prefieras)
        botonSalir = Boton.crearBotonImagen("/assets/imagenes/botonSalirEscape.png", ancho, alto);
        botonSalir.setBounds(xSalir, yFila1, ancho, alto);

        // ¡Magia aquí! Lo ocultamos nada más empezar el combate
        botonSalir.setVisible(false);

        botonSalir.addActionListener(e -> {
            if (musicaCombate != null)
                musicaCombate.stop();

            // Hablamos con VentanaPrincipal para que nos saque de aquí
            VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(PanelCombate.this);

            if (!this.jugador.estaVivo()) {
                // GAME OVER: Volver al menú de inicio
                ventana.cambiarAMenu();
            } else {
                // GANAR: Volver al mapa normal
                ventana.volverAMapaDesdeCombate();
            }
        });

        panel.add(botonSalir);
        // Aquí finaliza el método configurarBotones

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        if (imagenEnemigo != null) {
            g.drawImage(imagenEnemigo.getImage(), 820, 40, 300, 270, this);
        }
    }
}