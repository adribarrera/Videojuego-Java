package com.videojuego.modelo;

import java.util.List;
import java.util.ArrayList;

public class Personaje implements Entidad {
    // 1. Atributos de Identidad y Combate
    protected String nombre;
    protected String claseElegida;
    protected int vidaMaxima;
    protected int vidaActual;
    protected int ataque;
    protected int defensa;
    protected double probCritico;
    protected double probCriticoActual;
    protected int dinero;

    // 2. Atributos de Movimiento
    protected int posX;
    protected int posY;
    protected int velocidad;

    // 3. Atributos de Inventario
    protected List<Item> inventario;
    protected final int MAX_OBJETOS = 2;

    // Constructor base
    public Personaje(String nombre, String claseElegida) {
        this.nombre = nombre;
        this.claseElegida = claseElegida;
        this.posX = 0;
        this.posY = 0;
        this.inventario = new ArrayList<>();

        switch (claseElegida.toLowerCase()) {
            case "guerrero":
                this.vidaMaxima = 250;
                this.ataque = 250;
                this.defensa = 40; // 40% de reducción de daño
                this.probCritico = 0.10;
                this.velocidad = 5;
                break;
            case "mago":
                this.vidaMaxima = 200;
                this.ataque = 300;
                this.defensa = 15; // 15% de reducción de daño
                this.probCritico = 0.30;
                this.velocidad = 10;
                break;
            case "asesino":
                this.vidaMaxima = 250;
                this.ataque = 350;
                this.defensa = 25; // 25% de reducción de daño
                this.probCritico = 0.50;
                this.velocidad = 15;
                break;
            default:
                this.vidaMaxima = 100;
                this.ataque = 100;
                this.defensa = 10;
                this.probCritico = 0.10;
                this.velocidad = 5;
                break;
        }

        this.vidaActual = this.vidaMaxima; // Así la vida del personaje al nacer es igual a su vida máxima.
        this.probCriticoActual = this.probCritico;
        this.dinero = 0;
    }

    // --- MÉTODOS DE MOVIMIENTO ---

    // Moverse en una dirección

    public void moverDireccion(String direccion) {
        switch (direccion.toLowerCase()) {
            case "arriba":
                this.posY = this.posY + velocidad;
                break;
            case "abajo":
                this.posY = this.posY - velocidad;
                break;
            case "izquierda":
                this.posX = this.posX + velocidad;
                break;
            case "derecha":
                this.posX = this.posX - velocidad;
                break;

            default:
                System.err.println("Dirección no válida");
                return;
        }

    }

    // --- MÉTODOS DE COMBATE ---

    @Override
    public void atacar(Entidad enemigo) {
        int danioFinal = this.ataque;

        if (Math.random() < this.probCriticoActual) {
            System.out.println(nombre + " asesta un GOLPE CRÍTICO");
            danioFinal = danioFinal * 2;

            if (this.probCriticoActual >= 1.0) { // Si se han usado las gafas de Soraya, hay que devolverlo a lo normal
                this.probCriticoActual = this.probCritico; // Se resetea
            }
        }
        System.out.println(
                this.nombre + " el " + this.claseElegida + " ataca haciendo " + danioFinal + " puntos de daño.");

        enemigo.recibirDanio(danioFinal);
    }

    // Métodos comunes para todos los personajes

    @Override
    public void recibirDanio(int cantidad) {

        // La defensa reduce el daño recibido de forma porcentual (Ej: 30 de defensa =
        // 30% menos daño)
        double porcentajeReduccion = Math.min(this.defensa, 90) / 100.0; // Cap a 90% para nunca ser invencible
        int danioReducido = (int) (cantidad * (1.0 - porcentajeReduccion));

        // Siempre nos harán al menos 1 punto de daño si nos golpean de forma normal
        int danioReal = Math.max(1, danioReducido);

        this.vidaActual = this.vidaActual - danioReal;

        System.out.println(nombre + " recibe " + danioReal + " puntos de daño.");

        if (this.vidaActual <= 0) {
            this.vidaActual = 0;
            System.out.println(nombre + " ha sido derrotado...");
        }
    }

    // --- ESCALADO DE ESTADÍSTICAS ---
    public void mejorarAtributosAlDerrotarBoss() {
        this.vidaMaxima += 100;
        this.ataque += 50;

        // La defensa la subimos muy poco a poco porque escala porcentualmente
        if (this.defensa < 85) {
            this.defensa += 5;
        }

        // Curación total al derrotar al jefe
        this.vidaActual = this.vidaMaxima;

        System.out.println(nombre + " se hace más fuerte. ¡Sus estadísticas han aumentado!");
    }

    @Override
    public boolean estaVivo() {
        if (this.vidaActual > 0) {
            return true;
        } else {
            return false;
        }
    }

    // --- METODOS DE ITEMS ---

    public void recibirDanioDirecto(int cantidad) { // Para el Chatto y el Guantón de Juan Carlos
        this.vidaActual = this.vidaActual - cantidad;
        if (this.vidaActual < 0) {
            this.vidaActual = 0;
        }
        System.out.println(nombre + " sufre " + cantidad + " de DAÑO DIRECTO");
    }

    public void curar(int cantidad) {
        this.vidaActual = this.vidaActual + cantidad;
        if (this.vidaActual > this.vidaMaxima) {
            this.vidaActual = this.vidaMaxima;
        }
        System.out.println(nombre + " se cura.");
    }

    public void aumentarDefensa(int cantidad) {
        this.defensa = this.defensa + cantidad;
        System.out.println("La defensa de " + nombre + " sube a " + this.defensa);
    }

    // METODOS DE INVENTARIO
    public boolean recogerItem(Item nuevoItem) {
        if (inventario.size() < MAX_OBJETOS) {
            inventario.add(nuevoItem);
            return true;
        } else {
            return false;
        }
    }

    public void usarItem(int indice, Enemigo enemigoEnCombate) {
        if (indice >= 0 && indice < inventario.size()) {
            Item item = inventario.get(indice);
            item.aplicarA(this, enemigoEnCombate);
            inventario.remove(indice);
        } else {
            System.out.println("No hay objeto en ese hueco");
        }
    }

    // Getters y Settets
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClaseElegida() {
        return claseElegida;
    }

    public void setClaseElegida(String claseElegida) {
        this.claseElegida = claseElegida;
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

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public double getProbCritico() {
        return probCritico;
    }

    public void setProbCritico(double probCritico) {
        this.probCritico = probCritico;
    }

    public double getProbCriticoActual() {
        return probCriticoActual;
    }

    public void setProbCriticoActual(double probCriticoActual) {
        this.probCriticoActual = probCriticoActual;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public List<Item> getInventario() {
        return inventario;
    }

}
