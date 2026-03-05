package modelo;

public class Enemigo implements Entidad {
    protected String tipoEnemigo;
    protected int vida;
    protected int ataque;
    protected double critico;

    public Enemigo(String tipoEnemigo) {
        this.tipoEnemigo = tipoEnemigo;

        switch (tipoEnemigo.toLowerCase()) {
            case "boss":
                this.vida = 500;
                this.ataque = 250;
                this.critico = 0.50;
                break;
            case "slime":
                this.vida = 50;
                this.ataque = 10;
                this.critico = 0.05;
                break;
            case "esqueleto":
                this.vida = 80;
                this.ataque = 25;
                this.critico = 0.15;
                break;
            default:
                this.vida = 100;
                this.ataque = 20;
                this.critico = 0.10;
                break;
        }
    }

    @Override
    public void atacar() {
        if (this.tipoEnemigo.equalsIgnoreCase("boss")) {
            System.out.println("¡El Boss hace un ataque devastador en área de " + this.ataque + " puntos!");
        } else {
            System.out.println("El " + this.tipoEnemigo + " ataca haciendo " + this.ataque + " puntos de daño.");
        }
    }

    @Override
    public void recibirDaño(int cantidad) {
        this.vida -= cantidad;
        if (this.vida < 0)
            this.vida = 0;
        System.out.println(
                "El " + this.tipoEnemigo + " ha recibido " + cantidad + " puntos de daño. Vida restante: " + this.vida);
    }

    @Override
    public boolean estaVivo() {
        return this.vida > 0;
    }

}
