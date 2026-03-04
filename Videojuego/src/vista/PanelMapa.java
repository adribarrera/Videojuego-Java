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
	private boolean modoDebug = false; //Para ver las los rectangulos de colisiones :P

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
		guerrero.setPosY(550);

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
		muros.add(new Rectangle(0, 0, 230, 300));			// Esquina superior izquierda
		muros.add(new Rectangle(20, 430, 210, 200));		// Esquina inferior izquierda
		muros.add(new Rectangle(1200, 0, 60, 330));		// Esquina superior derecha
		muros.add(new Rectangle(1035, 470,225, 160));		// Esquina inferior derecha

		muros.add(new Rectangle(1045, 220, 155, 110));		// Hueco derecho inferior a Sala Boss
		muros.add(new Rectangle(990, 220, 55, 135));		// Hueco derecho inferior a Sala Boss 2

		muros.add(new Rectangle(820, 220, 50, 140));		// Hueco izquierdo inferior a Sala Boss
		muros.add(new Rectangle(695, 220, 125, 90));		// Hueco izquierdo inferior a Sala Boss 2

		muros.add(new Rectangle(490, 0, 205, 310));		// Hueco centro
		muros.add(new Rectangle(400, 220, 45, 300));		// Hueco de la izquierda de la sala central

		muros.add(new Rectangle(0, 300, 20, 380));			// Borde izquierdo
		muros.add(new Rectangle(0, 630, 1270, 50));		// Borde inferior
		muros.add(new Rectangle(1240, 330, 20, 140));		// Borde derecho

		muros.add(new Rectangle(230, 0, 260, 105));		// Muro superior Sala Servidores

		muros.add(new Rectangle(695, 0, 55, 70));			// Sala Boss esquina izquierda
		muros.add(new Rectangle(1145, 0, 55, 70));			// Sala Boss esquina derecha
		muros.add(new Rectangle(750, 0, 395, 40));			// Sala Boss muro superior

		muros.add(new Rectangle(265, 220, 10, 95));		// Pared esquina superior Sala Izquierda
		muros.add(new Rectangle(265, 390, 10, 125));		// Pared esquina inferior Sala Izquierda

		muros.add(new Rectangle(445, 470,125, 50));		// Pared inferior izquierda Sala Central
		muros.add(new Rectangle(695, 470,175, 50));		// Pared inferior derecha Sala Central
		muros.add(new Rectangle(825, 430,45, 40));			// Pared inferior derecha Sala Central 2

		muros.add(new Rectangle(990, 430,45, 90));			// Pared inferior esquina derecha

		muros.add(new Rectangle(920, 40,60, 50));			// Portal
	}

	public boolean verificarMovimiento(int x, int y, int ancho, int alto) {
		// Creamos un rectángulo imaginario donde quiere ir el personaje
		Rectangle futuroPersonaje = new Rectangle(x, y, ancho, alto);

		for (Rectangle muro : muros) {
			if (futuroPersonaje.intersects(muro)) {
				return false; // Choca - No permitir movimiento
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
			g.drawImage(iconPersonaje.getImage(), guerrero.getPosX(), guerrero.getPosY(), 70, 70, null);
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