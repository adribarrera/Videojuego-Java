package com.videojuego.vista;

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import com.videojuego.controlador.Boton;

/**
 * Pantalla inicial del juego que contiene el título y los controles de acceso.
 * Gestiona la música de bienvenida y la navegación al selector de personajes.
 */
public class PanelPortada extends JPanel {

	private ImageIcon imagenFondo;

	public PanelPortada() {
		// Usamos null layout para posicionar libremente
		this.setLayout(null);

		cargarRecursos();

		configurarBotones();
	}

	private void cargarRecursos() {
		// Cargar fondo
		try {
			URL urlImagen = getClass().getResource("/assets/imagenes/portada.jpg");
			if (urlImagen != null) {
				imagenFondo = new ImageIcon(urlImagen);
			} else {
				System.err.println("ERROR: No se encuentró la imagen de portada.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reproducirMusica() {
		com.videojuego.controlador.ControladorAudio.getInstance()
				.reproducirMusicaAmbiental("/assets/audio/instrumentalPortada.wav");
	}

	public void detenerMusica() {
		com.videojuego.controlador.ControladorAudio.getInstance().detenerMusica();
	}

	private void configurarBotones() {
		// --- CREACIÓN DEL BOTÓN INICIO ---
		JButton btnJugar = Boton.crearBotonImagen("/assets/imagenes/botonComenzar.png", 200, 80);
		btnJugar.setBounds(240, 550, 200, 120); // X, Y, Ancho, Alto

		// --- ACCIÓN DEL BOTÓN JUGAR ---
		btnJugar.addActionListener(e -> {

			VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);

			if (ventana != null) {
				ventana.irEleccionPersonaje();
			}
		});

		this.add(btnJugar);

		// --- CREACIÓN DEL BOTÓN CONTROLES ---
		JButton btnControles = Boton.crearBotonImagen("/assets/imagenes/botonControles.png", 200, 80);
		btnControles.setBounds(540, 550, 200, 120); // X, Y, Ancho, Alto

		// --- ACCIÓN DEL BOTÓN CONTROLES ---
		btnControles.addActionListener(e -> mostrarControles());

		this.add(btnControles);

		// --- CREACIÓN DEL BOTÓN SALIDA ---
		JButton btnSalir = Boton.crearBotonImagen("/assets/imagenes/botonSalir.png", 200, 80);
		btnSalir.setBounds(840, 550, 200, 120); // X, Y, Ancho, Alto

		// --- ACCIÓN DEL BOTÓN SALIR ---
		btnSalir.addActionListener(e -> {
			detenerMusica();
			System.exit(0);
		});

		this.add(btnSalir);
	}

	/**
	 * Muestra un cuadro de diálogo con los controles del juego.
	 * Crea un JDialog modal que sobrepone la imagen con las instrucciones
	 * de los controles y proporciona un botón para cerrarlo.
	 */
	private void mostrarControles() {
		Window ventana = SwingUtilities.getWindowAncestor(this);
		JDialog dialogControles = new JDialog((Frame) ventana, "Controles", true);
		dialogControles.setLayout(new BorderLayout());
		dialogControles.setUndecorated(true); // Sin bordes molestos

		// Fondo del dialog transparente o negro
		dialogControles.getContentPane().setBackground(Color.BLACK);

		// Imagen de controles
		JLabel labelImagen = new JLabel();
		labelImagen.setLayout(null); // Layout nulo para posicionar el botón libremente sobre la imagen
		try {
			URL urlControl = getClass().getResource("/assets/imagenes/panelControles.jpg");
			if (urlControl != null) {
				labelImagen.setIcon(new ImageIcon(urlControl));
			} else {
				labelImagen.setText("Imagen no encontrada");
				labelImagen.setForeground(Color.WHITE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		labelImagen.setHorizontalAlignment(SwingConstants.CENTER);

		// Botón volver
		// Botón un poco más grande
		JButton btnVolver = Boton.crearBotonImagen("/assets/imagenes/botonVolver.png", 200, 80);
		// Aseguramos la transparencia por si acaso
		btnVolver.setOpaque(false);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setBorderPainted(false);
		btnVolver.addActionListener(ev -> dialogControles.dispose());

		// Establecer el tamaño previo del dialog para poder calcular proporciones
		dialogControles.setSize(this.getSize());
		dialogControles.setLocationRelativeTo(this);

		// Posicionamos el botón directamente en el interior de la imagen, centrado en la parte de abajo
		btnVolver.setBounds(dialogControles.getWidth() / 2 - 100, dialogControles.getHeight() - 140, 200, 80);

		labelImagen.add(btnVolver);
		dialogControles.add(labelImagen, BorderLayout.CENTER);

		dialogControles.setVisible(true);
	}

	// --- DIBUJO DEL FONDO ---
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Dibujamos la imagen de fondo si se cargó correctamente
		if (imagenFondo != null) {
			g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}
}