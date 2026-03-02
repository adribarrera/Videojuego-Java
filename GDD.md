📜 DOCUMENTO DE DISEÑO DE VIDEOJUEGO
ELDAP
________________________________________
1. 📖 Concepto General
Género: RPG por turnos
Plataforma: PC
Tecnología: Java (Swing / JavaFX)
Duración estimada de desarrollo: 21 días
El juego es un RPG por turnos inspirado en los combates clásicos estilo Pokémon. El jugador explorará un mapa cerrado donde encontrará enemigos aleatorios, mini bosses y un boss final. A medida que derrota enemigos, podrá mejorar sus estadísticas y adquirir objetos que le ayudarán a progresar.
El objetivo final es derrotar al Boss Final del mapa.
________________________________________
2. 🎮 Mecánicas Principales
2.1 Elección de Personaje
Al comenzar la partida, el jugador podrá elegir entre distintos personajes (inicialmente 1, ampliable a 3 si el desarrollo avanza bien).
Cada personaje tendrá:
•	Estadísticas base distintas
•	Posible diferencia visual (skin)
Ejemplo de clases:
•	Guerrero (más vida y defensa)
•	Mago (más daño)
•	Asesino (más crítico)
________________________________________

2.2 Exploración del Mapa
•	Mapa cerrado en 2D.
•	Movimiento libre con teclado (WASD o flechas).
•	Encuentros aleatorios con enemigos al caminar.
•	Posibles zonas con mini bosses fijos.
El mapa será único y compacto para mantener el alcance del proyecto realista.
________________________________________
2.3 Sistema de Combate
Combate por turnos en pantalla separada.
Opciones del jugador:
•	Atacar
•	Defender
•	Usar objeto
•	Huir (opcional)
Sistema básico:
1.	Turno del jugador.
2.	Turno del enemigo.
3.	Se repite hasta que uno llegue a 0 HP.
Cálculo de daño base:
Daño = Ataque – Defensa + variación aleatoria
Posibilidad de:
•	Golpe crítico
•	Habilidades especiales en bosses
________________________________________




2.4 Estadísticas del Personaje
El jugador tendrá:
•	Vida (HP)
•	Ataque
•	Defensa
•	Probabilidad de crítico
Opcional:
•	Experiencia y nivel
________________________________________
2.5 Sistema de Mejoras
Al derrotar enemigos, el jugador obtiene:
•	Oro
•	(Opcional) Experiencia
El oro podrá usarse en una tienda para comprar mejoras permanentes:
Ejemplos:
•	+5 Ataque
•	+10 Vida
•	+2% Crítico
•	+5 Defensa
Las mejoras serán acumulativas.
________________________________________







2.6 Mini Bosses
Habrá varios mini bosses distribuidos por el mapa.
Características:
•	Más vida que enemigos normales
•	Habilidad especial
•	Recompensa especial al derrotarlos
Recompensas posibles:
•	Aumento permanente de estadísticas
•	Objeto único
________________________________________
2.7 Boss Final
El objetivo principal del juego.
Características:
•	Estadísticas superiores
•	Habilidad especial
•	Posible segunda fase
Al derrotarlo:
•	Opción de reiniciar partida
•	(Opcional) Modo Nueva Partida+ con mayor dificultad
________________________________________








3. 🎒 Sistema de Objetos
Habrá dos tipos de objetos:
3.1 Objetos Activos
Se usan en combate.
Ejemplos:
•	Poción → Recupera 30 HP
•	Suero → Aumenta ataque temporalmente
3.2 Objetos Pasivos
Otorgan mejoras permanentes o efectos constantes.
Ejemplos creativos:
•	Gafas de Soraya → Mayor visión en el mapa
•	M.L.P.E (Mantequilla Lista Para Enfoscar) → Aumenta daño crítico
•	Amuleto extraño → Probabilidad de esquivar ataques
________________________________________
4. 🧠 Enemigos
4.1 Enemigos Normales
•	Estadísticas básicas
•	Sin habilidades especiales
•	Dan poca recompensa
4.2 Mini Bosses
•	Más fuertes
•	Habilidad única
•	Recompensa especial
4.3 Boss Final
•	Máxima dificultad
•	Mecánicas especiales
________________________________________

5. 🖥 Interfaz
Pantallas necesarias:
•	Menú principal
•	Selección de personaje
•	Mapa
•	Pantalla de combate
•	Tienda
•	Pantalla de victoria / derrota
Opcional:
•	Animaciones de ataque
•	Barra de vida animada
•	Efectos visuales
________________________________________
6. 📈 Sistema de Dificultad
El juego aumentará la dificultad progresivamente mediante:
•	Enemigos más fuertes
•	Mayor probabilidad de encuentros
•	Mini bosses más frecuentes
Opcional:
Modo Nueva Partida+:
•	Enemigos con multiplicador de estadísticas.
________________________________________






7. 🎯 Objetivo del Proyecto
Desarrollar un RPG funcional con:
•	Sistema completo de exploración
•	Combate por turnos
•	Sistema de mejoras
•	Mini bosses
•	Boss final
El enfoque principal será la jugabilidad funcional antes que el apartado visual.
________________________________________
8. 🗓 Planificación
Semana 1:
•	Movimiento
•	Encuentros
•	Combate básico
Semana 2:
•	Sistema de mejoras
•	Mini bosses
•	Boss final
Semana 3:
•	Pulido
•	Interfaz
•	Balanceo
•	Animaciones opcionales

