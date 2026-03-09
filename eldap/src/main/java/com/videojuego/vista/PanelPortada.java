package com.videojuego.vista;

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;
import com.videojuego.controlador.Boton;

public class PanelPortada extends JPanel {

	private Clip musicaFondo;
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

			// Cargar Música
			URL urlMusica = getClass().getResource("/assets/audio/instrumentalPortada.wav");
			if (urlMusica != null) {
				AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica);
				musicaFondo = AudioSystem.getClip();
				musicaFondo.open(audioInst);
			} else {
				System.err.println("ERROR: No se encontró el audio de la portada.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reproducirMusica() {
		if (musicaFondo != null) {
			musicaFondo.setFramePosition(0);
			musicaFondo.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public void detenerMusica() {
		if (musicaFondo != null && musicaFondo.isRunning()) {
			musicaFondo.stop();
		}
	}

	private void configurarBotones() {
		// --- CREACIÓN DEL BOTÓN INICIO ---
		JButton btnJugar = Boton.crearBotonImagen("/assets/imagenes/botonComenzar.png", 200, 120);
		btnJugar.setBounds(350, 550, 200, 120); // X, Y, Ancho, Alto

		// --- ACCIÓN DEL BOTÓN JUGAR ---
		btnJugar.addActionListener(e -> {
			// Detenemos la música del menú principal
			detenerMusica();

			VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);

			if (ventana != null) {
				ventana.irEleccionPersonaje();
				ventana.revalidate();
				ventana.repaint();
			}
		});

		this.add(btnJugar);

		// --- CREACIÓN DEL BOTÓN SALIDA ---
		JButton btnSalir = Boton.crearBotonImagen("/assets/imagenes/botonSalir.png", 200, 120);
		btnSalir.setBounds(730, 550, 200, 120); // X, Y, Ancho, Alto

		// --- ACCIÓN DEL BOTÓN SALIR ---
		btnSalir.addActionListener(e -> {
			detenerMusica();
			System.exit(0);
		});

		this.add(btnSalir);
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