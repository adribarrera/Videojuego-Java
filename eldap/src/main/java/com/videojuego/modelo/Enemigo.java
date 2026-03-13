package com.videojuego.modelo;

/**
 * Representa a los oponentes que el jugador encuentra en el mapa.
 * Define estadísticas preestablecidas según el tipo de jefe.
 */
public class Enemigo implements Entidad {
    protected String nombre;
    protected int vidaMaxima;
    protected int vidaActual;
    protected int ataque;
    protected double probCritico;

    /**
     * Inicializa un enemigo con estadísticas fijas según su identidad.
     * @param tipoEnemigo Nombre del jefe (ej: "Soraya", "Sergio").
     */
    public Enemigo(String tipoEnemigo) {
        this.nombre = tipoEnemigo;

        switch (tipoEnemigo.toLowerCase()) {
            case "soraya":
                this.vidaMaxima = 1500;
                this.ataque = 120;
                this.probCritico = 0.075;
                break;
            case "jessica":
                this.vidaMaxima = 600;
                this.ataque = 50;
                this.probCritico = 0.025;
                break;

            case "juan carlos":
                this.vidaMaxima = 800;
                this.ataque = 100;
                this.probCritico = 0.025;
                break;
            case "sergio":
                this.vidaMaxima = 3000;
                this.ataque = 200;
                this.probCritico = 0.05;
                break;
            default:
                this.vidaMaxima = 600;
                this.ataque = 50;
                this.probCritico = 0.025;
                break;
        }
        this.vidaActual = this.vidaMaxima;
    }

    /**
     * Ejecuta un ataque contra el jugador.
     * Incluye una variación de daño aleatoria y cálculo de críticos.
     * @param objetivo La entidad que recibe el ataque.
     * @return true si el ataque fue un golpe crítico.
     */
    @Override
    public boolean atacar(Entidad objetivo) {
        // RNG: el ataque variará entre un 85% y un 115% de su valor base
        double multiplicadorRNG = 0.85 + (Math.random() * 0.30);
        int danioFinal = (int) (this.ataque * multiplicadorRNG);
        boolean esCritico = false;

        if (Math.random() < this.probCritico) {
            System.out.println(nombre + " asesta un GOLPE CRÍTICO SALVAJE");
            danioFinal = danioFinal * 3;
            esCritico = true;
        }

        objetivo.recibirDanio(danioFinal);
        return esCritico;
    }

    @Override
    public void recibirDanio(int cantidad) {
        this.vidaActual = this.vidaActual - cantidad;

        if (this.vidaActual < 0) {
            this.vidaActual = 0;
        }

        System.out.println(nombre + " ha recibido " + cantidad + " puntos de daño.");
    }

    @Override
    public boolean estaVivo() {
        if (this.vidaActual > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void recibirDanioDirecto(int cantidad) {
        this.vidaActual = this.vidaActual - cantidad;

        if (this.vidaActual < 0) {
            this.vidaActual = 0;
        }

        System.out.println(nombre + " ha recibido " + cantidad + " puntos de DAÑO DIRECTO.");
    }

    // --- Getters y Setters ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public double getProbCritico() {
        return probCritico;
    }

    public void setProbCritico(double probCritico) {
        this.probCritico = probCritico;
    }

}
