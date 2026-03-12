package com.videojuego.vista;

import com.videojuego.modelo.Personaje;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PanelMapaTest {

    @Test
    void eliminarBoss_yReiniciarMapa_actualizaLista() throws Exception {
        PanelMapa panel = new PanelMapa();

        Field bossesField = PanelMapa.class.getDeclaredField("bossesEnMapa");
        bossesField.setAccessible(true);
        List<?> bosses = (List<?>) bossesField.get(panel);
        assertEquals(4, bosses.size());

        panel.eliminarBoss("Soraya");
        assertEquals(3, bosses.size());

        panel.reiniciarMapa();
        List<?> bossesReiniciado = (List<?>) bossesField.get(panel);
        assertEquals(4, bossesReiniciado.size());
    }

    @Test
    void setPersonajeJugador_yActualizarAnimacion_noLanza() {
        PanelMapa panel = new PanelMapa();
        Personaje nuevo = new Personaje("zeio", "guerrero");
        panel.setPersonajeJugador(nuevo);
        panel.actualizarAnimacion("w", 2);
        assertTrue(nuevo.getPosX() >= 0);
    }
}
