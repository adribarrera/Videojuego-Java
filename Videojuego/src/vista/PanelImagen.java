package vista;

import java.awt.*;

import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;

public class PanelImagen extends JPanel {

	private Clip musicaFondo;
	private ImageIcon icon;

	public PanelImagen() {
		// Usamos null layout para posicionar libremente
		this.setLayout(null);

		// --- CARGA DE RECURSOS (Música e Imagen) ---
		URL urlMusica = getClass().getResource("/assets/audio/instrumentalPortada.wav");
		URL urlImagen = getClass().getResource("/assets/imagenes/portada.jpg");

		if (urlImagen != null) {
			icon = new ImageIcon(urlImagen);
		} else {
			System.err.println("No se encontró la imagen de portada.");
		}

		try {
			if (urlMusica != null) {
				AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica);
				musicaFondo = AudioSystem.getClip();
				musicaFondo.open(audioInst);
				musicaFondo.loop(Clip.LOOP_CONTINUOUSLY); // Bucle infinito para la musica
			} else {
				System.err.println("No se encontró el audio.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// --- CREACIÓN DEL BOTÓN INCIO ---
		// Creamos la imagen original y obtener su objeto 'Image'
		URL rutaImagenEntrada = getClass().getResource("/assets/imagenes/botonComenzar.png");
		Image imagenOriginal = new ImageIcon(rutaImagenEntrada).getImage();

		// Definimos dos variables para el ancho y alto del boton
		int anchoBoton = 200;
		int altoBoton = 120;

		// El parámetro 'Image.SCALE_SMOOTH' asegura la máxima calidad visual.
		Image imagenEscalada = imagenOriginal.getScaledInstance(anchoBoton, altoBoton, Image.SCALE_SMOOTH);

		// Crear el nuevo ImageIcon con la imagen reescalada
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

		// Creacion del botón
		JButton btnJugar = new JButton(iconoEscalado);

		btnJugar.setContentAreaFilled(false); // Hacemos que no tenga fondo
		btnJugar.setBorderPainted(false); // Reemplaza BorderFactory
		btnJugar.setFocusPainted(false); // No mostrar recuadro al hacer clic
		btnJugar.setOpaque(false); // Transparencia

		// Definimos x,y,ancho y alto
		btnJugar.setBounds(350, 550, anchoBoton, altoBoton);

		// --- ACCIÓN DEL BOTÓN ---
		btnJugar.addActionListener(e -> {
			// Detenemos la música del menú principal
			if (musicaFondo != null && musicaFondo.isRunning()) {
				musicaFondo.stop();
			}

			JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
			ventana.remove(this);

			// Creamos objeto y añadimos a la ventana
			PanelMapa mapa = new PanelMapa();
			ventana.add(mapa, BorderLayout.CENTER); // Añadimos mapa y lo centramos para que cuadre con la pantalla

			// Refrescar la ventana
			ventana.revalidate();
			ventana.repaint();

			// Le damos foco al mapa para que se visualice
			mapa.requestFocus();

		});

		// Añadimos el botón al panel
		this.add(btnJugar);

		// --- CREACIÓN DEL BOTÓN SALIDA ---
		// Creamos la imagen original y obtener su objeto 'Image'
		URL rutaImagenSalida = getClass().getResource("/assets/imagenes/botonSalir.png");
		Image imagenOriginalSalida = new ImageIcon(rutaImagenSalida).getImage();

		// Definimos dos variables para el ancho y alto del boton
		int anchoBotonSalida = 200;
		int altoBotonSalida = 120;

		// El parámetro 'Image.SCALE_SMOOTH' asegura la máxima calidad visual.
		Image imagenEscaladaSalida = imagenOriginalSalida.getScaledInstance(anchoBotonSalida, altoBotonSalida,
				Image.SCALE_SMOOTH);

		// Crear el nuevo ImageIcon con la imagen reescalada
		ImageIcon iconoEscaladoSalida = new ImageIcon(imagenEscaladaSalida);

		// Creacion del botón
		JButton btnSalir = new JButton(iconoEscaladoSalida);

		btnSalir.setContentAreaFilled(false); // Hacemos que no tenga fondo
		btnSalir.setBorderPainted(false); // Reemplaza BorderFactory
		btnSalir.setFocusPainted(false); // No mostrar recuadro al hacer clic
		btnSalir.setOpaque(false); // Transparencia

		// Definimos x,y,ancho y alto
		btnSalir.setBounds(730, 550, anchoBoton, altoBoton);

		// --- ACCIÓN DEL BOTÓN ---
		btnSalir.addActionListener(e -> {
			// Detenemos la música del menú principal
			if (musicaFondo != null && musicaFondo.isRunning()) {
				musicaFondo.stop();
			}

			System.exit(0); // Para cuando pulsemos el boton "Salir" cierre el videoJuego

		});

		// Añadimos el botón al panel
		this.add(btnSalir);
	}

	// --- DIBUJO DEL FONDO ---
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Dibujamos la imagen de fondo si se cargó correctamente
		if (icon != null) {
			g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}
}