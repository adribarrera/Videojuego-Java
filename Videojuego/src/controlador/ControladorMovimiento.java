package controlador;

import modelo.Personaje;
import vista.PanelMapa;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

public class ControladorMovimiento {

    private Personaje personaje;
    private PanelMapa panel; 
    private Colisiones colisiones;
    private int anchoPersonaje;
    private int altoPersonaje;

    // AÑADIMOS Colisiones al constructor
    public ControladorMovimiento(Personaje personaje, PanelMapa panel, Colisiones colisiones, int anchoPersonaje, int altoPersonaje) {
        this.personaje = personaje;
        this.panel = panel;
        this.colisiones = colisiones; 
        this.anchoPersonaje = anchoPersonaje;
        this.altoPersonaje = altoPersonaje;

        configurarControles();
    }

    private void configurarControles() {
        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("W"), "moverArriba");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moverAbajo");
        inputMap.put(KeyStroke.getKeyStroke("A"), "moverIzquierda");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moverDerecha");

        actionMap.put("moverArriba", new AccionMovimiento("w"));
        actionMap.put("moverAbajo", new AccionMovimiento("s"));
        actionMap.put("moverIzquierda", new AccionMovimiento("a"));
        actionMap.put("moverDerecha", new AccionMovimiento("d"));
    }

    private class AccionMovimiento extends AbstractAction {
        private String direccion;

        public AccionMovimiento(String direccion) {
            this.direccion = direccion;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int anchoMapa = panel.getWidth();
            int altoMapa = panel.getHeight();

            moverConLimites(direccion, anchoMapa, altoMapa);
            panel.repaint();

        }

        private void moverConLimites(String dir, int limiteX, int limiteY) {
            int vel = personaje.getVelocidad();
            int actualX = personaje.getPosX();
            int actualY = personaje.getPosY();
            
            int nuevaX = actualX;
            int nuevaY = actualY;

            switch (dir.toLowerCase()) {
                case "w": nuevaY -= vel; break;
                case "s": nuevaY += vel; break;
                case "a": nuevaX -= vel; break;
                case "d": nuevaX += vel; break;
            }

            // LÍMITES DE PANTALLA
            if (nuevaX < 0) nuevaX = 0;
            if (nuevaY < 0) nuevaY = 0;
            if (nuevaX > limiteX - anchoPersonaje) nuevaX = limiteX - anchoPersonaje;
            if (nuevaY > limiteY - altoPersonaje) nuevaY = limiteY - altoPersonaje;

            // --- LA MAGIA: Preguntamos DIRECTAMENTE a la clase Colisiones ---
            if (colisiones.verificarMovimiento(nuevaX, nuevaY, anchoPersonaje, altoPersonaje)) {
                personaje.setPosX(nuevaX);
                personaje.setPosY(nuevaY);
            } else {
                // Si entra aquí, es que ha chocado. Te saldrá este mensaje en la consola inferior
                System.out.println("¡BAM! Choque detectado intentando ir a X:" + nuevaX + " Y:" + nuevaY);
            }
        }
    }
}