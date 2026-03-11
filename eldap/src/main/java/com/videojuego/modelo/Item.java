package com.videojuego.modelo;

import javax.swing.ImageIcon; // Para manejar la imagen
import java.net.URL; // Para cargar la imagen desde archivos

public class Item {
    private String nombre;
    private String descripcion;
    private ImageIcon imagen;
    private int modificador; // Ejemplo: valor "50" a modificar
    private TipoEfecto efecto; // Sobre que se va a aplicar, para categorizar
    private int precio;
    private String rutaImagen;

    public Item(String nombre, String descripcion, String rutaImagen, int modificador, TipoEfecto efecto, int precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modificador = modificador;
        this.efecto = efecto;
        this.precio = precio;
        this.rutaImagen = rutaImagen;

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

    public String getRutaImagen() {
        return rutaImagen;
    }

    public int getModificador() {
        return modificador;
    }

    public TipoEfecto getEfecto() {
        return efecto;
    }

    public int getPrecio() {
        return precio;
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
                System.out.println("Abres la mantequilla y le lanzas tropezones al enemigo.");
                System.out.println("Infliges " + this.modificador + " puntos de daño fijo de puro enfoscamiento.");
                enemigo.recibirDanioDirecto(this.modificador);
                break;

            case DUPLICAR_DEFENSA: // GABARDINA DE SERGIO
                System.out.println("Te pones la gabardina de Sergio. Te vuelves casi invulnerable.");
                System.out.println("Tu resistencia al daño aumenta un " + this.modificador + "% extra permanentemente.");
                jugador.aumentarDefensa(this.modificador);
                break;

            case ROBAR_VIDA: // EL CHATTO
                System.out.println("El Chatto le roba la vitalidad al enemigo. Le quitas " + this.modificador
                        + " puntos de forma directa y te curas con ellos");
                enemigo.recibirDanioDirecto(this.modificador);
                jugador.curar(this.modificador);
                break;

            case CRITICO_SEGURO: // GAFAS DE SORAYA
                System.out.println(
                        "Te pones las Gafas de Soraya. Ves todo con más claridad, hasta los puntos débiles del enemigo.");
                System.out.println("Tu siguiente ataque infligirá un crítico del 100%");
                jugador.setProbCriticoActual(1.0); // 100% de probabilidad
                break;

            case TRAMPA_GUANTON: // VIRUS DE LINUX
                System.out.println("¡Usas el código fuente corrupto en tu enemigo!");
                System.out.println("Infliges 1500 de daño devastador.");
                enemigo.recibirDanioDirecto(1500);
                System.out.println("¡OH NO, ESPERA!");
                System.out.println("¡El virus corrompe tus propios sistemas! Pierdes el 50% de tu salud máxima.");
                int danioGuanton = (jugador.getVidaMaxima() * 50) / 100;
                jugador.recibirDanioDirecto(danioGuanton);
                break;

            default:
                System.out.println("Efecto desconocido.");
                break;
        }
    }

}