package com.videojuego.vista;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.net.URL;
import com.videojuego.controlador.Boton;
import com.videojuego.modelo.Enemigo;
import com.videojuego.modelo.Personaje;
import com.videojuego.modelo.Item;
import java.util.List;

/**
 * Panel que gestiona la escena de combate por turnos.
 * Se encarga de la renderización de personajes, gestión de HUD,
 * animaciones de daño y lógica de flujo de batalla.
 */
public class PanelCombate extends JPanel {
    private ImageIcon imagenFondo;
    private JTextArea areaTexto;

    private JButton botonAtacar;
    private JButton botonUsarObjeto;
    private JButton botonSalir;

    private Personaje jugador;
    private Enemigo enemigo;
    private ImageIcon imagenJugador;
    private ImageIcon imagenEnemigo;
    private PanelEstadisticasHUD hudEstadisticas; // Añadimos el HUD

    // -- Animaciones --
    private int offsetXEnemigo = 0;
    private int offsetYEnemigo = 0;
    private int offsetXPantalla = 0;
    private int offsetYPantalla = 0;

    // --- Componentes para Objetos ---
    private JButton[] botonesItems = new JButton[2];
    private int itemSeleccionadoCombate = -1;
    private ImageIcon[] iconosItemsNormales = new ImageIcon[2];
    private ImageIcon[] iconosItemsSeleccionados = new ImageIcon[2];
    private JButton botonConfirmarObjeto;
    private JButton botonCancelarObjeto;

    // --- Variable de Volumen ---
    private int volumenActual = 100;

    /**
     * Inicializa el combate entre el jugador y un jefe específico.
     * 
     * @param jugador           Instancia del jugador actual.
     * @param nombreBossEnemigo Nombre del jefe a enfrentar.
     */
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

        // --- CONFIGURACIÓN PARA EL MENÚ DE PAUSA ---
        this.setFocusable(true); // Vital para que el panel escuche el teclado
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(PanelCombate.this);

                    MenuEscape menu = new MenuEscape(
                            ventana,
                            volumenActual,
                            () -> {
                                PanelCombate.this.requestFocus(); // Devolvemos el foco al salir
                            },
                            (nuevoVolumen) -> {
                                cambiarVolumenMusica(nuevoVolumen); // Movemos el slider
                            });

