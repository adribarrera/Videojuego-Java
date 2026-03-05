package modelo;

public class Guerrero extends Personaje {

    // 1. Constructor donde solo pides el nombre, y los stats ya están fijados
    public Guerrero(String nombre) {

        // Llamamos al constructor de Personaje con los valores fijos para un Guerrero
        // (nombre, vida, ataque, defensa, critico, velocidad)
        super(nombre, 100, 100, 75, 0.20, 5);
    }

    @Override
    public void atacar() {
        System.out.println(this.nombre + " da un espadazo haciendo " + this.ataque + " puntos de daño.");
    }

}
