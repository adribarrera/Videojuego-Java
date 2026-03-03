package vista;

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;

public class PanelCombate extends JPanel {
    private Clip musica;
    private ImageIcon icon;
    private JTextArea areaTexto;
    

    public PanelCombate() {
        URL urlMusicaMiniBoss = getClass().getResource("/assets/audio/miniBoss.wav");
        URL urlMusicaBossFinal = getClass().getResource("/assets/audio/miniBoss.wav");
        URL urlImagen = getClass().getResource("/assets/imagenes/fondoCombate.jpg");

        // Creación de botones y obtención de sus rutas - imágenes
        JButton botonAtacar;
        JButton botonDefender;
        JButton botonUsarObjeto;

        URL rutaImagenAtacar = getClass().getResource("/assets/imagenes/botonAtacar.png");
        Image imagenOriginalAtacar = new ImageIcon(rutaImagenAtacar).getImage();

        URL rutaImagenDefender = getClass().getResource("/assets/imagenes/botonDefender.png");
        Image imagenOriginalDefender = new ImageIcon(rutaImagenDefender).getImage();

        URL rutaImagenUsarObjeto = getClass().getResource("/assets/imagenes/botonObjeto.png");
        Image imagenOriginalUsarObjeto = new ImageIcon(rutaImagenUsarObjeto).getImage();

        if (urlImagen != null) {
            icon = new ImageIcon(urlImagen);
        } else {
            System.err.println("No se encontró la imagen de portada.");
        }

        //Creación de otro JPanel (para cuadro de texto)
        JPanel panelInferior = new JPanel();
        panelInferior.setPreferredSize(new Dimension(1280, 200));
        panelInferior.setBackground(Color.BLACK);
        panelInferior.setLayout(null);
        panelInferior.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Borde para la caja de texto
        
        // Creación del área de texto.
        areaTexto = new JTextArea("Comienza el combate");
        areaTexto.setFont(new Font("Monospaced", Font.BOLD, 22));
        areaTexto.setBackground(Color.BLACK);
        areaTexto.setForeground(Color.WHITE);
        areaTexto.setEditable(false);   // No editable
        areaTexto.setLineWrap(true);       
        panelInferior.add(areaTexto);   // Se añade aeste JPanel
    }
}
