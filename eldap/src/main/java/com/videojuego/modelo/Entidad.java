package com.videojuego.modelo;

public interface Entidad {
    boolean atacar(Entidad objetivo);

    void recibirDanio(int cantidad);

    boolean estaVivo();
}
