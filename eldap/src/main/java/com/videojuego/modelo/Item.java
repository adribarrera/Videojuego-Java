package com.videojuego.modelo;

import javax.swing.ImageIcon; // Para manejar la imagen
import java.net.URL; // Para cargar la imagen desde archivos

public class Item {
    private String nombre;
    private String descripcion;
    private ImageIcon imagen;
    private int modificador; // Ejemplo: valor "50" a modificar
    private TipoEfecto efecto; // Sobre que se va a aplicar, para categorizar

    public Item(String nombre, String descripcion, String rutaImagen, int modificador, TipoEfecto efecto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modificador = modificador;
        this.efecto = efecto;

        cargarImagen(rutaImagen);
    }

    private void cargarImagen(String ruta) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            this.imagen = new ImageIcon(url);
        } else {
            System.err.println("ERROR: No se encuentra la imagen del ítem en: " + ruta);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public int getModificador() {
        return modificador;
    }

    public TipoEfecto getEfecto() {
        return efecto;
    }

    public void aplicarA(Personaje jugador, Enemigo enemigo) {
        System.out.println("Usando " + this.nombre + "...");

        switch (this.efecto) {
            case CURAR_PORCENTAJE: // VAPER
                System.out.println(
                        "Necesitas un break. Le das una calada al vaper. Disfrutas el sabor de la guanábana. Recuperas un "
                                + this.modificador + "% de tu salud");
                int cura = (jugador.getVidaMaxima() * this.modificador) / 100;
                jugador.curar(cura);
                break;

            case DANIO_PORCENTAJE_ENEMIGO: // M.L.P.E
                System.out.println("Abres la mantequilla y le lanzas tropezones al enemigo. Inglinges un "
                        + this.modificador + "% de daño de la vida del enemigo.");
                int danioMantequilla = (enemigo.getVidaMaxima() * this.modificador) / 100;
                enemigo.recibirDanioDirecto(danioMantequilla);
                break;

            case DUPLICAR_DEFENSA: // GABARDINA DE SERGIO
                System.out.println(
                        "Te pones la gabardina de Sergio. Ahora si que no hay quien te pare. Duplica el valor de tu escudo.");
                jugador.aumentarDefensa(jugador.getDefensa());
                break;

            case ROBAR_VIDA: // EL CHATTO
                System.out.println("El Chatto le roba las ideas al enemigo. Le quitas al enemigo el " + this.modificador
                        + "% de su vida y te la curas con ella");
                int vidaRobada = (enemigo.getVidaMaxima() * this.modificador) / 100;
                enemigo.recibirDanioDirecto(vidaRobada);
                jugador.curar(vidaRobada);
                break;

            case CRITICO_SEGURO: // GAFAS DE SORAYA
                System.out.println(
                        "Te pones las Gafas de Soraya. Ves todo con más claridad, hasta los puntos débiles del enemigo.");
                System.out.println("Tu siguiente ataque infligirá un crítico del 100%");
                jugador.setProbCriticoActual(1.0); // 100% de probabilidad
                break;

            case TRAMPA_GUANTON: // GUANTÓN DE JUAN CARLOS
                System.out.println("¡Usas el Guantón de Juan Carlos! Esas manos han vivido mucho...");
                System.out.println("Infliges un 200% de daño asegurado a... ");
                System.out.println("¡OH NO, ESPERA!");
                System.out.println("¡Te la ha liado! ¡La mano te ha metido el guantón a ti!");
                // El jugador se come un % de su propia vida máxima (Ej: 20%)
                int danioGuanton = (jugador.getVidaMaxima() * this.modificador) / 100;
                jugador.recibirDanioDirecto(danioGuanton);
                break;

            default:
                System.out.println("Efecto desconocido.");
                break;
        }
    }

}