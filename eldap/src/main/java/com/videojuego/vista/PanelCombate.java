package com.videojuego.vista;

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;
import com.videojuego.controlador.Boton;
import com.videojuego.modelo.Enemigo;
import com.videojuego.modelo.Personaje;
import com.videojuego.modelo.Item;
import java.util.List;

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

    // --- Componentes para Objetos ---
    private JButton[] botonesItems = new JButton[2];
    private int itemSeleccionadoCombate = -1;
    private ImageIcon[] iconosItemsNormales = new ImageIcon[2];
    private ImageIcon[] iconosItemsSeleccionados = new ImageIcon[2];
    private JButton botonConfirmarObjeto;
    private JButton botonCancelarObjeto;

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
        botonConfirmarObjeto = Boton.crearBotonImagen("/assets/imagenes/botonObjeto.png", 160, 60); // Reutilizamos
                                                                                                    // botonObjeto
                                                                                                    // porque pega
        botonConfirmarObjeto.setBounds(xConfirm, 40, 160, 60);
        botonConfirmarObjeto.setVisible(false);
        botonConfirmarObjeto.addActionListener(e -> usarItemSeleccionado());
        panel.add(botonConfirmarObjeto);

        botonCancelarObjeto = Boton.crearBotonImagen("/assets/imagenes/botonVolver.png", 160, 60); // Lo tenemos de
                                                                                                   // tienda
        botonCancelarObjeto.setBounds(xConfirm, 100, 160, 60);
        botonCancelarObjeto.setVisible(false);
        botonCancelarObjeto.addActionListener(e -> cancelarMenuObjeto());
        panel.add(botonCancelarObjeto);

        // --- Fin botones Submenú ---

        botonAtacar.addActionListener(e -> {

            // 1. El jugador ataca al enemigo
            this.jugador.atacar(this.enemigo);
            repaint(); // Actualizar interfaz (barra de vida del enemigo)

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

        // Acción al pulsar el botón principal Usar Objeto
        botonUsarObjeto.addActionListener(e -> activarMenuObjetos());

        panel.add(botonAtacar);
        panel.add(botonUsarObjeto);

        // Calculamos la posición del botón de Salir (Centrado en el mismo espacio)
        int xSalir = 850;

        // Creamos el botón (puedes usar la imagen que prefieras)
        botonSalir = Boton.crearBotonImagen("/assets/imagenes/botonSalirEscape.png", ancho, alto);
        botonSalir.setBounds(xSalir, yBotones, ancho, alto);

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
            areaTexto.setText("Buscas en la mochila pero no llevas ningún objeto.");
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
        areaTexto.setText("Has abierto la mochila, elige el objeto a usar.");
    }

    private void seleccionarItemInventario(int indice) {
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
        areaTexto.setText(obj.getNombre() + "\n" + obj.getDescripcion());
    }

    private void usarItemSeleccionado() {
        if (itemSeleccionadoCombate == -1) {
            areaTexto.setText("Selecciona un objeto de la lista.");
            return;
        }

        Item obj = jugador.getInventario().get(itemSeleccionadoCombate);
        jugador.usarItem(itemSeleccionadoCombate, enemigo); // Efecto aplicado internamente

        cerrarMenuObjeto(true);

        // Actualizamos UI
        repaint();
        if (hudEstadisticas != null)
            hudEstadisticas.actualizarEstadisticas(jugador);

        // Si el objeto mató al boss (ej: Virus Linux)
        if (!enemigo.estaVivo()) {
            areaTexto.setText("¡Has usado " + obj.getNombre() + "!\n¡Has derrotado a " + enemigo.getNombre() + "!");
            jugador.setDinero(jugador.getDinero() + 100);
            jugador.mejorarAtributosAlDerrotarBoss();
            if (hudEstadisticas != null)
                hudEstadisticas.actualizarEstadisticas(jugador);

            botonAtacar.setVisible(false);
            botonUsarObjeto.setVisible(false);
            botonSalir.setVisible(true);
            return;
        }

        // Si sobrevive, contraataca
        enemigo.atacar(jugador);
        if (hudEstadisticas != null)
            hudEstadisticas.actualizarEstadisticas(jugador);

        areaTexto.setText("Has usado " + obj.getNombre() + ".\n" +
                enemigo.getNombre() + " contraataca! Te queda " + jugador.getVidaActual() + " de vida.");

        // Check si morimos por el ataque enemigo (o suicidio de Guantón y consecuente
        // remate)
        if (!jugador.estaVivo()) {
            areaTexto.setText("¡Has sido derrotado! Fin del juego...");
            botonAtacar.setVisible(false);
            botonUsarObjeto.setVisible(false);
            botonSalir.setVisible(true);
        }
    }

    private void cancelarMenuObjeto() {
        cerrarMenuObjeto(true);
        areaTexto.setText("Decides guardarte la mochila. ¿Atacas o esperas?");
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        if (imagenEnemigo != null) {
            g.drawImage(imagenEnemigo.getImage(), 820, 40, 300, 270, this);

            // Dibujar barra de vida del enemigo
            if (enemigo != null && enemigo.getVidaMaxima() > 0) {
                int barraWidth = 200;
                int barraHeight = 20;
                int xBarra = 820 + (300 - barraWidth) / 2;
                int yBarra = 40 + 270 + 10;

                g.setColor(Color.DARK_GRAY);
                g.fillRect(xBarra, yBarra, barraWidth, barraHeight);

                int vidaActual = Math.max(0, enemigo.getVidaActual());
                int widthVida = (int) ((double) vidaActual / enemigo.getVidaMaxima() * barraWidth);
                g.setColor(Color.RED);
                g.fillRect(xBarra, yBarra, widthVida, barraHeight);

                g.setColor(Color.WHITE);
                g.drawRect(xBarra, yBarra, barraWidth, barraHeight);

                g.setFont(new Font("Monospaced", Font.BOLD, 14));
                String textoVida = vidaActual + " / " + enemigo.getVidaMaxima();
                FontMetrics fm = g.getFontMetrics();
                int textX = xBarra + (barraWidth - fm.stringWidth(textoVida)) / 2;
                int textY = yBarra + ((barraHeight - fm.getHeight()) / 2) + fm.getAscent();
                g.drawString(textoVida, textX, textY);
            }
        }
    }
}