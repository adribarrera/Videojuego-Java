package com.videojuego.controlador;

import javax.sound.sampled.*;
import java.net.URL;

/**
 * Gestor centralizado de audio que utiliza el patrón Singleton.
 * Se encarga de la reproducción de música ambiental en bucle y del control de volumen maestro.
 */
public class ControladorAudio {
    
    // Instancia única (Singleton)
    private static ControladorAudio instancia = null;
    
    private Clip musicaFondo;
    private int volumenGlobal = 100; // 0 a 100
    
    // Constructor privado para evitar que otras clases hagan un "new ControladorAudio()"
    private ControladorAudio() {
    }
    
    // Metodo para obtener la única instancia
    public static ControladorAudio getInstance() {
        if (instancia == null) {
            instancia = new ControladorAudio();
        }
        return instancia;
    }
    
    /**
     * Carga y reproduce una pista de audio en bucle continuo.
     * Si ya hay una pista sonando, la detiene y libera sus recursos antes de empezar.
     * @param rutaArchivo Ruta del archivo de audio (.wav) en el classpath.
     */
    public void reproducirMusicaAmbiental(String rutaArchivo) {
        detenerMusica(); // Nos aseguramos de apagar lo que hubiera sonando
        
        try {
            URL urlMusica = getClass().getResource(rutaArchivo);
            if (urlMusica != null) {
                AudioInputStream audioInst = AudioSystem.getAudioInputStream(urlMusica);
                musicaFondo = AudioSystem.getClip();
                musicaFondo.open(audioInst);
                
                // Aplicamos el volumen actual antes de darle al play
                aplicarVolumenClip();
                
                musicaFondo.loop(Clip.LOOP_CONTINUOUSLY); // Reproducción en bucle eterno
            } else {
                System.err.println("ERROR: No se encontró el audio de fondo en: " + rutaArchivo);
            }
        } catch (Exception e) {
            System.err.println("Excepción al cargar el audio: " + rutaArchivo);
            e.printStackTrace();
        }
    }
    
    public void detenerMusica() {
        if (musicaFondo != null) {
            if (musicaFondo.isRunning()) {
                musicaFondo.stop();
            }
            if (musicaFondo.isOpen()) {
                musicaFondo.close(); // Liberamos los recursos de la pista anterior
            }
        }
    }
    
    // Modifica la variable global y ajusta los decibelios en tiempo real
    public void setVolumenGlobal(int porcentaje) {
        this.volumenGlobal = porcentaje;
        aplicarVolumenClip();
    }
    
    public int getVolumenGlobal() {
        return this.volumenGlobal;
    }
    
    // Lógica matemática interna para pasar de "0-100" a los Decibelios de Java Sound
    private void aplicarVolumenClip() {
        if (musicaFondo != null && musicaFondo.isOpen()) {
            try {
                FloatControl control = (FloatControl) musicaFondo.getControl(FloatControl.Type.MASTER_GAIN);
                
                if (volumenGlobal == 0) {
                    control.setValue(control.getMinimum()); // Silencio absoluto
                } else {
                    float decibelios = (float) (Math.log10(volumenGlobal / 100.0) * 20.0);
                    control.setValue(decibelios);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("El Clip actual no soporta control de volumen Master Gain.");
            }
        }
    }
}
