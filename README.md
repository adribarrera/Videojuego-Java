# 🗡️ ELDAP: The Java RPG Challenge

**ELDAP** es un videojuego de rol (RPG) por turnos desarrollado íntegramente en **Java nativo** utilizando la biblioteca **Swing** para la interfaz gráfica. El juego combina exploración en un mapa 2D, gestión de inventario y combates estratégicos contra jefes únicos.

---

## 🎮 Cómo Jugar

### Controles
- **WASD**: Movimiento del personaje por el mapa.
- **Tecla E**: Interactuar con objetos (Máquina Delik.IA) o iniciar combates con Jefes.
- **Tecla ESC**: Menú de pausa (Ajuste de volumen y salida).
- **Click izquierdo**: Pulsar botones.

### Clases Disponibles
- **Guerrero**: El tanque equilibrado. Mucha vida y defensa sólida.
- **Mago**: El cañón de cristal. Daño masivo pero muy vulnerable.
- **Asesino**: El maestro del crítico. Poca vida pero con gran probabilidad de daño x3.

---

## 🛠️ Tecnologías Utilizadas
- **Lenguaje**: Java 17+
- **Interfaz**: Java Swing / AWT.
- **Gestión de Audio**: Clip API (javax.sound.sampled).
- **Construcción**: Maven.
- **Documentación**: Mermaid para diagramas UML.

---

## 🚀 Instalación y Ejecución

Asegúrate de tener instalado **Maven** y un **JDK 17** o superior.

1. **Compilar el proyecto**:
   ```bash
   mvn compile
   ```

2. **Ejecutar el juego**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.videojuego.principal.Main"
   ```

---

## 📄 Documentación Técnica Incluida
- [GDD.md](./GDD.md): Documento de Diseño con estadísticas exactas.
- [UML.md](./UML.md): Diagrama de clases y arquitectura.
- [CASOUSOUML.md](./CASOUSOUML.md): Casos de uso funcionales.
- [SECUENCIAUML.md](./SECUENCIAUML.md): Flujos de ejecución inicio/combate/tienda.

---
*Desarrollado para el Proyecto ELDAP - Java.*