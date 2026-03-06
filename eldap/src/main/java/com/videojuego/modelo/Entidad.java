package com.videojuego.modelo;

public interface Entidad {

    void atacar(Entidad objetivo);

    void recibirDanio(int cantidad);

    boolean estaVivo();
}
