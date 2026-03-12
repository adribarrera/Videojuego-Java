package com.videojuego.modelo;

import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BossEnMapaTest {

    @Test
    void constructor_asignaCamposBasicos() {
        Rectangle area = new Rectangle(0, 0, 10, 10);
        BossEnMapa boss = new BossEnMapa("Soraya", 1, 2, "/no-existe.png", area);

        assertEquals("Soraya", boss.nombre);
        assertEquals(1, boss.posX);
        assertEquals(2, boss.posY);
        assertNotNull(boss.areaInteraccion);
    }
}
