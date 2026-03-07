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
Al comenzar la partida, el jugador podrá elegir entre distintos personajes.
Cada personaje tiene atributos propios en base a la clase elegida:
• Guerrero: Vida Max: 250 | Ataque: 250 | Defensa: 150 | Crítico: 10% | Velocidad en el mapa: 5
• Mago: Vida Max: 200 | Ataque: 300 | Defensa: 100 | Crítico: 30% | Velocidad en el mapa: 10
• Asesino: Vida Max: 250 | Ataque: 350 | Defensa: 75 | Crítico: 50% | Velocidad en el mapa: 15
________________________________________

2.2 Exploración del Mapa
•	Mapa cerrado en 2D.
•	Movimiento libre con teclado (WASD).
•	Zonas con mini bosses fijos.
•	Tiendas con pasivos y activos


El mapa será único y compacto para mantener el alcance del proyecto realista.
________________________________________
2.3 Sistema de Combate
Combate por turnos en pantalla separada.
Opciones del jugador:
•	Atacar
•	Defender
•	Usar objeto activo
•	Huir (opcional)
Sistema básico:
1.	Turno del jugador.
2.	Turno del enemigo.
3.	Se repite hasta que uno llegue a 0 HP.
Cálculo de daño neto:
Daño Final = Ataque – Defensa del enemigo (el daño recibido no baja de 0).
Posibilidades en el ataque:
• Golpe Crítico: Basado en la `probCritico` del combatiente, que duplica el daño final por turno.
• Daño directo: Mediante el uso de objetos como *El Chatto* o *El Guantón*.
________________________________________
2.4 Estadísticas del Personaje
El jugador administrará:
• Vida Máxima y Actual
• Ataque base y Defensa base
• Probabilidad de Golpe Crítico
• Velocidad de movimiento en el mapa (WASD)
• Dinero (para gastar en tiendas del mapa)
• Inventario limitado (Máx. 2 objetos permitidos)
2.5 Sistema de Mejoras
Al derrotar enemigos, el jugador obtiene:
•	Oro
•	(Opcional) Experiencia

El oro podrá usarse en tiendas fijas del mapa (ej. Tienda Delik.IA, activable con la letra 'E') para comprar mejoras permanentes y objetos activos:
Ejemplos:
•	+5 Ataque
•	+10 Vida
•	+2% Crítico
•	+5 Defensa
Las mejoras serán acumulativas.
________________________________________
2.6 Sistema de Bosses del Mapa (Enemigos)
Habrá varios Bosses de historia distribuidos por áreas o "salas" del mapa. El combate se activa al interactuar con ellos ('E').
Características actuales implementadas en los Bosses:
• Soraya: Vida 2000 | Ataque 25 | Crítico 50% (Sala izquierda)
• Jessica: Vida 1000 | Ataque 10 | Crítico 5% (Sala derecha)
• Juan Carlos: Vida 3000 | Ataque 30 | Crítico 15% (Sala arriba-izquierda)
• Sergio: Vida 5000 | Ataque 50 | Crítico 40% (Sala arriba-derecha)

Opcional (Futuro desarrollo):
• Recompensas especiales para potenciar atributos base.
________________________________________
2.7 Boss Final
El objetivo principal del juego.
Características:
•	Estadísticas superiores
•	Habilidad especial
•	(Opcional) Segunda fase
Al derrotarlo:
•	Opción de reiniciar partida
•	(Opcional) Modo Nueva Partida+ con mayor dificultad
________________________________________
3. 🎒 Sistema de Objetos limitados (Inventario)
El jugador puede llevar un máximo de 2 opciones. Existen diferentes ítems, basados en los elementos del panel de la tienda:

Objetos Implementados en la Tienda Delik.IA (En Desarrollo):
• Vaper: Elemento curativo
• Mantequilla (M.L.P.E.): Elemento que incrementa la armadura/defensa en 10.
• Gabardina: Mejora los stats o recuperación 
• Gafas de Soraya: Modifican el golpe crítico temporalmente al 100% y luego se resetea.
• El Chatto: Objeto ofensivo. Causa daño directo y letal, omitiendo armadura del enemigo.
• Guantón (de Juan Carlos): Daño directo que escala por estadística base.
Todos los objetos se usan mediante la acción de "Buscando en el inventario" durante el Panel de Combate contra cualquier Boss.
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
•	Selección de personaje (JDialog)
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

