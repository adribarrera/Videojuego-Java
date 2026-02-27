package eldap;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.net.URL;
import javax.sound.sampled.*;

public class PanelImagen extends JPanel {

	private Clip musicaFondo;
	private ImageIcon icon;
	public PanelImagen() {
		setLayout(null); // para poner botones encima
		
		// Cargo y reproduzco la instrumental
		URL urlMusica = getClass().getResource("/assets/audio/instrumentalPortada.wav");	// Obtengo su ruta
		icon = new ImageIcon(getClass().getResource("/assets/imagenes/portada.jpg")); 
		try {
			AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica);	// Marco la ruta
			musicaFondo = AudioSystem.getClip(); // Obtengo la instrumental
			musicaFondo.open(audioInst);	// Abre la instrumental
			musicaFondo.loop(Clip.LOOP_CONTINUOUSLY); // Para que suene en bucle
		} catch (Exception e) {
			e.printStackTrace(); // Capturar excepciones que puedan salir 
		}
	}

	@Override public void paint(Graphics g){ 
		if (icon != null) {
			g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), null); 
		}
		setOpaque(false); 
		super.paintChildren(g); 
	} 

}