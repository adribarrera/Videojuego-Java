package vista;

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;
import controlador.Boton;

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

		// --- CREACIÓN DEL BOTÓN INICIO (Con tu clase Boton) ---
		JButton btnJugar = Boton.crearBotonImagen("/assets/imagenes/botonComenzar.png", 200, 120);
		btnJugar.setBounds(350, 550, 200, 120); // X, Y, Ancho, Alto

		// --- ACCIÓN DEL BOTÓN JUGAR ---
		btnJugar.addActionListener(e -> {
			// Detenemos la música del menú principal
			if (musicaFondo != null && musicaFondo.isRunning()) {
				musicaFondo.stop();
			}

			VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);

			// Cambiamos al panel del mapa
			ventana.cambiarPanel();

			// Refrescar la ventana
			ventana.revalidate();
			ventana.repaint();
		});

		// Añadimos el botón al panel
		this.add(btnJugar);

		// --- CREACIÓN DEL BOTÓN SALIDA (Con tu clase Boton) ---
		JButton btnSalir = Boton.crearBotonImagen("/assets/imagenes/botonSalir.png", 200, 120);
		btnSalir.setBounds(730, 550, 200, 120); // X, Y, Ancho, Alto

		// --- ACCIÓN DEL BOTÓN SALIR ---
		btnSalir.addActionListener(e -> {
			// Detenemos la música del menú principal
			if (musicaFondo != null && musicaFondo.isRunning()) {
				musicaFondo.stop();
			}

			System.exit(0); // Cierra el videoJuego
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