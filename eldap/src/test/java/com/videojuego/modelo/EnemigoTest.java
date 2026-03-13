package com.videojuego.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemigoTest {

    @Test
    void constructor_configuraStatsPorTipo() {
        Enemigo soraya = new Enemigo("soraya");
        assertEquals("soraya", soraya.getNombre());
        assertEquals(1500, soraya.getVidaMaxima());
        assertEquals(1500, soraya.getVidaActual());
        assertEquals(120, soraya.getAtaque());
        assertEquals(0.075, soraya.getProbCritico(), 0.0001);

        Enemigo jessica = new Enemigo("jessica");
        assertEquals(600, jessica.getVidaMaxima());
        assertEquals(50, jessica.getAtaque());
        assertEquals(0.025, jessica.getProbCritico(), 0.0001);

        Enemigo juanCarlos = new Enemigo("juanCarlos");
        assertEquals(600, juanCarlos.getVidaMaxima());
        assertEquals(50, juanCarlos.getAtaque());
        assertEquals(0.025, juanCarlos.getProbCritico(), 0.0001);

        Enemigo sergio = new Enemigo("sergio");
        assertEquals(3000, sergio.getVidaMaxima());
        assertEquals(200, sergio.getAtaque());
        assertEquals(0.05, sergio.getProbCritico(), 0.0001);

        Enemigo desconocido = new Enemigo("otro");
        assertEquals(600, desconocido.getVidaMaxima());
        assertEquals(50, desconocido.getAtaque());
        assertEquals(0.025, desconocido.getProbCritico(), 0.0001);
    }

    @Test
    void recibirDanio_restaVidaYNoBajaDeCero() {
        Enemigo enemigo = new Enemigo("jessica");
        enemigo.recibirDanio(100);
        assertEquals(500, enemigo.getVidaActual());

        enemigo.recibirDanio(1000);
        assertEquals(0, enemigo.getVidaActual());
    }

    @Test
    void recibirDanioDirecto_restaVidaYNoBajaDeCero() {
        Enemigo enemigo = new Enemigo("jessica");
        enemigo.recibirDanioDirecto(250);
        assertEquals(350, enemigo.getVidaActual());

        enemigo.recibirDanioDirecto(1000);
        assertEquals(0, enemigo.getVidaActual());
    }

    @Test
    void estaVivo_dependeDeVidaActual() {
        Enemigo enemigo = new Enemigo("jessica");
        assertTrue(enemigo.estaVivo());

        enemigo.setVidaActual(0);
        assertFalse(enemigo.estaVivo());
    }

    @Test
    void atacar_sinCritico_danioEnRango() {
        Enemigo atacante = new Enemigo("jessica");
        atacante.setProbCritico(0.0);

        Enemigo objetivo = new Enemigo("jessica");
        int vidaInicial = objetivo.getVidaActual();

        boolean critico = atacante.atacar(objetivo);
        int danio = vidaInicial - objetivo.getVidaActual();

        int min = (int) (atacante.getAtaque() * 0.85);
        int max = (int) (atacante.getAtaque() * 1.15);

        assertFalse(critico);
        assertTrue(danio >= min && danio <= max);
    }

    @Test
    void atacar_conCritico_danioEnRango() {
        Enemigo atacante = new Enemigo("jessica");
        atacante.setProbCritico(1.0);

        Enemigo objetivo = new Enemigo("jessica");
        int vidaInicial = objetivo.getVidaActual();

        boolean critico = atacante.atacar(objetivo);
        int danio = vidaInicial - objetivo.getVidaActual();

        int min = (int) (atacante.getAtaque() * 0.85) * 3;
        int max = (int) (atacante.getAtaque() * 1.15) * 3;

        assertTrue(critico);
        assertTrue(danio >= min && danio <= max);
    }
}
