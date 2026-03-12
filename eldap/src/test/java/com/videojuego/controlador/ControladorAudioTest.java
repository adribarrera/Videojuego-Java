package com.videojuego.controlador;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControladorAudioTest {

    @Test
    void singleton_noEsNulo() {
        assertNotNull(ControladorAudio.getInstance());
    }

    @Test
    void setYGetVolumenGlobal() {
        ControladorAudio audio = ControladorAudio.getInstance();
        audio.setVolumenGlobal(42);
        assertEquals(42, audio.getVolumenGlobal());
    }

    @Test
    void reproducirYDetener_noLanzaExcepcion() {
        ControladorAudio audio = ControladorAudio.getInstance();
        assertDoesNotThrow(() -> audio.reproducirMusicaAmbiental("/no-existe.wav"));
        assertDoesNotThrow(audio::detenerMusica);
    }
}
