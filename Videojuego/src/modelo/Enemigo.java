package modelo;

public abstract class Enemigo implements Entidad {
    protected int vida;
    protected int ataque;
    protected double critico;

    public Enemigo(int vida, int ataque, double critico) {
        this.vida = vida;
        this.ataque = ataque;
        this.critico = critico;
    }

    @Override
    public abstract void atacar();

    @Override
    public void recibirDaño(int cantidad) {
        this.vida -= cantidad;
        if (this.vida < 0)
            this.vida = 0;
        System.out.println("El enemigo recibe " + cantidad + " de daño. Vida restante: " + this.vida);
    }

    @Override
    public boolean estaVivo() {
        return this.vida > 0;
    }

}
