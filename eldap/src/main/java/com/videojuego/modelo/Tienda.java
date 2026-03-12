package com.videojuego.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tienda {
    private String nombre;
    private List<Item> inventarioTienda;

    public Tienda(String nombre) {
        this.nombre = nombre;
        this.inventarioTienda = new ArrayList<>();
        cargarMaquinaExpendedora();
    }

    private void cargarMaquinaExpendedora() {
        inventarioTienda.add(new Item("Vaper de Guanábana", "Recuperas un 50% de tu salud máxima.",
                "/assets/imagenes/Objetos/Normal/vaper.png", 50, TipoEfecto.CURAR_PORCENTAJE, 80));
        inventarioTienda.add(new Item("Mantequilla L.P.E.",
                "Tu probabilidad de crítico aumenta de forma permanente un 10%.",
                "/assets/imagenes/Objetos/Normal/mantequilla.png", 40,
                TipoEfecto.AUMENTAR_CRITICO, 150));
        inventarioTienda.add(new Item("Gabardina de Sergio", "Aumenta permanentemente tu defensa en 10 puntos extra.",
                "/assets/imagenes/Objetos/Normal/gabardina.png", 30, TipoEfecto.AUMENTAR_DEFENSA, 120));
        inventarioTienda.add(new Item("El Chatto", "Robas 600 puntos de vida al enemigo y te curas con ello.",
                "/assets/imagenes/Objetos/Normal/chato.png", 600, TipoEfecto.ROBAR_VIDA, 225));
        inventarioTienda
                .add(new Item("Examen Sorpresa de Soraya", "Tu siguiente ataque será un golpe crítico del 100%.",
                        "/assets/imagenes/Objetos/Normal/examen.png", 0, TipoEfecto.CRITICO_SEGURO, 150));
        inventarioTienda.add(new Item("Virus de Linux",
                "Pierdes un 20% de tu vida máxima a cambio de +50 de ataque permanente.",
                "/assets/imagenes/Objetos/Normal/virus.png", 50, TipoEfecto.PACTO_VIDA_ATAQUE, 50));
    }

    public String obtenerInfoItems(int indice) {
        if (indice >= 0 && indice < inventarioTienda.size()) {
            Item item = inventarioTienda.get(indice);
            return item.getNombre() + "\nPrecio: " + item.getPrecio() + " monedas \n" + item.getDescripcion();
        }
        return "Espacio vacío";
    }

    public String procesarCompra(int indice, Personaje comprador) {
        if (indice < 0 || indice >= inventarioTienda.size()) {
            return "Selección inválida.";
        }

        Item objeto = inventarioTienda.get(indice);

        // Compruebo que el personaje tiene dinero
        if (comprador.getDinero() < objeto.getPrecio()) {
            return "Te faltan " + (objeto.getPrecio() - comprador.getDinero()) + " monedas para comprar "
                    + objeto.getNombre();
        }

        boolean sePuedeGuardar = comprador.recogerItem(objeto);

        if (sePuedeGuardar) {
            comprador.setDinero(comprador.getDinero() - objeto.getPrecio());
            com.videojuego.controlador.UtilidadesAudio.reproducirSonido("compra.wav");
            return "Compra realizada.\n" + objeto.getNombre() + " añadido a la mochila";
        } else {
            return "No puedes llevar más objetos en el inventario.\nTienes la mochila llena (Máx "
                    + comprador.MAX_OBJETOS + ").";
        }
    }
}