                    menu.setVisible(true);
                }
            }
        });
    }

    /**
     * Carga las imágenes y la música necesarias para el combate.
     * Selecciona los sprites según la clase del jugador y el tipo de jefe.
     */
    private void cargarRecursos() {
        // Fondo
        URL urlFondo = getClass().getResource("/assets/imagenes/fondoCombate.jpg");

        if (urlFondo != null) {
            imagenFondo = new ImageIcon(urlFondo);
        } else {
            System.err.println("ERROR: No se encuentra fondoCombate.jpg");
        }

        try {
            if (this.enemigo.getNombre().equalsIgnoreCase("Sergio")) {
                com.videojuego.controlador.ControladorAudio.getInstance()
                        .reproducirMusicaAmbiental("/assets/audio/bossfinalInst.wav");
            } else {
                com.videojuego.controlador.ControladorAudio.getInstance()
                        .reproducirMusicaAmbiental("/assets/audio/minibossInst.wav");
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

        // Imagen del jugador dependiendo de su clase
        String claseElegida = this.jugador.getClaseElegida();
        String nombreImgJugador = "Pablo"; // Por defecto guerrero

        if (claseElegida != null) {
            switch (claseElegida.toLowerCase()) {
                case "guerrero":
                    nombreImgJugador = "Pablo";
                    break;
                case "mago":
                    nombreImgJugador = "Adri";
                    break;
                case "asesino":
                    nombreImgJugador = "Dani";
                    break;
            }
        }

        String rutaImagenJugador = "/assets/imagenes/Sprites/Combate/" + nombreImgJugador + ".png";
        URL urlJugador = getClass().getResource(rutaImagenJugador);
        if (urlJugador != null) {
            imagenJugador = new ImageIcon(urlJugador);
        } else {
            System.err.println("ERROR: No se encuentra la imagen del jugador: " + rutaImagenJugador);
        }
    }

    /**
     * Crea el panel inferior que contiene el cuadro de texto y los botones de
     * acción.
     * 
     * @return El panel configurado para la zona sur de la pantalla.
     */
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
        areaTexto.setFont(new Font("Monospaced", Font.BOLD, 18)); // Fuente reducida de 22 a 18 para que quepan 5 líneas
        areaTexto.setBackground(Color.decode("#123038")); // Color del fondo del area de texto
        areaTexto.setForeground(Color.WHITE); // Color de la fuente del area de texto
        areaTexto.setEditable(false); // No editable
        areaTexto.setLineWrap(true); // Para que el texto corte y no se salga del cuadro
        areaTexto.setWrapStyleWord(true); // Corta por palabras, no por letras
        areaTexto.setBounds(30, 30, 600, 140); // Posicion y dimensiones // Se añade al JPanel inferior
    }

    private void configurarBotones(JPanel panel) {
        int ancho = 200;
        int alto = 80;
        int separacion = 20;

        int xFila = 740;
        int yBotones = (200 - alto) / 2;

        // Aquí usamos la clase Boton en lugar del método interno que hemos borrado
        botonAtacar = Boton.crearBotonImagen("/assets/imagenes/botonAtacar.png", ancho, alto);

        botonUsarObjeto = Boton.crearBotonImagen("/assets/imagenes/botonObjeto.png", ancho, alto);
        botonAtacar.setBounds(xFila, yBotones, ancho, alto);
        botonUsarObjeto.setBounds(xFila + ancho + separacion, yBotones, ancho, alto);

        // --- Botones e items para el Submenú de Inventario ---
        int xItemsBase = xFila - 50;
        for (int i = 0; i < 2; i++) {
            botonesItems[i] = new JButton();
            botonesItems[i].setBounds(xItemsBase + i * 140, 40, 120, 120); // Tamaño grande para que parezca inventario
            botonesItems[i].setContentAreaFilled(false);
            botonesItems[i].setBorderPainted(false);
            botonesItems[i].setFocusPainted(false);
            botonesItems[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            botonesItems[i].setVisible(false); // Ocultos de inicio
            final int index = i;
            botonesItems[i].addActionListener(e -> seleccionarItemInventario(index));
            panel.add(botonesItems[i]);
        }

        // Boton Confirmar y Cancelar que aparecen al usar Objeto
        int xConfirm = xItemsBase + 280;
        botonConfirmarObjeto = Boton.crearBotonImagen("/assets/imagenes/botonObjeto.png", 160, 60);
        botonConfirmarObjeto.setBounds(xConfirm, 40, 160, 60);
        botonConfirmarObjeto.setVisible(false);
        botonConfirmarObjeto.addActionListener(e -> usarItemSeleccionado());
        panel.add(botonConfirmarObjeto);

        botonCancelarObjeto = Boton.crearBotonImagen("/assets/imagenes/botonVolver.png", 160, 60);
        botonCancelarObjeto.setBounds(xConfirm, 100, 160, 60);
        botonCancelarObjeto.setVisible(false);
        botonCancelarObjeto.addActionListener(e -> cancelarMenuObjeto());
        panel.add(botonCancelarObjeto);

        // --- Fin botones Submenú ---

        // Acción al atacar (Ahora enlazada a la función de turnos temporizados)
        botonAtacar.addActionListener(e -> {
            ejecutarTurnoJugador(() -> {

                String clase = this.jugador.getClaseElegida().toLowerCase();
                if (clase.equals("guerrero")) {
                    com.videojuego.controlador.UtilidadesAudio.reproducirSonido("espadaPablo.wav");
                } else if (clase.equals("mago")) {
                    com.videojuego.controlador.UtilidadesAudio.reproducirSonido("magiaAdrian.wav");
                } else if (clase.equals("asesino")) {
                    com.videojuego.controlador.UtilidadesAudio.reproducirSonido("cuchillaDani.wav");
                }

                int vidaEnemigoAntes = enemigo.getVidaActual();
                boolean critico = this.jugador.atacar(this.enemigo);

                if (critico) {
                    com.videojuego.controlador.UtilidadesAudio.reproducirSonido("critico.wav");
                } else if (enemigo.getVidaActual() < vidaEnemigoAntes) {
                    com.videojuego.controlador.UtilidadesAudio.reproducirSonido("hit.wav");
                }

                if (enemigo.getVidaActual() < vidaEnemigoAntes) {
                    animarVibracionEnemigo();
                }
                String prefijoCritico = critico ? "¡GOLPE CRÍTICO!\n" : "";
                if (enemigo.estaVivo()) {
                    areaTexto.setText(prefijoCritico + "Has atacado a " + this.enemigo.getNombre() + ". Le queda "
                            + this.enemigo.getVidaActual() + " de vida.");
                } else {
                    areaTexto.setText(
                            prefijoCritico + "Has atacado a " + this.enemigo.getNombre() + " con un golpe letal.");
                }
            });
        });

        // Acción al pulsar el botón principal Usar Objeto
        botonUsarObjeto.addActionListener(e -> activarMenuObjetos());

        panel.add(botonAtacar);
        panel.add(botonUsarObjeto);

        // Calculamos la posición del botón de Salir (Centrado en el mismo espacio)
        int xSalir = 850;

        // Creamos el botón (puedes usar la imagen que prefieras)
        botonSalir = Boton.crearBotonImagen("/assets/imagenes/BotonVolverAlMapa.png", ancho, alto);
        botonSalir.setBounds(xSalir, yBotones, ancho, alto);

        // Inicialmente oculto hasta el final del combate
        botonSalir.setVisible(false);

        botonSalir.addActionListener(e -> {
            // Hablamos con VentanaPrincipal para que nos saque de aquí
            VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(PanelCombate.this);

            if (!this.jugador.estaVivo()) {
                // GAME OVER: Volver al menú de inicio
                ventana.cambiarAMenu();
            } else if (!this.enemigo.estaVivo()) {
                // GANAR: Volver al mapa normal y eliminar al boss
                ventana.volverAMapaYEliminarBoss(this.enemigo.getNombre());
            } else {
                // VOLVER ESCAPE: Volver al mapa normal
                ventana.volverAMapaDesdeCombate();
            }
        });

        panel.add(botonSalir);
        // Aquí finaliza el método configurarBotones

    }

    // --- MÉTODOS DE AUDIO ---
    public void cambiarVolumenMusica(int porcentaje) {
        if (this.volumenActual == porcentaje)
            return; // Optimización anti-lag

        this.volumenActual = porcentaje;
        com.videojuego.controlador.ControladorAudio.getInstance().setVolumenGlobal(porcentaje);
    }

    // --- METODOS PROPIOS DEL INVENTARIO DE COMBATE ---
    private ImageIcon cargarIconoEscalado(String ruta, int ancho, int alto) {
        try {
            URL url = getClass().getResource(ruta);
            if (url != null) {
                Image imagen = new ImageIcon(url).getImage();
                return new ImageIcon(imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
            }
        } catch (Exception e) {
            System.err.println("No carga: " + ruta);
        }
        return null;
    }

    private void activarMenuObjetos() {
        List<Item> inventario = jugador.getInventario();
        if (inventario.isEmpty()) {
            añadirLinea("Buscas en la mochila pero no llevas ningún objeto.");
            return;
        }

        botonAtacar.setVisible(false);
        botonUsarObjeto.setVisible(false);

        itemSeleccionadoCombate = -1;

        for (int i = 0; i < 2; i++) {
            if (i < inventario.size()) {
                Item item = inventario.get(i);
                String rutaNormal = item.getRutaImagen();
                String rutaSelect = rutaNormal.replace("/Normal/", "/Seleccionado/");

                iconosItemsNormales[i] = cargarIconoEscalado(rutaNormal, 120, 120);
                iconosItemsSeleccionados[i] = cargarIconoEscalado(rutaSelect, 120, 120);

                botonesItems[i].setIcon(iconosItemsNormales[i]);
                botonesItems[i].setVisible(true);
            } else {
                botonesItems[i].setVisible(false);
            }
        }

        botonConfirmarObjeto.setVisible(true);
        botonCancelarObjeto.setVisible(true);
        añadirLinea("Has abierto la mochila, elige el objeto a usar.");
    }

    private void seleccionarItemInventario(int indice) {
        com.videojuego.controlador.UtilidadesAudio.reproducirSonido("audioItem.wav");
        List<Item> inv = jugador.getInventario();
        if (indice >= inv.size())
            return;

        for (int i = 0; i < 2; i++) {
            if (botonesItems[i].isVisible())
                botonesItems[i].setIcon(iconosItemsNormales[i]);
        }
        botonesItems[indice].setIcon(iconosItemsSeleccionados[indice]);
        itemSeleccionadoCombate = indice;

        Item obj = inv.get(indice);
        añadirLinea(obj.getNombre() + ": " + obj.getDescripcion());
    }

    private void usarItemSeleccionado() {
        if (itemSeleccionadoCombate == -1) {
            añadirLinea("Selecciona un objeto de la lista.");
            return;
        }

        ejecutarTurnoJugador(() -> {
            int vidaEnemigoAntes = enemigo.getVidaActual();
            String mensajeItem = jugador.usarItem(itemSeleccionadoCombate, enemigo);

            cerrarMenuObjeto(true);

            if (enemigo.getVidaActual() < vidaEnemigoAntes) {
                animarVibracionEnemigo();
            }
            añadirLinea(mensajeItem);
        });
    }

    private void cancelarMenuObjeto() {
        cerrarMenuObjeto(true);
        añadirLinea("Decides guardarte la mochila. ¿Atacas o esperas?");
    }

    private void cerrarMenuObjeto(boolean restaurarBotonesPrincipales) {
        for (JButton b : botonesItems) {
            if (b != null)
                b.setVisible(false);
        }
        botonConfirmarObjeto.setVisible(false);
        botonCancelarObjeto.setVisible(false);

        if (restaurarBotonesPrincipales) {
            botonAtacar.setVisible(true);
            botonUsarObjeto.setVisible(true);
        }
    }

    /**
     * Añade una nueva línea al registro de combate con un efecto de desplazamiento.
     * Mantiene un máximo de 4 líneas visibles para evitar desbordamientos.
     * 
     * @param nuevaLinea Texto a mostrar.
     */
    private void añadirLinea(String nuevaLinea) {
        // Si la nueva línea ya trae saltos explícitos, los procesamos uno a uno
        if (nuevaLinea.contains("\n")) {
            for (String parte : nuevaLinea.split("\n", -1)) {
                añadirLinea(parte);
            }
            return;
        }

        // --- Lógica de Auto-Wrap manual ---
        // El JTextArea tiene 600px de ancho y fuente Monospaced 18px Bold.
        // Aproximadamente caben 53-55 caracteres por línea. Usaremos un margen de
        // seguridad.
        int MAX_CARACTERES_POR_FILA = 50;

        if (nuevaLinea.length() > MAX_CARACTERES_POR_FILA) {
            String parte1 = nuevaLinea.substring(0, MAX_CARACTERES_POR_FILA);
            String parte2 = nuevaLinea.substring(MAX_CARACTERES_POR_FILA);
            // Buscamos el último espacio para no cortar palabras
            int ultimoEspacio = parte1.lastIndexOf(' ');
            if (ultimoEspacio > 30) { // Si hay un espacio razonable, cortamos ahí
                añadirLinea(nuevaLinea.substring(0, ultimoEspacio));
                añadirLinea(nuevaLinea.substring(ultimoEspacio + 1));
            } else {
                añadirLinea(parte1);
                añadirLinea(parte2);
            }
            return;
        }

        String actual = areaTexto.getText();
        String[] lineas = actual.isEmpty() ? new String[0] : actual.split("\n", -1);

        int MAX_LINEAS_VISIBLES = 4;
        String resultado;
        if (lineas.length >= MAX_LINEAS_VISIBLES) {
            StringBuilder sb = new StringBuilder();
            // Descartamos la primera línea y sumamos el resto
            for (int i = 1; i < lineas.length; i++) {
                sb.append(lineas[i]).append("\n");
            }
            sb.append(nuevaLinea);
            resultado = sb.toString();
        } else {
            resultado = actual.isEmpty() ? nuevaLinea : actual + "\n" + nuevaLinea;
        }
        areaTexto.setText(resultado);
    }

    // --- ANIMACIONES Y FLUJO DE TURNOS ---
    private void animarVibracionEnemigo() {
        Timer timerVibracion = new Timer(50, null);
        timerVibracion.addActionListener(new java.awt.event.ActionListener() {
            int ticks = 0;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (ticks >= 8) {
                    offsetXEnemigo = 0;
                    offsetYEnemigo = 0;
                    repaint();
                    timerVibracion.stop();
                } else {
                    offsetXEnemigo = (int) (Math.random() * 20 - 10);
                    offsetYEnemigo = (int) (Math.random() * 20 - 10);
                    repaint();
                    ticks++;
                }
            }
        });
        timerVibracion.start();
    }

    private void animarVibracionPantalla() {
        Timer timerVibracion = new Timer(50, null);
        timerVibracion.addActionListener(new java.awt.event.ActionListener() {
            int ticks = 0;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (ticks >= 8) {
                    offsetXPantalla = 0;
                    offsetYPantalla = 0;
                    repaint();
                    timerVibracion.stop();
                } else {
                    offsetXPantalla = (int) (Math.random() * 20 - 10);
                    offsetYPantalla = (int) (Math.random() * 20 - 10);
                    repaint();
                    ticks++;
                }
            }
        });
        timerVibracion.start();
    }

    /**
     * Orquesta el flujo de un turno: acción del jugador, chequeo de
     * victoria/derrota
     * y el contraataque automático del enemigo.
     * 
     * @param accionJugador Bloque de código con la acción seleccionada.
     */
    private void ejecutarTurnoJugador(Runnable accionJugador) {
        // Bloqueamos botones para evitar spam
        botonAtacar.setEnabled(false);
        botonUsarObjeto.setEnabled(false);
        botonConfirmarObjeto.setEnabled(false);
        botonCancelarObjeto.setEnabled(false);
        for (JButton b : botonesItems) {
            if (b != null)
                b.setEnabled(false);
        }

        // Ejecutamos la accion del jugador
        accionJugador.run();

        // Actualizamos UI
        repaint();
        if (hudEstadisticas != null)
            hudEstadisticas.actualizarEstadisticas(jugador);

        // Verificamos suicidio instántaneo
        if (!this.jugador.estaVivo()) {
            animarVibracionPantalla();
            añadirLinea("¡Has sido derrotado! Fin del juego...");
            cerrarMenuObjeto(false);
            botonAtacar.setVisible(false);
            botonUsarObjeto.setVisible(false);
            // Timer de 2.5s antes de ir a PanelDerrota
            Timer timerDerrota = new Timer(2500, ev -> {
                VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(PanelCombate.this);
                if (ventana != null)
                    ventana.irADerrota();
            });
            timerDerrota.setRepeats(false);
            timerDerrota.start();
            return;
        }

        // Verificamos si el enemigo murió
        if (!this.enemigo.estaVivo()) {

            // Recompensa de monedas dependiente del boss
            int monedasGanadas = 0;
            String nombreJefe = this.enemigo.getNombre().toLowerCase();

            if (nombreJefe.contains("jessica")) {
                monedasGanadas = 80 + (int) (Math.random() * 31); // 80 - 110
            } else if (nombreJefe.contains("juancarlos") || nombreJefe.contains("juan carlos")) {
                monedasGanadas = 120 + (int) (Math.random() * 41); // 120 - 160
            } else if (nombreJefe.contains("soraya")) {
                monedasGanadas = 200 + (int) (Math.random() * 41); // 200 - 240
            } else {
                monedasGanadas = 100; // Default just in case
            }

            añadirLinea("¡Has derrotado a " + this.enemigo.getNombre()
                    + " y consigues " + monedasGanadas + " oro!");
            añadirLinea("¡Mejoras tus stats y te curas por completo!");

            this.jugador.setDinero(this.jugador.getDinero() + monedasGanadas);
            this.jugador.mejorarAtributosAlDerrotarBoss();
            if (hudEstadisticas != null)
                hudEstadisticas.actualizarEstadisticas(jugador);

            cerrarMenuObjeto(false);
            botonAtacar.setVisible(false);
            botonUsarObjeto.setVisible(false);

            if (nombreJefe.contains("sergio")) {
                // BOSS FINAL: transición automática a pantalla de Victoria
                Timer timerVictoria = new Timer(2500, ev -> {
                    VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(PanelCombate.this);
                    if (ventana != null)
                        ventana.irAVictoria();
                });
                timerVictoria.setRepeats(false);
                timerVictoria.start();
            } else {
                // BOSS INTERMEDIO: mostrar botón de salir al mapa
                botonSalir.setVisible(true);
                botonSalir.setEnabled(true);
            }
            return;
        }

        // Si sobrevive, programar contraataque del enemigo
        Timer timerEnemigo = new Timer(1500, e -> {
            com.videojuego.controlador.UtilidadesAudio.reproducirSonido("golpeEnemigo.wav");

            int vidaJugadorAntes = this.jugador.getVidaActual();
            boolean criticoEnemigo = this.enemigo.atacar(this.jugador);

            if (criticoEnemigo) {
                com.videojuego.controlador.UtilidadesAudio.reproducirSonido("critico.wav");
            } else if (this.jugador.getVidaActual() < vidaJugadorAntes) {
                com.videojuego.controlador.UtilidadesAudio.reproducirSonido("hit.wav");
            }

            if (this.jugador.getVidaActual() < vidaJugadorAntes) {
                animarVibracionPantalla();
            }

            if (hudEstadisticas != null)
                hudEstadisticas.actualizarEstadisticas(jugador);

            String prefijoCriticoEnemigo = criticoEnemigo ? "¡GOLPE CRÍTICO!\n" : "";
            añadirLinea(prefijoCriticoEnemigo + this.enemigo.getNombre()
                    + " contraataca! Te queda " + this.jugador.getVidaActual() + " de vida.");

            if (!this.jugador.estaVivo()) {
                añadirLinea("¡Has sido derrotado! Fin del juego...");
                cerrarMenuObjeto(false);
                botonAtacar.setVisible(false);
                botonUsarObjeto.setVisible(false);
                // Timer de 2.5s antes de ir a PanelDerrota
                Timer timerDerrota = new Timer(2500, ev -> {
                    VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(PanelCombate.this);
                    if (ventana != null)
                        ventana.irADerrota();
                });
                timerDerrota.setRepeats(false);
                timerDerrota.start();
            } else {
                // Rehabilitamos de nuevo
                botonAtacar.setEnabled(true);
                botonUsarObjeto.setEnabled(true);
                botonConfirmarObjeto.setEnabled(true);
                botonCancelarObjeto.setEnabled(true);
                for (JButton b : botonesItems) {
                    if (b != null)
                        b.setEnabled(true);
                }
            }
        });
        timerEnemigo.setRepeats(false);
        timerEnemigo.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(offsetXPantalla, offsetYPantalla);

        if (imagenFondo != null) {
            g2d.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Dibujamos al jugador primero para que quede bien posicionado sobre el disco
        // rúnico izquierdo
        if (imagenJugador != null) {
            g2d.drawImage(imagenJugador.getImage(), 160, 140, 500, 500, this);
        }

        if (imagenEnemigo != null) {
            g2d.drawImage(imagenEnemigo.getImage(), 820 + offsetXEnemigo, 40 + offsetYEnemigo, 300, 270, this);

            // Dibujar barra de vida del enemigo
            if (enemigo != null && enemigo.getVidaMaxima() > 0) {
                int barraWidth = 200;
                int barraHeight = 20;
                int xBarra = 820 + offsetXEnemigo + (300 - barraWidth) / 2;
                int yBarra = 40 + offsetYEnemigo + 270 + 10;

                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(xBarra, yBarra, barraWidth, barraHeight);

                int vidaActual = Math.max(0, enemigo.getVidaActual());
                int widthVida = (int) ((double) vidaActual / enemigo.getVidaMaxima() * barraWidth);
                g2d.setColor(Color.RED);
                g2d.fillRect(xBarra, yBarra, widthVida, barraHeight);

                g2d.setColor(Color.WHITE);
                g2d.drawRect(xBarra, yBarra, barraWidth, barraHeight);

                g2d.setFont(new Font("Monospaced", Font.BOLD, 14));
                String textoVida = vidaActual + " / " + enemigo.getVidaMaxima();
                FontMetrics fm = g2d.getFontMetrics();
                int textX = xBarra + (barraWidth - fm.stringWidth(textoVida)) / 2;
                int textY = yBarra + ((barraHeight - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(textoVida, textX, textY);
            }
        }
        g2d.dispose();
    }
}