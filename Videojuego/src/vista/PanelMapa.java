package vista;

import java.awt.color.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.net.URL;
import javax.sound.sampled.*;
import java.awt.Rectangle;

import modelo.Guerrero;
import controlador.Colisiones; // Importamos nuestra nueva clase
import controlador.ControladorMovimiento;

public class PanelMapa extends JPanel {
	private Clip musicaFondo;
	private ImageIcon icon;
	private ImageIcon iconPersonaje;
	private Guerrero guerrero;
	private ControladorMovimiento controlador;
	private boolean modoDebug = false;
	private Colisiones colisiones;

	public PanelMapa() {

		setLayout(null); // para poner botones encima

		// 1. Inicializamos al Guerrero (Nombre, vida, ataque, defensa, critico,
		// velocidad)
		// Lo centramos en la pantalla de 1280x720 (aprox en 640x360)
		guerrero = new Guerrero("Arthur", 100, 20, 10, 0.5, 15);
		guerrero.setPosX(640);
		guerrero.setPosY(360);

		URL urlPersonaje = getClass().getResource("/assets/imagenes/Pablete.png");
		if (urlPersonaje != null) {
			iconPersonaje = new ImageIcon(urlPersonaje);
		} else {
			System.err.println("Imagen del personaje no encontrada");
		}

		// 3. Activamos el Controlador de Movimiento
		// Le pasamos: el guerrero, este panel, y el tamaño de la imagen (ej: 50x50
		// píxeles)
		controlador = new ControladorMovimiento(guerrero, this, colisiones, 70, 50);

		// Cargo y reproduzco la instrumental
		URL urlMusica = getClass().getResource("/assets/audio/mapaInst.wav"); // Obtengo su ruta
		icon = new ImageIcon(getClass().getResource("/assets/imagenes/mapa.jpg"));
		try {
			AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica); // Marco la ruta
			musicaFondo = AudioSystem.getClip(); // Obtengo la instrumental
			musicaFondo.open(audioInst); // Abre la instrumental
			musicaFondo.loop(Clip.LOOP_CONTINUOUSLY); // Para que suene en bucle
		} catch (Exception e) {
			e.printStackTrace(); // Capturar excepciones que puedan salir
		}
	}

	// Metodo para dibujar la imagen del personaje
	@Override

	public void paint(Graphics g) {
		super.paint(g); // Importante llamar al super.paint(g) al inicio
		if (icon != null) {
			g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), null);
		}
		// DIBUJAR AL GUERRERO
		if (iconPersonaje != null && guerrero != null) {
			// Dibujamos la imagen elegida en las coordenadas X e Y que tenga el guerrero en
			// ese momento
			g.drawImage(iconPersonaje.getImage(), guerrero.getPosX(), guerrero.getPosY(), 50, 50, null);
		}

		setOpaque(false);
		super.paintChildren(g);

		if (modoDebug) {
			g.setColor(Color.RED);
			// Obtenemos los muros usando el getter que creamos
			for (Rectangle muro : colisiones.getMuros()) {
				g.drawRect(muro.x, muro.y, muro.width, muro.height);
			}
		}
	}
}