package com.videojuego.controlador;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class UtilidadesAudio {

    private static long ultimoSonidoPaso = 0;
    private static final int COOLDOWN_PASOS = 200; // Milisegundos entre paso y paso

    public static void resetCooldownPasos() {
        ultimoSonidoPaso = 0;
    }

    public static void reproducirSonido(String archivoActivacion) {
        // Evitamos que los pasos se reproduzcan a lo loco filtrando solo
        // "pasos.wav"
        if ("pasos.wav".equals(archivoActivacion)) {
            long tiempoActual = System.currentTimeMillis();
            if (tiempoActual - ultimoSonidoPaso < COOLDOWN_PASOS) {
                return; // Ignoramos si no ha pasado el tiempo adecuado
            }
            ultimoSonidoPaso = tiempoActual;
        }

        try {
            URL urlSonido = UtilidadesAudio.class.getResource("/assets/audio/" + archivoActivacion);
            if (urlSonido != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlSonido);
                Clip clip = AudioSystem.getClip();
                clip.addLineListener(event -> {
                    if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                        event.getLine().close();
                    }
                });
                clip.open(audioStream);
                clip.start();
            } else {
                System.err.println("No se encontró el archivo de audio: " + archivoActivacion);
            }
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido " + archivoActivacion + ": " + e.getMessage());
        }
    }
}
