package com.videojuego.modelo;

/**
 * Interfaz fundamental para cualquier objeto que participe en el sistema de combate.
 * Define el contrato básico de interacción entre jugadores y enemigos.
 */
public interface Entidad {
    /**
     * Realiza un ataque contra otra entidad.
     * @param objetivo La entidad receptora del daño.
     * @return true si el ataque resultó en un golpe crítico.
     */
    boolean atacar(Entidad objetivo);

    /**
     * Procesa la recepción de daño reduciendo los puntos de vida.
     * @param cantidad Cantidad bruta de daño a aplicar.
     */
    void recibirDanio(int cantidad);

    /**
     * Comprueba si la entidad aún tiene puntos de vida disponibles.
     * @return true si vidaActual > 0.
     */
    boolean estaVivo();
}
