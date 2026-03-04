package controlador;

import modelo.Personaje;
import vista.PanelMapa; // Importamos tu panel específico
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

public class ControladorMovimiento {

    private Personaje personaje;
    private PanelMapa panel; // CAMBIO 1: Usamos PanelMapa en lugar de JPanel
    private int anchoPersonaje;
    private int altoPersonaje;

    // El constructor recibe el Modelo (Personaje) y la Vista (PanelMapa)
    public ControladorMovimiento(Personaje personaje, PanelMapa panel, int anchoPersonaje, int altoPersonaje) {
        this.personaje = personaje;
        this.panel = panel;
        this.anchoPersonaje = anchoPersonaje;
        this.altoPersonaje = altoPersonaje;

        configurarControles();
    }

    private void configurarControles() {
        // JPanel.WHEN_IN_FOCUSED_WINDOW es vital para que funcione sin tener que hacer clic antes
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
            // Posición actual
            int actualX = personaje.getPosX();
            int actualY = personaje.getPosY();
            
            // Calculamos la POSICIÓN FUTURA (Tentativa)
            int nuevaX = actualX;
            int nuevaY = actualY;

            switch (dir.toLowerCase()) {
                case "w": nuevaY -= vel; break;
                case "s": nuevaY += vel; break;
                case "a": nuevaX -= vel; break;
                case "d": nuevaX += vel; break;
            }

            // --- FASE 1: LÍMITES DE PANTALLA (Corregir si se sale del marco) ---
            if (nuevaX < 0) nuevaX = 0;
            if (nuevaY < 0) nuevaY = 0;
            if (nuevaX > limiteX - anchoPersonaje) nuevaX = limiteX - anchoPersonaje;
            if (nuevaY > limiteY - altoPersonaje) nuevaY = limiteY - altoPersonaje;

            // --- FASE 2: DETECCIÓN DE MUROS (La lógica nueva) ---
            // Preguntamos al PanelMapa: "¿Puedo ponerme en nuevaX, nuevaY?"
            if (panel.verificarMovimiento(nuevaX, nuevaY, anchoPersonaje, altoPersonaje)) {
                // Si el panel dice TRUE (no hay muro), aplicamos el cambio
                personaje.setPosX(nuevaX);
                personaje.setPosY(nuevaY);
            } else {
                // Si dice FALSE (choca), no hacemos nada.
                // Opcional: Podrías reproducir un sonido de choque aquí.
                System.out.println("¡Chocaste contra un muro!");
            }
        }
    }
}