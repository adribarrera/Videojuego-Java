package vista;


import java.awt.*;
import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;

public class PanelCombate extends JPanel {
    private Clip musicaCombate;
    private ImageIcon imagenFondo;
    private JTextArea areaTexto;

    private JButton botonAtacar;
    private JButton botonDefender;
    private JButton botonUsarObjeto;

    public PanelCombate() {

        this.setLayout(new BorderLayout());
        
        cargarRecursos();

        JPanel panelInferior = crearPanelInferior(); // Llamo al metodo para crear el panel inferior
        
        this.add(panelInferior, BorderLayout.SOUTH); // Añado el panelInferior a PanelCombate y lo pongo abajo.
        
    }

    // Metodo para cargar los recursos en el constructor
    private void cargarRecursos() { 
        // Fondo
        URL urlFondo = getClass().getResource("/assets/imagenes/fondoCombate.jpg");
        
        if (urlFondo != null) {
            imagenFondo = new ImageIcon(urlFondo);
        } else {
            System.err.println("ERROR: No se encuentra fondoCombate.jpg");;
        }

        // Música
        try {
        URL urlMusica = getClass().getResource("/assets/audio/minibossInst.wav");
        
        if (urlMusica != null) {
            AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica);
            musicaCombate = AudioSystem.getClip();
            musicaCombate.open(audioInst);
            musicaCombate.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("ERROR: No se encuentra minibossInst.wav");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creación de otro JPanel (para cuadro de texto)
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1280, 200));   //Tamaño
        panel.setBackground(Color.decode("#123038"));   //Color personalizado
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.decode("#00CBD1"), 3)); // Borde para la caja de texto
        
        configurarAreaTexto(); // Se crea y configura el area de texto a insertar en este panel
        panel.add(areaTexto);   // Se añade a este panel
        
        configurarBotones(panel);
        
        return panel;
    }

    // Creación del área de texto.
    private void configurarAreaTexto() {
        areaTexto = new JTextArea("Comienza el combate");                   // Texto introductorio
        areaTexto.setFont(new Font("Monospaced", Font.BOLD, 22));     // Fuente
        areaTexto.setBackground(Color.decode("#123038"));                                    // Color del fondo del area de texto
        areaTexto.setForeground(Color.WHITE);                                   // Color de la fuente del area de texto
        areaTexto.setEditable(false);                                        // No editable
        areaTexto.setLineWrap(true);                                     // Para que el texto corte y no se salga del cuadro
        areaTexto.setBounds(30, 30, 600, 140);             // Posicion y dimensiones                                       // Se añade al JPanel inferior
    }

    private JButton crearBoton(String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);

        if (url == null) {
            System.err.println("ERROR: No se encuentra la imagen: " + ruta);
            JButton botonError = new JButton("ERROR");
            botonError.setBackground(Color.RED);
            botonError.setBounds(0,0, ancho, alto);
            return botonError; // Creo este "boton de error" por si falla que no rompa
        }

        ImageIcon fotoBoton = new ImageIcon(url);    //Cargo imagen

        JButton boton = new JButton(fotoBoton);

        boton.setContentAreaFilled(false); 
        boton.setBorderPainted(false);     
        boton.setFocusPainted(false);      
        boton.setOpaque(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    private void configurarBotones(JPanel panel) {
        int ancho = 200;
        int alto = 80;
        int separacion = 10;

        int xInicio = 800;
        int xCentro = xInicio + (ancho/2) + (separacion/2); //Formula para determinar el centro debajo de dos botones
        int yFila1 = 15;
        int yFila2 = yFila1 + alto + separacion;

        botonAtacar = crearBoton("/assets/imagenes/botonAtacar.png", ancho, alto);
        botonDefender = crearBoton("/assets/imagenes/botonDefender.png", ancho, alto);
        botonUsarObjeto = crearBoton("/assets/imagenes/botonObjeto.png", ancho, alto);

        botonAtacar.setBounds(xInicio, yFila1, ancho, alto);    
        botonDefender.setBounds(xInicio + ancho + separacion, yFila1, ancho, alto);
        botonUsarObjeto.setBounds(xCentro, yFila2, ancho, alto);

        botonAtacar.addActionListener(e -> areaTexto.setText("Has atacado"));
        panel.add(botonAtacar);

        botonDefender.addActionListener(e -> areaTexto.setText("Te has defendido."));
        panel.add(botonDefender);

        botonUsarObjeto.addActionListener(e -> areaTexto.setText("Buscando en el inventario..."));
        panel.add(botonUsarObjeto);
        
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
