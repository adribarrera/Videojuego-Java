package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.HashMap;
import java.net.URL;
import javax.sound.sampled.*;
import java.awt.Rectangle;

import modelo.Personaje;
import controlador.Boton;
import controlador.Colisiones; // Importamos nuestra nueva clase
import controlador.ControladorMovimiento;

public class PanelMapa extends JPanel {
	private Clip musicaFondo;
	private ImageIcon imagenMapa;

	// Variables para la Animación
	private HashMap<String, ImageIcon> spritesPersonaje;
	private String direccionActual = "S";
	private int frameActual = 1;

	private Personaje personaje;
	private ControladorMovimiento controlador;
	private boolean modoDebug = true;
	private Colisiones colisiones;
	private boolean juegoPausado = false;

	public PanelMapa() {
		colisiones = new Colisiones();
		setLayout(null); // para poner botones encima

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !juegoPausado) {
					mostrarDialogoPausa();
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
		// Le pasamos: el personaje, este panel, y el tamaño de la imagen (ej: 50x50
		// píxeles)
		controlador = new ControladorMovimiento(personaje, this, colisiones, 70, 70);

		cargarRecursos();
	}

	private void cargarSprites() {
		spritesPersonaje = new HashMap<>();
		String[] direcciones = { "W", "A", "S", "D" };

		for (String dir : direcciones) {
			for (int i = 1; i <= 4; i++) {
				
				String nombreArchivo = "/assets/imagenes/Sprites/Caminar/Pablo/" + dir + "/Sprite" + i + ".png";

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

		JDialog dialogoPausa = new JDialog(ventana, "Pausa", true);
		dialogoPausa.setSize(400, 250);
		dialogoPausa.setLocationRelativeTo(ventana); // Centrado
		dialogoPausa.setUndecorated(true); // Quitamos barra superior
		dialogoPausa.setBackground(new Color(0, 0, 0, 0));// Fondo transparente
		dialogoPausa.setLayout(null); // Layout Libre

		JPanel panelFondoPausa = new JPanel() {
			ImageIcon iconFondo = new ImageIcon(getClass().getResource("/assets/imagenes/panelEscape.png"));

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (iconFondo != null) {
					// Dibuja la imagen ajustada al tamaño del panel
					g.drawImage(iconFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
				}
			}
		};
		panelFondoPausa.setOpaque(false); // Para que sea transparente
		panelFondoPausa.setLayout(null); // Layout Libre

		// --- BOTON SEGUIR ---
		JButton btnSeguir = Boton.crearBotonImagen("/assets/imagenes/botonContinuarEscape.png", 200, 60);
		btnSeguir.setBounds(100, 60, 200, 60); // X, Y, Ancho, Alto

		btnSeguir.addActionListener(e -> {
			dialogoPausa.dispose();
			juegoPausado = false;
			this.requestFocus();
		});

		// --- BOTON SALIR DEL JUEGO ---
		JButton btnSalirPausa = Boton.crearBotonImagen("/assets/imagenes/botonSalirEscape.png", 200, 60);
		btnSalirPausa.setBounds(100, 130, 200, 60); // X, Y, Ancho, Alto

		btnSalirPausa.addActionListener(e -> System.exit(0));

		// Añadimos al panel y mostramos
		panelFondoPausa.add(btnSeguir);
		panelFondoPausa.add(btnSalirPausa);

		dialogoPausa.setContentPane(panelFondoPausa);
		dialogoPausa.setVisible(true);
	}

	// Metodo para dibujar la imagen del personaje
	@Override

	public void paint(Graphics g) {
		super.paint(g); // Importante llamar al super.paint(g) al inicio
		if (imagenMapa != null) {
			g.drawImage(imagenMapa.getImage(), 0, 0, getWidth(), getHeight(), null);
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
}