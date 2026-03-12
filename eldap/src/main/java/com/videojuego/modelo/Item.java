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

    public String aplicarA(Personaje jugador, Enemigo enemigo) {
        switch (this.efecto) {
            case CURAR_PORCENTAJE: // VAPER
                int cura = (jugador.getVidaMaxima() * this.modificador) / 100;
                jugador.curar(cura);
                return "Le das una calada al vaper. Sabor guanábana.\nRecuperas un " + this.modificador
                        + "% de tu salud.";

            case DANIO_PORCENTAJE_ENEMIGO: // M.L.P.E
                enemigo.recibirDanioDirecto(this.modificador);
                return "Lanzas tropezones de mantequilla al enemigo.\nInfliges " + this.modificador + " de daño fijo.";

            case DUPLICAR_DEFENSA: // GABARDINA DE SERGIO
                jugador.aumentarDefensa(this.modificador);
                return "Te pones la gabardina de Sergio.\nTu resistencia aumenta un " + this.modificador + "% extra.";

            case ROBAR_VIDA: // EL CHATTO
                enemigo.recibirDanioDirecto(this.modificador);
                jugador.curar(this.modificador);
                return "El Chatto roba vitalidad al enemigo.\nLe quitas " + this.modificador + " HP y te los curas.";

            case CRITICO_SEGURO: // EXAMEN DE SORAYA
                jugador.setProbCriticoActual(1.0);
                return "Sacas el examen sorpresa de Soraya.\nTu siguiente ataque será un crítico del 100%.";

            case TRAMPA_VIRUS: // VIRUS DE LINUX
                int danioVirus = (jugador.getVidaMaxima() * 50) / 100;
                jugador.recibirDanioDirecto(danioVirus);
                return "Lanzas el virus al enemigo y...\n¡Oh no! El virus te corrompe: pierdes el 50% de tu vida.";

            default:
                return "Efecto desconocido.";
        }
    }

}
