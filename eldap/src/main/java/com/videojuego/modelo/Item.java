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
                System.out.println("Te pones la gabardina de Sergio. Ahora si que no hay quien te pare.");
                System.out.println("Tu escudo aumenta un " + this.modificador + "% extra permanentemente.");
                jugador.aumentarDefensa(this.modificador);
                break;

            case ROBAR_VIDA: // EL CHATTO
                System.out.println("El Chatto le roba las ideas al enemigo. Le quitas al enemigo " + this.modificador
                        + " puntos de su vida y te curas con ella");
                enemigo.recibirDanioDirecto(this.modificador);
                jugador.curar(this.modificador);
                break;

            case CRITICO_SEGURO: // GAFAS DE SORAYA
                System.out.println(
                        "Te pones las Gafas de Soraya. Ves todo con más claridad, hasta los puntos débiles del enemigo.");
                System.out.println("Tu siguiente ataque infligirá un crítico del 100%");
                jugador.setProbCriticoActual(1.0); // 100% de probabilidad
                break;

            case TRAMPA_GUANTON: // GUANTÓN DE JUAN CARLOS O VIRUS DE LINUX
                System.out.println("¡Usas el objeto que acabas de comprar!");
                System.out.println("Infliges 1000 de daño asegurado al enemigo, de una.");
                enemigo.recibirDanioDirecto(1000);
                System.out.println("¡OH NO, ESPERA!");
                System.out.println("¡Esto tenía truco! ¡El rebote te hace daño a ti!");
                int danioGuanton = (jugador.getVidaMaxima() * this.modificador) / 100;
                jugador.recibirDanioDirecto(danioGuanton);
                break;

            default:
                System.out.println("Efecto desconocido.");
                break;
        }
    }

}