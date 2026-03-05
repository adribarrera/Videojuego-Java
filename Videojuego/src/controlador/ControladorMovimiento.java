package controlador;

import modelo.Personaje;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

public class ControladorMovimiento {

    private Personaje personaje;
    private JPanel panel;
    private int anchoPersonaje;
    private int altoPersonaje;

    // El constructor recibe el Modelo (Personaje) y la Vista (Panel)
    public ControladorMovimiento(Personaje personaje, JPanel panel, int anchoPersonaje, int altoPersonaje) {
        this.personaje = personaje;
        this.panel = panel;
        this.anchoPersonaje = anchoPersonaje;
        this.altoPersonaje = altoPersonaje;

        configurarControles();
    }

    private void configurarControles() {
        // En Swing, para configurar teclas se usan InputMap y ActionMap
        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        // 1. Asignamos Teclas a "Nombres de Acción"
        inputMap.put(KeyStroke.getKeyStroke("W"), "moverArriba");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moverAbajo");
        inputMap.put(KeyStroke.getKeyStroke("A"), "moverIzquierda");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moverDerecha");

        // 2. Asignamos "Nombres de Acción" al código real que se va a ejecutar
        actionMap.put("moverArriba", new AccionMovimiento("w"));
        actionMap.put("moverAbajo", new AccionMovimiento("s"));
        actionMap.put("moverIzquierda", new AccionMovimiento("a"));
        actionMap.put("moverDerecha", new AccionMovimiento("d"));
    }

    // Clase interna que define lo que ocurre al pulsar la tecla
    private class AccionMovimiento extends AbstractAction {
        private String direccion;

        public AccionMovimiento(String direccion) {
            this.direccion = direccion;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // El tamaño del mapa viene dado por el tamaño actual del panel
            // (VentanaPrincipal es 1280x720)
            int anchoMapa = panel.getWidth();
            int altoMapa = panel.getHeight();

            moverConLimites(direccion, anchoMapa, altoMapa);
            // Le pedimos al panel que se vuelva a dibujar
            panel.repaint();

        }

        private void moverConLimites(String dir, int limiteX, int limiteY) {
            int vel = personaje.getVelocidad();
            int currentX = personaje.getPosX();
            int currentY = personaje.getPosY();

            switch (dir.toLowerCase()) {
                case "w": // Arriba
                    if (currentY - vel >= 0) {
                        personaje.setPosY(currentY - vel);
                    } else {
                        personaje.setPosY(0); // Tope arriba
                    }
                    break;
                case "s": // Abajo
                    if (currentY + vel <= limiteY - altoPersonaje) {
                        personaje.setPosY(currentY + vel);
                    } else {
                        personaje.setPosY(limiteY - altoPersonaje); // Tope abajo
                    }
                    break;
                case "a": // Izquierda
                    if (currentX - vel >= 0) {
                        personaje.setPosX(currentX - vel);
                    } else {
                        personaje.setPosX(0); // Tope izquierda
                    }
                    break;
                case "d": // Derecha
                    if (currentX + vel <= limiteX - anchoPersonaje) {
                        personaje.setPosX(currentX + vel);
                    } else {
                        personaje.setPosX(limiteX - anchoPersonaje); // Tope derecha
                    }
                    break;
            }
        }
    }
}
