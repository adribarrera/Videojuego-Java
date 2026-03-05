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

import java.net.URL;
import javax.sound.sampled.*;
import modelo.Guerrero;
import controlador.ControladorMovimiento;

public class PanelMapa extends JPanel {
	private Clip musicaFondo;
	private ImageIcon icon;
	private ImageIcon iconPersonaje;
	private Guerrero guerrero;
	private boolean juegoPausado = false;
	private ControladorMovimiento controlador;

	public PanelMapa() {

		setLayout(null); // para poner botones encima
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !juegoPausado) {
					mostrarDialogoPausa();
				}
			}
		});
		// 1. Inicializamos al Guerrero (Nombre, vida, ataque, defensa, critico,
		// velocidad)
		// Lo centramos en la pantalla de 1280x720 (aprox en 640x360)
		guerrero = new Guerrero("Pablo", 100, 20, 10, 0.5, 15);
		guerrero.setPosX(640);
		guerrero.setPosY(360);

		// 2. Cargamos la imagen de "Pablete.png" para representar a nuestro guerrero
		URL urlPersonaje = getClass().getResource("/assets/imagenes/Pablete.png");
		if (urlPersonaje != null) {
			iconPersonaje = new ImageIcon(urlPersonaje);
		} else {
			System.err.println("Imagen del personaje no encontrada");
		}

		// 3. Activamos el Controlador de Movimiento
		// Le pasamos: el guerrero, este panel, y el tamaño de la imagen
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

	public void mostrarDialogoPausa() { // Metodo que creamos botones y dialogoPausa
		juegoPausado = true;

		VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);

		JDialog dialogoPausa = new JDialog(ventana, "Pausa", true);
		dialogoPausa.setSize(400, 250);
		dialogoPausa.setLocationRelativeTo(ventana); // Centrado
		dialogoPausa.setUndecorated(true); // Quitamos barra superior
		dialogoPausa.getContentPane().setBackground(new Color(40, 40, 40)); // Fondo oscuro
		dialogoPausa.setLayout(null); // Layout Libre

		// --- BOTON SEGUIR ---
		JButton btnSeguir = new JButton("SEGUIR");
		btnSeguir.setBounds(100, 50, 200, 60);
		btnSeguir.addActionListener(e -> {
			dialogoPausa.dispose();
			juegoPausado = false;
			this.requestFocus();
		});

		// --- BOTON SALIR DEL JUEGO ---
		JButton btnSalirPausa = new JButton("SALIR DEL JUEGO");
		btnSalirPausa.setBounds(100, 150, 200, 60);
		btnSalirPausa.addActionListener(e -> System.exit(0));

		dialogoPausa.add(btnSeguir);
		dialogoPausa.add(btnSalirPausa);

		dialogoPausa.setVisible(true);
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
	}

}