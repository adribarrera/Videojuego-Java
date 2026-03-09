package com.videojuego.vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.HashMap;
import java.util.ArrayList;
import java.net.URL;
import javax.sound.sampled.*;
import java.awt.Rectangle;

import com.videojuego.modelo.Personaje;
import com.videojuego.modelo.BossEnMapa;
import com.videojuego.controlador.Colisiones; // Importamos nuestra nueva clase
import com.videojuego.controlador.ControladorMovimiento;

public class PanelMapa extends JPanel {
	private Clip musicaFondo;
	private ImageIcon imagenMapa;

	// Variables para la Animación
	private HashMap<String, ImageIcon> spritesPersonaje;
	private String direccionActual = "S";
	private int frameActual = 1;

	private Personaje personaje;
	private boolean modoDebug = true;
	private Colisiones colisiones;
	private boolean juegoPausado = false;

	// Añadir a las variables de instancia de PanelMapa
	private ArrayList<BossEnMapa> bossesEnMapa;
	private BossEnMapa bossCercano = null; // Nos dirá si estamos pegados a un boss

	// Añadir para la Tienda Delikia
	private ImageIcon imagenDelikia;
	private Rectangle areaFisicaDelikia; // Donde se pinta y colisiona si hiciera falta
	private Rectangle areaInteraccionDelikia; // Area para interactuar
	private boolean cercaDeDelikia = false;

	// Panel HUD de estadísticas
	private PanelEstadisticasHUD hudEstadisticas;

