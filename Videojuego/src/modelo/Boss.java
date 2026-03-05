package modelo;

public class Boss extends Enemigo {

    public Boss(int vida, int ataque, double critico) {
        super(vida, ataque, critico);
    }

    @Override
    public void atacar() {
        System.out.println("¡El Boss hace un ataque devastador en área de " + this.ataque + " puntos!");
    }

    @Override
    public void recibirDaño(int cantidad) {
        this.vida -= cantidad;
        if (this.vida < 0)
            this.vida = 0;
        System.out.println("¡El Boss ha recibido " + cantidad + " puntos de daño! Le queda " + this.vida + " de vida.");
    }

}
