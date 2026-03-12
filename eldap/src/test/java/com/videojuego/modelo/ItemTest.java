package com.videojuego.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void aplicarA_curarPorcentaje_noSuperaMaximo() {
        Personaje p = new Personaje("Ana", "guerrero");
        p.recibirDanio(200);
        int vidaAntes = p.getVidaActual();

        Item vaper = new Item("Vaper", "", "/no-existe.png", 50, TipoEfecto.CURAR_PORCENTAJE, 10);
        String msg = vaper.aplicarA(p, new Enemigo("jessica"));

        assertTrue(p.getVidaActual() > vidaAntes);
        assertTrue(p.getVidaActual() <= p.getVidaMaxima());
        assertTrue(msg.contains("Recuperas"));
    }

    @Test
    void aplicarA_aumentarCritico_actualizaProbabilidades() {
        Personaje p = new Personaje("Ana", "guerrero");
        double base = p.getProbCritico();

        Item mantequilla = new Item("Mantequilla", "", "/no-existe.png", 40, TipoEfecto.AUMENTAR_CRITICO, 10);
        mantequilla.aplicarA(p, new Enemigo("jessica"));

        assertEquals(base + 0.40, p.getProbCritico(), 0.0001);
        assertEquals(p.getProbCritico(), p.getProbCriticoActual(), 0.0001);
    }

    @Test
    void aplicarA_aumentarDefensa_sumaCantidad() {
        Personaje p = new Personaje("Ana", "guerrero");
        Item gabardina = new Item("Gabardina", "", "/no-existe.png", 30, TipoEfecto.AUMENTAR_DEFENSA, 10);
        gabardina.aplicarA(p, new Enemigo("jessica"));

        assertEquals(70, p.getDefensa());
    }

    @Test
    void aplicarA_robarVida_afectaAmbos() {
        Personaje p = new Personaje("Ana", "guerrero");
        Enemigo e = new Enemigo("jessica");
        p.recibirDanio(200);

        int vidaJugadorAntes = p.getVidaActual();
        int vidaEnemigoAntes = e.getVidaActual();

        Item chatto = new Item("Chatto", "", "/no-existe.png", 60, TipoEfecto.ROBAR_VIDA, 10);
        chatto.aplicarA(p, e);

        assertEquals(vidaEnemigoAntes - 60, e.getVidaActual());
        assertEquals(vidaJugadorAntes + 60, p.getVidaActual());
    }

    @Test
    void aplicarA_criticoSeguro_seteaProbCriticoActual() {
        Personaje p = new Personaje("Ana", "guerrero");
        Item examen = new Item("Examen", "", "/no-existe.png", 0, TipoEfecto.CRITICO_SEGURO, 10);
        examen.aplicarA(p, new Enemigo("jessica"));
        assertEquals(1.0, p.getProbCriticoActual(), 0.0001);
    }

    @Test
    void aplicarA_pactoVidaAtaque_ajustaStats() {
        Personaje p = new Personaje("zeio", "guerrero");
        int vidaMaxAntes = p.getVidaMaxima();
        int ataqueAntes = p.getAtaque();

        Item virus = new Item("Virus", "", "/no-existe.png", 50, TipoEfecto.PACTO_VIDA_ATAQUE, 10);
        virus.aplicarA(p, new Enemigo("jessica"));

        int vidaMaxEsperada = vidaMaxAntes - (vidaMaxAntes * 20 / 100);
        assertEquals(vidaMaxEsperada, p.getVidaMaxima());
        assertEquals(ataqueAntes + 50, p.getAtaque());
        assertTrue(p.getVidaActual() <= p.getVidaMaxima());
    }
}
