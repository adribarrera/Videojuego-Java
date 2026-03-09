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
        inventarioTienda.add(new Item("Vaper de Guanábana", "Recuperas un 25% de tu salud máxima.", "/assets/imagenes/Objetos/Normal/vaper.png", 25, TipoEfecto.CURAR_PORCENTAJE, 50));
        inventarioTienda.add(new Item("Mantequilla Lista Para Enfoscar (M.L.P.E)", "Inflige un 20% de daño al enemigo basándose en su vida máxima.", "/assets/imagenes/Objetos/Normal/mantequilla.png", 20, TipoEfecto.DANIO_PORCENTAJE_ENEMIGO, 80));
        inventarioTienda.add(new Item("Gabardina de Sergio", "Duplica temporalmente tu defensa.", "/assets/imagenes/Objetos/Normal/gabardina.png", 0, TipoEfecto.DUPLICAR_DEFENSA, 120));
        inventarioTienda.add(new Item("El Chatto", "Roba un 30% de vida al enemigo y te curas con ello.", "/assets/imagenes/Objetos/Normal/chato.png", 30, TipoEfecto.ROBAR_VIDA, 150));
        inventarioTienda.add(new Item("Examen Sorpresa de Soraya", "Tu siguiente ataque será un golpe crítico del 100%.", "/assets/imagenes/Objetos/Normal/examen.png", 0, TipoEfecto.CRITICO_SEGURO, 200));
        inventarioTienda.add(new Item("Virus de Linux", "Inflinges un 50% de daño al enemigo basándose en su vida máximo!", "/assets/imagenes/Objetos/Normal/virus.png", 20, TipoEfecto.TRAMPA_GUANTON, 50));
    }

    public String obtenerInfoItems(int indice) {
        if (indice >=0 && indice < inventarioTienda.size()) {
            Item item = inventarioTienda.get(indice);
            return item.getNombre() + " - Precio: " + item.getPrecio() + " monedas \n" + item.getDescripcion();  
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
            return "Te faltan " + (objeto.getPrecio() - comprador.getDinero()) + " monedas para comprar " + objeto.getNombre();
        }

        boolean sePuedeGuardar = comprador.recogerItem(objeto);

        if (sePuedeGuardar) {
            comprador.setDinero(comprador.getDinero() - objeto.getPrecio());
            return "Compra realizada." + objeto.getNombre() + " añadido a la mochila";
        } else {
            return "No puedes llevar más objetos en el inventario. Tienes la mochila llena (Máx " + comprador.MAX_OBJETOS + ").";
        }
    }
}
