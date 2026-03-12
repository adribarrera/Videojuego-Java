package com.videojuego.controlador;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UtilidadesAudioTest {

    @Test
    void reproducirSonido_noLanzaExcepcion() {
        assertDoesNotThrow(UtilidadesAudio::resetCooldownPasos);
        assertDoesNotThrow(() -> UtilidadesAudio.reproducirSonido("pasos.wav"));
        assertDoesNotThrow(() -> UtilidadesAudio.reproducirSonido("pasos.wav"));
        assertDoesNotThrow(() -> UtilidadesAudio.reproducirSonido("no-existe.wav"));
    }
}
