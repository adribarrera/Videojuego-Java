📜 DOCUMENTO DE DISEÑO DE VIDEOJUEGO: ELDAP
________________________________________
1. 📖 Concepto General
Género: RPG por turnos
Plataforma: PC
Tecnología: Java (Swing)
El juego es un RPG por turnos inspirado en combates clásicos. El jugador explora un mapa donde debe derrotar a 4 jefes finales para demostrar su valía. A medida que derrota enemigos, mejora sus estadísticas permanentemente y adquiere objetos en la máquina expendedora Delik.IA.

________________________________________
2. 🎮 Mecánicas Principales
2.1 Elección de Personaje
Al comenzar, el jugador elige entre tres clases con estadísticas base diferenciadas:
• Guerrero: Vida: 600 | Ataque: 75 | Defensa: 40% | Crítico: 4% | Velocidad: 5
• Mago: Vida: 400 | Ataque: 120 | Defensa: 30% | Crítico: 8% | Velocidad: 6
• Asesino: Vida: 500 | Ataque: 90 | Defensa: 35% | Crítico: 25% | Velocidad: 7

2.2 Exploración y Combate
• Movimiento: WASD en un entorno 2D con colisiones reales.
• Interacción: Tecla 'E' para hablar con la tienda o iniciar combates.
• Combate: Sistema de turnos con variación de daño (85%-115%) y multiplicador crítico (x3).

________________________________________
3. 🎒 Sistema de Objetos (Tienda Delik.IA)
El jugador puede llevar un máximo de 2 objetos.
• Vaper de Guanábana: Recupera el 50% de la salud máxima.
• Mantequilla L.P.E.: +40% de probabilidad de crítico permanente.
• Gabardina de Sergio: +30 puntos de defensa permanentes.
• El Chatto: Roba 600 HP al enemigo (daño directo) y te los cura.
• Examen de Soraya: Garantiza que el próximo ataque sea crítico (100%).
• Virus de Linux: Sacrifica 20% de HP máximo por +50 de ataque permanente.

________________________________________
4. 🧠 Enemigos (Bosses)
• Jessica: Vida 600 | Ataque 50 | Crítico 2.5% (Dificultad: Baja)
• Juan Carlos: Vida 800 | Ataque 100 | Crítico 2.5% (Dificultad: Media)
• Soraya: Vida 1500 | Ataque 120 | Crítico 7.5% (Dificultad: Alta)
• Sergio: Vida 3000 | Ataque 200 | Crítico 5.0% (Jefe Final)

________________________________________
5. 🖥 Interfaz
• Portada: Menú de inicio con música.
• Selección: Previsualización de stats reales antes de jugar.
• Mapa: HUD persistente con vida, ataque, defensa y dinero.
• Combate: Log de eventos con 4 líneas de historial y auto-ajuste de texto.
