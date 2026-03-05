package modelo;

public class Personaje implements Entidad {
    // 1. Atributos de Identidad y Combate
    protected String nombre;
    protected String claseElegida;
    protected int vida;
    protected int ataque;
    protected int defensa;
    protected double critico;

    // 2. Atributos de Movimiento
    protected int posX;
    protected int posY;
    protected int velocidad;

    // Constructor base
    public Personaje(String nombre, String claseElegida) {
        this.nombre = nombre;
        this.claseElegida = claseElegida;
        this.posX = 0;
        this.posY = 0;

        switch (claseElegida.toLowerCase()) {
            case "guerrero":
                this.vida = 100;
                this.ataque = 100;
                this.defensa = 75;
                this.critico = 0.20;
                this.velocidad = 5;
                break;
            case "arquero":
                this.vida = 80;
                this.ataque = 120;
                this.defensa = 40;
                this.critico = 0.40;
                this.velocidad = 10;
                break;
            case "asesino":
                this.vida = 70;
                this.ataque = 150;
                this.defensa = 30;
                this.critico = 0.50;
                this.velocidad = 15;
                break;
            default:
                this.vida = 100;
                this.ataque = 100;
                this.defensa = 50;
                this.critico = 0.10;
                this.velocidad = 5;
                break;
        }
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
    public void atacar() {
        System.out.println(this.nombre + " el " + this.claseElegida + " ataca haciendo " + this.ataque + " puntos de daño.");
    }

    // Métodos comunes para todos los personajes

    public void recibirDaño(int cantidad) {

        // La defensa reduce el daño recibido

        int danioReal = Math.max(0, cantidad - this.defensa);
        this.vida = this.vida - danioReal;

        System.out.println(nombre + " recibe " + danioReal + " puntos de daño.");

        if (this.vida <= 0) {
            this.vida = 0;
            System.out.println(nombre + " ha sido derrotado...");
        }
    }

    public void usarObjeto() {
        System.out.println(nombre + " usó un objeto misterioso.");
    }

    // === GETTERS y SETTERS ===

    public String getNombre() {
        return nombre;

    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVida() {
        return vida;
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    public void setVida(int vida) {
        this.vida = vida;
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

    public double getCritico() {
        return critico;
    }

    public void setCritico(double critico) {
        this.critico = critico;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

}