	public PanelMapa() {
		colisiones = new Colisiones();
		setLayout(null); // para poner botones encima

		// Inicializamos el HUD y lo colocamos arriba a la izquierda
		hudEstadisticas = new PanelEstadisticasHUD();
		hudEstadisticas.setBounds(30, 20, 560, 45); // X cambiado a 30
		this.add(hudEstadisticas);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !juegoPausado) {
					mostrarDialogoPausa();
				}
				// NUEVO EVENTO: Tecla 'E' para combatir o tienda
				if (e.getKeyCode() == KeyEvent.VK_E && !juegoPausado) {
					if (bossCercano != null) { // Solo si hay cartelito / estamos en área
						// Buscamos a la madre de todo (VentanaPrincipal) y le ordenamos combatir
						VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(PanelMapa.this);
						// Le pasamos el nombre del boss para que genere la pelea adecuada
						ventana.iniciarCombate(bossCercano.nombre);
					} else if (cercaDeDelikia) {
						// LOGICA DE TIENDA
						VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(PanelMapa.this);
						ventana.abrirTienda("Delik.IA", personaje);
					}
				}
			}
		});

		// 1. Inicializamos al Personaje (Pasamos nombre y clase)
		personaje = new Personaje("Pablo", "Guerrero");
		personaje.setPosX(600);
		personaje.setPosY(500);

		// 2. Cargar todos los sprites de animación
		cargarSprites();

		// 3. Activamos el Controlador de Movimiento
		// Le pasamos: el personaje, este panel, y el tamaño de la imagen

		new ControladorMovimiento(personaje, this, colisiones, 70, 70);

		cargarRecursos();
		inicializarBosses();
		inicializarDelikia();
	}

	private void inicializarDelikia() {
		// La ponemos en la sala central (ajustar coordenadas si hace falta)
		areaFisicaDelikia = new Rectangle(645, 280, 100, 100);
		areaInteraccionDelikia = new Rectangle(645, 280, 100, 100); // Area mas grande

		URL urlDelikia = getClass().getResource("/assets/imagenes/DelikiaMapa.png");
		if (urlDelikia != null) {
			imagenDelikia = new ImageIcon(urlDelikia);
		} else {
			System.err.println("No se encontró la imagen de Delikia.");
		}
	}

	private void inicializarBosses() {
		bossesEnMapa = new ArrayList<>();

		// 1. SORAYA (Sala de la izquierda)
		Rectangle areaSoraya = new Rectangle(85, 345, 80, 80);
		BossEnMapa soraya = new BossEnMapa("Soraya", 90, 350, "/assets/imagenes/SorayaMapa.png", areaSoraya);
		bossesEnMapa.add(soraya);

		// 2. SERGIO (Sala arriba a la derecha)
		Rectangle areaSergio = new Rectangle(910, 135, 80, 80);
		BossEnMapa sergio = new BossEnMapa("Sergio", 915, 140, "/assets/imagenes/SergioMapa.png", areaSergio);
		bossesEnMapa.add(sergio);

		// 3. JESSICA (Sala de la derecha)
		Rectangle areaJessica = new Rectangle(1100, 345, 80, 80);
		BossEnMapa jessica = new BossEnMapa("Jessica", 1105, 350, "/assets/imagenes/JessicaMapa.png", areaJessica);
		bossesEnMapa.add(jessica);

		// 4. JUAN CARLOS (Sala arriba a la izquierda)
		Rectangle areaJuanCarlos = new Rectangle(335, 125, 80, 80);
		BossEnMapa juancarlos = new BossEnMapa("Juan Carlos", 340, 130, "/assets/imagenes/JuanCarlosMapa.png",
				areaJuanCarlos);
		bossesEnMapa.add(juancarlos);
	}

	private void cargarSprites() {
		spritesPersonaje = new HashMap<>();
		String[] direcciones = { "W", "A", "S", "D" };

		for (String dir : direcciones) {
			for (int i = 1; i <= 4; i++) {

				String nombreArchivo = "";
				if (personaje.getClaseElegida().equalsIgnoreCase("guerrero")) {
					nombreArchivo = "/assets/imagenes/Sprites/Caminar/Pablo/" + dir + "/Sprite" + i + ".png";
				} else if (personaje.getClaseElegida().equalsIgnoreCase("mago")) {
					nombreArchivo = "/assets/imagenes/Sprites/Caminar/Adri/" + dir + "/Sprite" + i + ".png";
				} else if (personaje.getClaseElegida().equalsIgnoreCase("asesino")) {
					nombreArchivo = "/assets/imagenes/Sprites/Caminar/Dani/" + dir + "/Sprite" + i + ".png";
				}

				URL url = getClass().getResource(nombreArchivo);
				if (url != null) {
					spritesPersonaje.put(dir + i, new ImageIcon(url));
				} else {
					System.err.println("No se encontró el sprite: " + nombreArchivo);
				}
			}
		}
	}

	public void actualizarAnimacion(String direccion, int frame) {
		this.direccionActual = direccion.toUpperCase();
		this.frameActual = frame;
		repaint();
	}

	public void mostrarDialogoPausa() { // Metodo que creamos botones y dialogoPausa
		juegoPausado = true;

		VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);

		// Instanciamos nuestro nuevo menú reutilizable.
		// Le pasamos la ventana, y lo que tiene que hacer cuando le demos a "Continuar"
		MenuEscape menu = new MenuEscape(ventana, () -> {
			juegoPausado = false;
			this.requestFocus();
		});

		// Lo hacemos visible (al ser modal, el código se pausa aquí hasta que el menú
		// se cierre)
		menu.setVisible(true);
	}

	// Metodo para dibujar la imagen del personaje
	@Override

	public void paint(Graphics g) {

		super.paint(g); // Importante llamar al super.paint(g) al inicio
		if (imagenMapa != null) {
			g.drawImage(imagenMapa.getImage(), 0, 0, getWidth(), getHeight(), null);
		}

		// Actualizamos el HUD en cada pintado si hay personaje
		if (personaje != null && hudEstadisticas != null) {
			hudEstadisticas.actualizarEstadisticas(personaje);
		}
		// DIBUJAR AL PERSONAJE ANIMADO
		if (spritesPersonaje != null) {
			String claveSprite = direccionActual + frameActual;
			ImageIcon spriteFoco = spritesPersonaje.get(claveSprite);

			if (spriteFoco != null) {
				g.drawImage(spriteFoco.getImage(), personaje.getPosX(), personaje.getPosY(), 70, 70, null);
			}
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
		// 1. Pintamos todos los bosses de la lista + Tienda Delikia
		if (imagenDelikia != null && areaFisicaDelikia != null) {
			g.drawImage(imagenDelikia.getImage(), areaFisicaDelikia.x, areaFisicaDelikia.y, areaFisicaDelikia.width,
					areaFisicaDelikia.height, null);
		}

		if (bossesEnMapa != null) {
			for (BossEnMapa boss : bossesEnMapa) {
				if (boss.imagen != null) {
					g.drawImage(boss.imagen.getImage(), boss.posX, boss.posY, boss.ancho, boss.alto, null);
				}
			}
		}

		// 2. Detección de cercanía del personaje jugador
		Rectangle rectPersonaje = new Rectangle(personaje.getPosX(), personaje.getPosY(), 70, 70);
		bossCercano = null; // Reseteamos
		cercaDeDelikia = false; // Reseteamos

		// Comprobar Tienda
		if (areaInteraccionDelikia != null && rectPersonaje.intersects(areaInteraccionDelikia)) {
			cercaDeDelikia = true;
			g.setColor(Color.WHITE);
			g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
			g.drawString("Pulsa E para comprar (Delik.IA)", personaje.getPosX() - 50, personaje.getPosY() - 15);
		} else {
			// Comprobar Bosses (solo si no estamos ya interactuando con la tienda)
			for (BossEnMapa boss : bossesEnMapa) {
				if (rectPersonaje.intersects(boss.areaInteraccion)) {
					bossCercano = boss; // ¡Estamos en la zona de un boss!

					// Pintamos el "cartelito"
					g.setColor(Color.WHITE);
					g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
					g.drawString("Pulsa E para combatir (vs " + boss.nombre + ")", personaje.getPosX() - 50,
							personaje.getPosY() - 15);
					break;
				}
			}
		}
		// Opcional: Como tienes el "modoDebug", puedes añadir pintar en verde el área
		// de los bosses

		if (modoDebug) {
			g.setColor(Color.GREEN);
			for (BossEnMapa b : bossesEnMapa) {
				g.drawRect(b.areaInteraccion.x, b.areaInteraccion.y, b.areaInteraccion.width, b.areaInteraccion.height);
			}
		}
	}

	private void cargarRecursos() {
		// Cargar Fondo del Mapa
		URL urlMapa = getClass().getResource("/assets/imagenes/mapa.jpg");
		if (urlMapa != null) {
			imagenMapa = new ImageIcon(urlMapa);
		} else {
			System.err.println("ERROR: No se encontró la imagen del mapa.");
		}

		// Cargar Música
		try {
			URL urlMusica = getClass().getResource("/assets/audio/mapaInst.wav");
			if (urlMusica != null) {
				AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica);
				musicaFondo = AudioSystem.getClip();
				musicaFondo.open(audioInst);
			} else {
				System.err.println("ERROR: No se encontró el audio del mapa.");
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

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonajeJugador(Personaje nuevoPersonaje) {
		this.personaje = nuevoPersonaje;

		// Lo posicionamos en el centro inicial
		this.personaje.setPosX(600);
		this.personaje.setPosY(500);

		cargarSprites();

		// ¡Súper Importante! Creamos el ControladorMovimiento AHORA, no en el
		// constructor,
		// porque antes el personaje era null.
		new ControladorMovimiento(this.personaje, this, colisiones, 70, 70);

		// Refrescamos la pantalla para dibujarlo
		repaint();
	}

}