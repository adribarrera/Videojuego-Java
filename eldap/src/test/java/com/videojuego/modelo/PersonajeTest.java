package com.videojuego.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonajeTest {

    @Test
    void constructor_configuraStatsPorClase() {
        Personaje guerrero = new Personaje("zeio", "guerrero");
        assertEquals("zeio", guerrero.getNombre());
        assertEquals("guerrero", guerrero.getClaseElegida());
        assertEquals(600, guerrero.getVidaMaxima());
        assertEquals(600, guerrero.getVidaActual());
        assertEquals(75, guerrero.getAtaque());
        assertEquals(40, guerrero.getDefensa());
        assertEquals(0.04, guerrero.getProbCritico(), 0.0001);
        assertEquals(0.04, guerrero.getProbCriticoActual(), 0.0001);
        assertEquals(5, guerrero.getVelocidad());

        Personaje mago = new Personaje("Luis", "mago");
        assertEquals(400, mago.getVidaMaxima());
        assertEquals(120, mago.getAtaque());
        assertEquals(30, mago.getDefensa());
        assertEquals(0.08, mago.getProbCritico(), 0.0001);
        assertEquals(6, mago.getVelocidad());

        Personaje desconocido = new Personaje("X", "otra");
        assertEquals(500, desconocido.getVidaMaxima());
        assertEquals(75, desconocido.getAtaque());
        assertEquals(40, desconocido.getDefensa());
        assertEquals(0.025, desconocido.getProbCritico(), 0.0001);
        assertEquals(5, desconocido.getVelocidad());
    }

    @Test
    void recibirDanio_aplicaDefensaYNoBajaDeCero() {
        Personaje p = new Personaje("zeio", "guerrero");
        p.recibirDanio(100);
        assertEquals(540, p.getVidaActual());

        p.recibirDanio(1000);
        assertEquals(0, p.getVidaActual());
    }

    @Test
    void recibirDanio_siempreAlMenosUno() {
        Personaje p = new Personaje("zeio", "guerrero");
        p.setDefensa(75);
        int vidaInicial = p.getVidaActual();
        p.recibirDanio(1);
        assertEquals(vidaInicial - 1, p.getVidaActual());
    }

    @Test
    void atacar_sinCritico_danioEnRango() {
        Personaje p = new Personaje("zeio", "guerrero");
        p.setProbCriticoActual(0.0);
        Enemigo e = new Enemigo("jessica");
        int vidaInicial = e.getVidaActual();

        boolean critico = p.atacar(e);
        int danio = vidaInicial - e.getVidaActual();

        int min = (int) (p.getAtaque() * 0.85);
        int max = (int) (p.getAtaque() * 1.15);

        assertFalse(critico);
        assertTrue(danio >= min && danio <= max);
    }

    @Test
    void atacar_conCritico_reseteaProbCriticoActual() {
        Personaje p = new Personaje("zeio", "guerrero");
        p.setProbCriticoActual(1.0);
        Enemigo e = new Enemigo("jessica");

        boolean critico = p.atacar(e);

        assertTrue(critico);
        assertEquals(p.getProbCritico(), p.getProbCriticoActual(), 0.0001);
    }

    @Test
    void mejorarAtributosAlDerrotarBoss_aumentaYCuracionTotal() {
        Personaje p = new Personaje("zeio", "guerrero");
        p.setVidaActual(100);
        int vidaMaxAntes = p.getVidaMaxima();
        int ataqueAntes = p.getAtaque();
        int defensaAntes = p.getDefensa();

        p.mejorarAtributosAlDerrotarBoss();

        int vidaMaxDespues = p.getVidaMaxima();
        int ataqueDespues = p.getAtaque();
        int defensaDespues = p.getDefensa();

        assertTrue(vidaMaxDespues >= vidaMaxAntes + 80 && vidaMaxDespues <= vidaMaxAntes + 120);
        assertTrue(ataqueDespues >= ataqueAntes + 15 && ataqueDespues <= ataqueAntes + 25);
        assertTrue(defensaDespues >= defensaAntes + 1 && defensaDespues <= defensaAntes + 3);
        assertEquals(vidaMaxDespues, p.getVidaActual());
    }

    @Test
    void curar_noSuperaVidaMaxima() {
        Personaje p = new Personaje("zeio", "guerrero");
        p.recibirDanio(200);
        p.curar(1000);
        assertEquals(p.getVidaMaxima(), p.getVidaActual());
    }

    @Test
    void aumentarDefensa_sumaCantidad() {
        Personaje p = new Personaje("zeio", "guerrero");
        p.aumentarDefensa(10);
        assertEquals(50, p.getDefensa());
    }

    @Test
    void recogerItem_limitaInventario() {
        Personaje p = new Personaje("zeio", "guerrero");
        Item i1 = new Item("I1", "", "/no-existe.png", 10, TipoEfecto.CURAR_PORCENTAJE, 1);
        Item i2 = new Item("I2", "", "/no-existe.png", 10, TipoEfecto.CURAR_PORCENTAJE, 1);
        Item i3 = new Item("I3", "", "/no-existe.png", 10, TipoEfecto.CURAR_PORCENTAJE, 1);

        assertTrue(p.recogerItem(i1));
        assertTrue(p.recogerItem(i2));
        assertFalse(p.recogerItem(i3));
        assertEquals(2, p.getInventario().size());
    }

    @Test
    void usarItem_consumesSlot_yMensaje() {
        Personaje p = new Personaje("zeio", "guerrero");
        Enemigo e = new Enemigo("jessica");
        Item i1 = new Item("I1", "", "/no-existe.png", 10, TipoEfecto.CURAR_PORCENTAJE, 1);
        p.recogerItem(i1);

        String msg = p.usarItem(0, e);

        assertEquals(0, p.getInventario().size());
        assertTrue(msg.contains("Recuperas"));

        String msg2 = p.usarItem(0, e);
        assertEquals("No hay objeto en ese hueco.", msg2);
    }

    @Test
    void moverDireccion_actualizaPosicion() {
        Personaje p = new Personaje("zeio", "guerrero");
        p.moverDireccion("arriba");
        assertEquals(5, p.getPosY());
        p.moverDireccion("abajo");
        assertEquals(0, p.getPosY());
        p.moverDireccion("izquierda");
        assertEquals(5, p.getPosX());
        p.moverDireccion("derecha");
        assertEquals(0, p.getPosX());
    }
}
