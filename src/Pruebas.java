import java.util.NoSuchElementException;

/** Pruebas esenciales de funcionamiento */
public class Pruebas {
    private static int correctas = 0, total = 0;
    private static void comprobar(String nombre, boolean condicion) {
        total++;
        if (condicion) { correctas++; System.out.println("OK: " + nombre); }
        else System.out.println("FALLO: " + nombre);
    }

    /** Comprueba que la acción lance la excepción esperada. */
    private static void lanza(String nombre, Runnable accion, Class<? extends RuntimeException> tipo) {
        total++;
        try {
            accion.run();
            System.out.println("FALLO: " + nombre + " (no lanzó excepción)");
        } catch (RuntimeException e) {
            if (tipo.isInstance(e)) { correctas++; System.out.println("OK: " + nombre); }
            else System.out.println("FALLO: " + nombre + " (lanzó " + e.getClass().getSimpleName() + ")");
        }
    }

    public static void main(String[] args) {
        Estructuras.Pila<String> pila = new Estructuras.Pila<>();
        pila.apilar("A"); pila.apilar("B");
        comprobar("Pila respeta LIFO", "B".equals(pila.desapilar()) && "A".equals(pila.desapilar()));
        comprobar("Pila vacía devuelve null", pila.desapilar() == null);

        Estructuras.Cola<String> cola = new Estructuras.Cola<>();
        cola.encolar("A"); cola.encolar("B");
        comprobar("Cola respeta FIFO", "A".equals(cola.desencolar()) && "B".equals(cola.desencolar()));
        comprobar("Cola vacía devuelve null", cola.desencolar() == null);

        Aerolinea a = new Aerolinea("Prueba");
        a.agregarVuelo(new Vuelo("V1", "2026-12-15", "Madrid", 10));
        a.agregarVuelo(new Vuelo("V2", "2026-12-15", "Roma", 20));
        a.registrarPasajero("V1", new Pasajero("P1", "Ana"));
        a.registrarPasajero("V1", new Pasajero("P2", "Luis"));
        comprobar("Búsqueda de vuelo y pasajero", a.buscarVuelo("V1") != null
                && a.buscarVuelo("V1").buscarPasajero("P2") != null);
        comprobar("Cálculo recursivo de pasajeros", a.buscarVuelo("V1").cantidadPasajeros() == 2);

        a.registrarReclamo(new Reclamo("P1", "V1", "Equipaje"));
        comprobar("Pila integrada: consulta y deshacer",
                a.consultarUltimoReclamo() != null && a.deshacerUltimoReclamo() != null);
        a.encolarParaEmbarque("V1", "P1");
        a.encolarParaEmbarque("V1", "P2");
        comprobar("Cola integrada: orden de embarque",
                "P1".equals(a.embarcarSiguiente().pasaporte)
                && "P2".equals(a.embarcarSiguiente().pasaporte));

        // 2/10 = 20 %: poco potencial. 11/20 = 55 %: no debe incluirse.
        for (int i = 1; i <= 11; i++)
            a.registrarPasajero("V2", new Pasajero("Q" + i, "Pasajero " + i));
        Estructuras.Cola<String> poco = a.vuelosConPocoPotencial("2026-12-15");
        comprobar("Recursividad aplica el límite estricto del 55 %",
                "V1".equals(poco.desencolar()) && poco.estaVacia()
                && a.contarVuelosConPocoPotencial("2026-12-15") == 1);

        comprobar("Fecha sin vuelos produce resultado vacío",
                a.vuelosConPocoPotencial("2030-01-01").estaVacia());
        // ---- Casos negativos y de borde ----
        comprobar("Vuelo o pasajero inexistente devuelve null",
                a.buscarVuelo("NOPE") == null && a.buscarVuelo("V1").buscarPasajero("ZZZ") == null);
        lanza("Registrar en un vuelo inexistente lanza excepción",
                () -> a.registrarPasajero("NOPE", new Pasajero("Z1", "Nadie")), NoSuchElementException.class);
        lanza("Pasaporte repetido lanza excepción",
                () -> a.registrarPasajero("V1", new Pasajero("P1", "Otro")), IllegalArgumentException.class);
        lanza("Capacidad 0 lanza excepción",
                () -> new Vuelo("V9", "2026-12-15", "Roma", 0), IllegalArgumentException.class);
        Aerolinea chica = new Aerolinea("Chica");
        chica.agregarVuelo(new Vuelo("C1", "2026-12-15", "Lima", 1));
        chica.registrarPasajero("C1", new Pasajero("X1", "Uno"));
        lanza("Vuelo lleno no acepta más pasajeros",
                () -> chica.registrarPasajero("C1", new Pasajero("X2", "Dos")), IllegalStateException.class);
        lanza("Fecha vacía lanza excepción",
                () -> a.vuelosConPocoPotencial("  "), IllegalArgumentException.class);

        System.out.println("\nResultado: " + correctas + " de " + total + " pruebas correctas.");
        if (correctas != total) throw new AssertionError("Hay pruebas fallidas");
    }
}
