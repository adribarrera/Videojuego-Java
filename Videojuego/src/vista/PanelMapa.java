package vista;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
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
    private Colisiones colisiones; // Instanciamos la nueva clase
    private boolean modoDebug = true;

    public PanelMapa() {
        colisiones = new Colisiones(); // Inicializamos el gestor de colisiones
        setLayout(null);

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

        guerrero = new Guerrero("Pablete", 100, 20, 10, 0.5, 5);
        guerrero.setPosX(610);
        guerrero.setPosY(550);

        URL urlPersonaje = getClass().getResource("/assets/imagenes/Pablete.png");
        if (urlPersonaje != null) {
            iconPersonaje = new ImageIcon(urlPersonaje);
        } else {
            System.err.println("Imagen del personaje no encontrada");
        }

		controlador = new ControladorMovimiento(guerrero, this, colisiones, 70, 50);
        URL urlMusica = getClass().getResource("/assets/audio/mapaInst.wav"); 
        icon = new ImageIcon(getClass().getResource("/assets/imagenes/mapa.jpg"));
        try {
            AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica); 
            musicaFondo = AudioSystem.getClip(); 
            musicaFondo.open(audioInst); 
            musicaFondo.loop(Clip.LOOP_CONTINUOUSLY); 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        if (icon != null) {
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), null);
        }
        
        if (iconPersonaje != null && guerrero != null) {
            g.drawImage(iconPersonaje.getImage(), guerrero.getPosX(), guerrero.getPosY(), 70, 70, null);
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