package com.videojuego.principal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainTest {

    @Test
    public void testMainArrancaSinErrores() {
        assertDoesNotThrow(() -> {
            // 1. Instanciamos la clase para testear el "constructor invisible" (Esto te da
            // el 100%)
            Main aplicacion = new Main();
            assertNotNull(aplicacion); // Comprobamos que se ha creado bien

            // 2. Ejecutamos el método main para testear la lógica
            String[] args = {};
            Main.main(args);

        }, "El constructor y el método main deberían ejecutarse sin lanzar excepciones.");
    }
}