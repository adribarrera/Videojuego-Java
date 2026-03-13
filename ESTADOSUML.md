# Diagrama de Estados - Videojuego ELDAP

El siguiente diagrama detalla los estados globales de la aplicación y las transiciones entre ellos, gestionadas por la `VentanaPrincipal` y su `CardLayout`.

```mermaid
stateDiagram-v2
    [*] --> Portada: Iniciar Aplicación

    state Portada {
        [*] --> MenuPrincipal
        MenuPrincipal: música "audioMenu.wav"
    }

    Portada --> Seleccion: Click "Comenzar"
    
    state Seleccion {
        [*] --> ElegirClase
        ElegirClase: Guerrero, Mago o Asesino
    }

    Seleccion --> Jugando: Confirmar y Jugar
    
    state Jugando {
        [*] --> Mapa
        Mapa: Exploración 2D con WASD
        
        state Pausa {
            MenuEscape: Ajustes de volumen / Salir
        }

        Mapa --> Pausa: Tecla ESC
        Pausa --> Mapa: Reanudar
        
        state Tienda {
            Delik_IA: Compra de hasta 2 ítems
        }

        Mapa --> Tienda: Tecla 'E' (Proximidad)
        Tienda --> Mapa: Volver (Cerrar)
    }

    Jugando --> Combate: Tecla 'E' (Proximidad Boss)
    
    state Combate {
        [*] --> TurnoJugador
        TurnoJugador --> TurnoEnemigo: Atacar / Usar Item
        TurnoEnemigo --> TurnoJugador: Contraataque autom.
        
        state VictoriaCombate {
            MejoraStats: Recompensa + Oro
        }
        
        TurnoJugador --> VictoriaCombate: Boss HP = 0
    }

    Combate --> Jugando: Derrota Boss (Volver)
    Combate --> GameOver: Jugador HP = 0
    VictoriaCombate --> VictoryScreen: Sergio Derrotado (Jefe Final)

    state GameOver {
        PanelDerrota: Timer 2.5s
    }

    state VictoryScreen {
        PanelVictoria: Créditos
    }

    GameOver --> Portada: Click "Volver al Menú"
    VictoryScreen --> Portada: Click "Finalizar"
```

## Descripción de Estados

1.  **Portada**: El estado inicial del juego. Carga la música de ambiente del menú.
2.  **Selección**: Estado de configuración donde se instancia el objeto `Personaje` con sus slots de inventario vacíos.
3.  **Mapa (Exploración)**: Estado principal de juego. Permite la libre navegación y la entrada a sub-estados como la **Tienda** o la **Pausa**.
4.  **Combate**: Estado crítico donde la música cambia y se bloquea la exploración. El flujo es cíclico entre los turnos de las entidades hasta que una muere.
5.  **Game Over / Victory**: Estados terminales de una sesión. Al salir de ellos, se limpia la instancia del personaje y el mapa para un reinicio fresco.
