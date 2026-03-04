package vista;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.net.URL;
import javax.sound.sampled.*;
import modelo.Guerrero;
import controlador.ControladorMovimiento;
import java.awt.Rectangle; // Importante para las colisiones
import java.util.ArrayList; // Para guardar la lista de muros

public class PanelMapa extends JPanel {
	private Clip musicaFondo;
	private ImageIcon icon;
	private ImageIcon iconPersonaje;
	private Guerrero guerrero;
	private ControladorMovimiento controlador;
	private ArrayList<Rectangle> muros;
	private boolean modoDebug = true;

	public PanelMapa() {
		muros = new ArrayList<>();
		crearMuros();
		setLayout(null); // para poner botones encima

		// --- HERRAMIENTA DE MAPEO (SOLO EN DEBUG) ---
		if (modoDebug) {
			this.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("CLIC EN: X=" + e.getX() + " | Y=" + e.getY());
				}
			});
		}
		// --------------------------

		// 1. Inicializamos al Guerrero (Nombre, vida, ataque, defensa, critico,
		// velocidad)
		// Lo centramos en la pantalla de 1280x720 (aprox en 640x360)
		guerrero = new Guerrero("Pablete", 100, 20, 10, 0.5, 5);
		guerrero.setPosX(610);
		guerrero.setPosY(600);

		// 2. Cargamos la imagen de "Pablete.png" para representar a nuestro guerrero
		URL urlPersonaje = getClass().getResource("/assets/imagenes/Pablete.png");
		if (urlPersonaje != null) {
			iconPersonaje = new ImageIcon(urlPersonaje);
		} else {
			System.err.println("Imagen del personaje no encontrada");
		}

		// 3. Activamos el Controlador de Movimiento
		// Le pasamos: el guerrero, este panel, y el tamaño de la imagen (ej: 50x50
		// píxeles)
		controlador = new ControladorMovimiento(guerrero, this, 70, 50);

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

	private void crearMuros() {
		// Ejemplo: Un muro en la parte superior
		muros.add(new Rectangle(0, 0, 250, 340));
		muros.add(new Rectangle(0, 0, 40, 700));
		muros.add(new Rectangle(1230, 0, 40, 700));

		muros.add(new Rectangle(0, 450, 250, 200));
		muros.add(new Rectangle(0, 650, 1280, 30));

		muros.add(new Rectangle(709, 120, 10, 130));
		muros.add(new Rectangle(709, 110, 50, 10));
		muros.add(new Rectangle(759, 80, 10, 30));
		muros.add(new Rectangle(759, 70, 390, 10));
		muros.add(new Rectangle(1149, 70, 10, 30));

	}

	public boolean verificarMovimiento(int x, int y, int ancho, int alto) {
		// Creamos un rectángulo imaginario donde quiere ir el personaje
		Rectangle futuroPersonaje = new Rectangle(x, y, ancho, alto);

		for (Rectangle muro : muros) {
			if (futuroPersonaje.intersects(muro)) {
				return false; // ¡CHOCA! No permitir movimiento
			}
		}
		return true; // No choca con nada
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
			for (Rectangle muro : muros) {
				g.drawRect(muro.x, muro.y, muro.width, muro.height);
			}
		}
	}

}