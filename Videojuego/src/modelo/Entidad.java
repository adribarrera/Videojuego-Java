package modelo;

public interface Entidad {

    void atacar();

    void recibirDaño(int cantidad);

    boolean estaVivo();
}
