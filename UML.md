# Diagrama de Clases UML - Videojuego ELDAP

```mermaid
classDiagram
    %% Interfaces
    class Entidad {
        <<interface>>
        +atacar(Entidad objetivo) boolean
        +recibirDanio(int cantidad) void
        +estaVivo() boolean
    }

    %% Clases del Modelo
    class Personaje {
        #String nombre
        #String claseElegida
        #int vidaMaxima
        #int vidaActual
        #int ataque
        #int defensa
        #double probCritico
        #double probCriticoActual
        #int dinero
        #int posX
        #int posY
        #int velocidad
        #List~Item~ inventario
        #int MAX_OBJETOS
        +moverDireccion(String direccion)
        +atacar(Entidad enemigo) boolean
        +recibirDanio(int cantidad) void
        +mejorarAtributosAlDerrotarBoss() void
        +estaVivo() boolean
        +recibirDanioDirecto(int cantidad) void
        +curar(int cantidad) void
        +aumentarDefensa(int cantidad) void
        +recogerItem(Item nuevoItem) boolean
        +usarItem(int indice, Enemigo enemigoEnCombate) String
    }

    class Enemigo {
        #String nombre
        #int vidaMaxima
        #int vidaActual
        #int ataque
        #double probCritico
        +atacar(Entidad objetivo) boolean
        +recibirDanio(int cantidad) void
        +estaVivo() boolean
        +recibirDanioDirecto(int cantidad) void
    }

    class Item {
        -String nombre
        -String descripcion
        -int modificador
        -TipoEfecto efecto
        -int precio
        +aplicarA(Personaje jugador, Enemigo enemigo) String
    }

    class Tienda {
        -String nombre
        -List~Item~ inventarioTienda
        +obtenerInfoItems(int indice) String
        +procesarCompra(int indice, Personaje comprador) String
    }

    class TipoEfecto {
        <<enum>>
        CURAR_PORCENTAJE
        AUMENTAR_CRITICO
        AUMENTAR_DEFENSA
        ROBAR_VIDA
        CRITICO_SEGURO
        PACTO_VIDA_ATAQUE
    }

    %% Relaciones Modelo
    Entidad <|.. Personaje
    Entidad <|.. Enemigo
    Item --> TipoEfecto : usa
    Tienda "1" *-- "many" Item : vende
    Personaje "1" o-- "0..2" Item : lleva

    %% Vista Principal y Controladores
    class VentanaPrincipal {
        -CardLayout gestorPantallas
        -cambiarPanelConTransicion(String nombre, Runnable accion)
        +iniciarJuegoConPersonaje(Personaje p)
        +iniciarCombate(String nombreBoss)
        +abrirTienda(String nombre, Personaje jugador)
        +volverAMapaYEliminarBoss(String nombreBoss)
        +cambiarAMenu()
        +irADerrota()
        +irAVictoria()
    }

    class ControladorAudio {
        <<singleton>>
        -Clip musicaFondo
        -int volumenGlobal
        +getInstance() ControladorAudio
        +reproducirMusicaAmbiental(String ruta)
        +detenerMusica()
        +setVolumenGlobal(int vol)
        +getVolumenGlobal() int
    }

    class ControladorMovimiento {
        -Personaje personaje
        -Timer timerMovimiento
        -List~String~ teclasPulsadas
        +configurarControles()
        +detener()
    }
    
    class Colisiones {
        -ArrayList~Rectangle~ muros
        +verificarMovimiento(x, y, w, h) boolean
        +eliminarMuroBoss(String nombreBoss)
    }

    %% Relaciones de Dependencia
    VentanaPrincipal *-- PanelMapa : contiene
    PanelMapa o-- Personaje : referencia
    PanelMapa --> ControladorMovimiento : crea
    PanelMapa --> Colisiones : usa
    PanelCombate --> Personaje : referencia
    PanelCombate --> Enemigo : crea
    PanelTienda --> Tienda : referencia
```