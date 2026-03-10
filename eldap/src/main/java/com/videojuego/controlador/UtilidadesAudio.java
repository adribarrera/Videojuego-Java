package com.videojuego.controlador;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class UtilidadesAudio {

    public static void reproducirSonido(String archivoActivacion) {
        try {
            URL urlSonido = UtilidadesAudio.class.getResource("/assets/audio/" + archivoActivacion);
            if (urlSonido != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlSonido);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
                // Opcional: Para evitar fugas de memoria, se puede cerrar al acabar (pero al
                // ser sonidos rápidos y Swing, no es 100% vital si es ligero, aunque es buena
                // práctica).
            } else {
                System.err.println("No se encontró el archivo de audio: " + archivoActivacion);
            }
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido " + archivoActivacion + ": " + e.getMessage());
        }
    }
}
