package controlador;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Colisiones {
    private ArrayList<Rectangle> muros;

    public Colisiones() {
        muros = new ArrayList<>();
        crearMuros();
    }

    private void crearMuros() {
        muros.add(new Rectangle(0, 0, 230, 300));           // Esquina superior izquierda
        muros.add(new Rectangle(20, 430, 210, 200));        // Esquina inferior izquierda
        muros.add(new Rectangle(1200, 0, 60, 330));         // Esquina superior derecha
        muros.add(new Rectangle(1035, 470,225, 160));       // Esquina inferior derecha

        muros.add(new Rectangle(1045, 220, 155, 110));      // Hueco derecho inferior a Sala Boss
        muros.add(new Rectangle(990, 220, 55, 135));        // Hueco derecho inferior a Sala Boss 2

        muros.add(new Rectangle(820, 220, 50, 140));        // Hueco izquierdo inferior a Sala Boss
        muros.add(new Rectangle(695, 220, 125, 90));        // Hueco izquierdo inferior a Sala Boss 2

        muros.add(new Rectangle(490, 0, 205, 310));         // Hueco centro
        muros.add(new Rectangle(400, 220, 45, 300));        // Hueco de la izquierda de la sala central

        muros.add(new Rectangle(0, 300, 20, 380));          // Borde izquierdo
        muros.add(new Rectangle(0, 630, 1270, 50));         // Borde inferior
        muros.add(new Rectangle(1240, 330, 20, 140));       // Borde derecho

        muros.add(new Rectangle(230, 0, 260, 105));         // Muro superior Sala Servidores

        muros.add(new Rectangle(695, 0, 55, 70));           // Sala Boss esquina izquierda
        muros.add(new Rectangle(1145, 0, 55, 70));          // Sala Boss esquina derecha
        muros.add(new Rectangle(750, 0, 395, 40));          // Sala Boss muro superior

        muros.add(new Rectangle(265, 220, 10, 95));         // Pared esquina superior Sala Izquierda
        muros.add(new Rectangle(265, 390, 10, 125));        // Pared esquina inferior Sala Izquierda

        muros.add(new Rectangle(445, 470,125, 50));         // Pared inferior izquierda Sala Central
        muros.add(new Rectangle(695, 470,175, 50));         // Pared inferior derecha Sala Central
        muros.add(new Rectangle(825, 430,45, 40));          // Pared inferior derecha Sala Central 2

        muros.add(new Rectangle(990, 430,45, 90));          // Pared inferior esquina derecha

        muros.add(new Rectangle(920, 40,60, 50));           // Portal
    }

    public boolean verificarMovimiento(int x, int y, int ancho, int alto) {
        Rectangle futuroPersonaje = new Rectangle(x, y, ancho, alto);

        for (Rectangle muro : muros) {
            if (futuroPersonaje.intersects(muro)) {
                return false; // Choca - No permitir movimiento
            }
        }
        return true; // No choca con nada
    }

    // Añadimos este 'getter' para que PanelMapa pueda dibujar los rectángulos en modo Debug
    public ArrayList<Rectangle> getMuros() {
        return muros;
    }
}
