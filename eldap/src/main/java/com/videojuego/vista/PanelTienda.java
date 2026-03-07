package com.videojuego.vista;

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import javax.sound.sampled.*;
import com.videojuego.controlador.Boton;

public class PanelTienda extends JPanel {
    private Clip musicaFondo;
    private ImageIcon imagenFondo;

    private JPanel panelMenu;
    private JTextArea areaDescripcion;
    private JButton botonComprar;
    private JButton botonVolver;
    private JButton botonesItems[] = new JButton[6];
    private int itemSeleccionado = -1; // Como el array de items es de 0 a 5, lo inicializo a -1
    private ImageIcon[] iconosNormales = new ImageIcon[6];
    private ImageIcon[] iconosSeleccionados = new ImageIcon[6];

    public PanelTienda() {
        this.setLayout(null);
        cargarRecursos();
        configurarMenuDerecho();
    }

    public void cargarRecursos() {
            URL urlImagen = getClass().getResource("/assets/imagenes/fondoTienda.jpg");
            if (urlImagen != null) {
                imagenFondo = new ImageIcon(urlImagen);
            } else {
                System.err.println("ERROR: No se encontró la imagen de la tienda");
            }

        try {
            URL urlMusica = getClass().getResource("/assets/audio/mapaInst.wav");
            if (urlMusica != null) {
                AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica);
                musicaFondo = AudioSystem.getClip();
                musicaFondo.open(audioInst);
        } else {
            System.err.println("ERROR: No se encontró el audio de la tienda.");
        }
        } catch(Exception e) {
        e.printStackTrace();
        }
        
    }

    public void configurarMenuDerecho() {
        // Panel Contenedor (Mitad derecha)
        panelMenu = new JPanel() {

        @Override       //Se fuerza la transparencia del panel (había bugs visuales y con esto se arreglan las transparencias)
        protected void paintComponent(Graphics g) {
            g.setColor(new Color(18, 48, 56, 200));
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
            }
        };

        panelMenu.setBounds(635, 0, 645, 720);  //Posición y dimensiones
        panelMenu.setBackground(new Color(18, 48, 56, 200));    //Color de fondo
        panelMenu.setLayout(null); 
        panelMenu.setOpaque(false); // Desactivo la solidez del repintado para que el fondo transparente funcione bien

        // Cuadrícula Items
        JPanel panelGridItems = new JPanel();
        panelGridItems.setBounds(50, 50, 540, 250);
        panelGridItems.setLayout(new GridLayout(2, 3, 10 ,10)); // 2 Filas, 3 columnas, 10 px de gap
        panelGridItems.setOpaque(false);

        String nombresObjetos [] = {
            "vaper.png",        // Item 0
            "mantequilla.png",  // Item 1
            "gabardina.png",    // Item 2
            "chato.png",       // Item 3
            "examen.png",       // Item 4
            "virus.png"       // Item 5
        };
        

        for (int i = 0; i < nombresObjetos.length; i++) {
            final int indiceItem = i;

            String rutaIconoNormal = "/assets/imagenes/Objetos/Normal/" + nombresObjetos[i];    //Consigo las rutas para los iconos concatenando
            String rutaIconoSeleccionado = "/assets/imagenes/Objetos/Seleccionado/" + nombresObjetos[i];

            iconosNormales[i] = cargarIconoEscalado(rutaIconoNormal, 120, 120);          //Obtengo iconos escalados
            iconosSeleccionados[i] = cargarIconoEscalado(rutaIconoSeleccionado, 120, 120);

            botonesItems[i] = new JButton(iconosNormales[i]);       //Creo botones sus botones y les asigno sus iconos normales por defecto
            botonesItems[i].setContentAreaFilled(false);
            botonesItems[i].setBorderPainted(false);
            botonesItems[i].setFocusPainted(false);
            botonesItems[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            botonesItems[i].addActionListener(e -> seleccionarItem(indiceItem));

            panelGridItems.add(botonesItems[i]);
        }
        
        panelMenu.add(panelGridItems);  // Añado el grid de items al panel.
        
        //Area Texto
        JPanel panelContenedorTexto = new JPanel();
        panelContenedorTexto.setBounds(50, 350, 540, 150);
        panelContenedorTexto.setLayout(new BorderLayout()); // Para que el texto ocupe todo el interior
        panelContenedorTexto.setBorder(BorderFactory.createLineBorder(Color.decode("#00CBD1"), 2)); // Borde panel texto

        areaDescripcion = new JTextArea("Bienvenido a Delik.IA. Seleccione un producto.");
        areaDescripcion.setFont(new Font("Monospaced", Font.BOLD, 18));
        areaDescripcion.setBackground(Color.decode("#123038"));
        areaDescripcion.setForeground(Color.WHITE);
        areaDescripcion.setEditable(false);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Le damos un "padding" de 10px para que las letras no choquen con el borde

        panelContenedorTexto.add(areaDescripcion, BorderLayout.CENTER);
        panelMenu.add(panelContenedorTexto);
        
        //Boton Comprar
        botonComprar = Boton.crearBotonImagen("/assets/imagenes/botonComprar.png", 200, 80);
        botonComprar.setBounds(50, 550, 200, 80);
        botonComprar.addActionListener(e -> accionComprar());
        panelMenu.add(botonComprar);
        
        //Boton Volver al mapa
        botonVolver = Boton.crearBotonImagen("/assets/imagenes/botonVolver.png", 200, 80);
        botonVolver.setBounds(390, 550, 200, 80);
        botonVolver.addActionListener(e -> accionVolver());
        panelMenu.add(botonVolver);
        
        this.add(panelMenu);    // Añado el menú derecho completo al panel general
    }

    private ImageIcon cargarIconoEscalado(String ruta, int ancho, int alto) {       //Metodo para escalar los iconos
        try {
            URL url = getClass().getResource(ruta); //Obtengo su ruta
            if (url != null) {
                Image imagen = new ImageIcon(url).getImage();   //Obtengo su imagen
                Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);       //Lo escalo
                return new ImageIcon(imagenEscalada);
            } else {
                System.err.println("No se encontró la imagen: " + ruta);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar y escalar imagen: " + ruta);
            return null;
        }
    }
        
    // --- Acciones de los Botones ---
    private void seleccionarItem(int indice) {
        for (int i = 0; i < 6; i++) {
            if (botonesItems[i] != null) {
                botonesItems[i].setIcon(iconosNormales[i]);
            }
        }

        botonesItems[indice].setIcon(iconosSeleccionados[indice]);  // Cambio su imagen
       
        itemSeleccionado=indice;
        //Texto temporal hasta que tengamos la clase Tienda
        areaDescripcion.setText("Has seleccionado el Item " + (indice + 1) + ". Descripción del objeto. Precio: X");
    }
    
    private void accionComprar() {
        if (itemSeleccionado == -1) {
            areaDescripcion.setText("¡Primero debes seleccionar un objeto!");
            return;     //Corto aqui para que no pueda comprar un objeto "fantasma"
        }
        //Texto temporal hasta que tengamos la clase Tienda
        areaDescripcion.setText("¡Has comprado el Item " + (itemSeleccionado + 1) + "!");
    }

    private void accionVolver() {
        if (itemSeleccionado != -1) {
            botonesItems[itemSeleccionado].setIcon(iconosNormales[itemSeleccionado]);       //Reinicio los iconos a normales
        }

        itemSeleccionado = -1;      //Reinicio el seleccionador de objetos
        areaDescripcion.setText("Bienvenido a Delik.IA. Seleccione un producto.");  //Reinicio el texto de entrada a la máquina

        VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
        if (ventana != null) {
            ventana.volverAMapaDesdeTienda();   //Cambio de panel para volver al mapa.
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (imagenFondo != null) {
            g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}