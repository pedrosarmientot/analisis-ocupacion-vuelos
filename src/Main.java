/** Punto de entrada: demostración breve de las estructuras del proyecto. */
public class Main {
    public static void main(String[] args) {
        Aerolinea a = new Aerolinea("Cubana Express");
        a.agregarVuelo(new Vuelo("CU100", "2026-12-15", "Madrid", 10));
        a.agregarVuelo(new Vuelo("CU200", "2026-12-15", "Miami", 20));

        a.registrarPasajero("CU100", new Pasajero("P01", "Ana Pérez"));
        a.registrarPasajero("CU100", new Pasajero("P02", "Luis Gómez"));
        for (int i = 1; i <= 11; i++)
            a.registrarPasajero("CU200", new Pasajero("Q" + i, "Pasajero " + i));

        System.out.println("=== MULTILISTA: VUELOS Y PASAJEROS ===");
        System.out.println(a.describir());

        System.out.println("=== PILA: RECLAMOS (LIFO) ===");
        a.registrarReclamo(new Reclamo("P01", "CU100", "Maleta extraviada"));
        a.registrarReclamo(new Reclamo("P02", "CU100", "Maleta dañada"));
        System.out.println("Último reclamo: " + a.consultarUltimoReclamo());
        System.out.println("Deshacer: " + a.deshacerUltimoReclamo());

        System.out.println("\n=== COLA: EMBARQUE (FIFO) ===");
        a.encolarParaEmbarque("CU100", "P01");
        a.encolarParaEmbarque("CU100", "P02");
        System.out.println("Primero en embarcar: " + a.embarcarSiguiente());
        System.out.println("Segundo en embarcar: " + a.embarcarSiguiente());

        System.out.println("\n=== RECURSIVIDAD: POCO POTENCIAL ===");
        Estructuras.Cola<String> ids = a.vuelosConPocoPotencial("2026-12-15");
        System.out.print("Vuelos por debajo del 55 %: ");
        if (ids.estaVacia()) System.out.println("ninguno");
        while (!ids.estaVacia()) System.out.print(ids.desencolar() + " ");
        System.out.println("\nTotal: " + a.contarVuelosConPocoPotencial("2026-12-15"));
    }
}
