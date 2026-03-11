package com.videojuego.vista;

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;
import com.videojuego.controlador.Boton;

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
		com.videojuego.controlador.ControladorAudio.getInstance().reproducirMusicaAmbiental("/assets/audio/instrumentalPortada.wav");
	}

	public void detenerMusica() {
		com.videojuego.controlador.ControladorAudio.getInstance().detenerMusica();
	}

	private void configurarBotones() {
		// --- CREACIÓN DEL BOTÓN INICIO ---
		JButton btnJugar = Boton.crearBotonImagen("/assets/imagenes/botonComenzar.png", 200, 80);
		btnJugar.setBounds(350, 550, 200, 120); // X, Y, Ancho, Alto

		// --- ACCIÓN DEL BOTÓN JUGAR ---
		btnJugar.addActionListener(e -> {

			VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);

			if (ventana != null) {
				ventana.irEleccionPersonaje();
			}
		});

		this.add(btnJugar);

		// --- CREACIÓN DEL BOTÓN SALIDA ---
		JButton btnSalir = Boton.crearBotonImagen("/assets/imagenes/botonSalir.png", 200, 80);
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