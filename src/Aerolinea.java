import java.util.NoSuchElementException;

public class Aerolinea {
    private final String nombre;
    private Estructuras.Nodo<Vuelo> vuelos;
    private final Estructuras.Pila<Reclamo> reclamos = new Estructuras.Pila<>();
    private final Estructuras.Cola<Pasajero> embarque = new Estructuras.Cola<>();

    public Aerolinea(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        this.nombre = nombre.trim();
    }

    public void agregarVuelo(Vuelo vuelo) {
        if (vuelo == null || buscarVuelo(vuelo.numero) != null)
            throw new IllegalArgumentException("Vuelo nulo o número repetido");
        Estructuras.Nodo<Vuelo> nuevo = new Estructuras.Nodo<>(vuelo);
        if (vuelos == null) vuelos = nuevo;
        else {
            Estructuras.Nodo<Vuelo> actual = vuelos;
            while (actual.siguiente != null) actual = actual.siguiente;
            actual.siguiente = nuevo;
        }
    }

    public Vuelo buscarVuelo(String numero) {
        for (Estructuras.Nodo<Vuelo> n = vuelos; n != null; n = n.siguiente)
            if (n.dato.numero.equals(numero)) return n.dato;
        return null;
    }

    public boolean eliminarVuelo(String numero) {
        if (vuelos == null) return false;
        if (vuelos.dato.numero.equals(numero)) {
            vuelos = vuelos.siguiente;
            return true;
        }
        Estructuras.Nodo<Vuelo> anterior = vuelos;
        while (anterior.siguiente != null) {
            if (anterior.siguiente.dato.numero.equals(numero)) {
                anterior.siguiente = anterior.siguiente.siguiente;
                return true;
            }
            anterior = anterior.siguiente;
        }
        return false;
    }

    public void registrarPasajero(String numeroVuelo, Pasajero pasajero) {
        Vuelo v = buscarVuelo(numeroVuelo);
        if (v == null) throw new NoSuchElementException("No existe el vuelo " + numeroVuelo);
        v.agregarPasajero(pasajero);
    }

    public void registrarReclamo(Reclamo reclamo) {
        if (reclamo == null) throw new IllegalArgumentException("Reclamo nulo");
        reclamos.apilar(reclamo);
    }
    public Reclamo consultarUltimoReclamo() { return reclamos.cima(); }
    public Reclamo deshacerUltimoReclamo() { return reclamos.desapilar(); }

    public void encolarParaEmbarque(String numeroVuelo, String pasaporte) {
        Vuelo v = buscarVuelo(numeroVuelo);
        if (v == null) throw new NoSuchElementException("No existe el vuelo " + numeroVuelo);
        Pasajero p = v.buscarPasajero(pasaporte);
        if (p == null) throw new NoSuchElementException("Pasajero no registrado");
        embarque.encolar(p);
    }
    public Pasajero verSiguienteEnEmbarque() { return embarque.frente(); }
    public Pasajero embarcarSiguiente() { return embarque.desencolar(); }

    /** Devuelve en una cola los vuelos de la fecha con ocupación menor al 55 %. */
    public Estructuras.Cola<String> vuelosConPocoPotencial(String fecha) {
        validarFecha(fecha);
        Estructuras.Cola<String> resultado = new Estructuras.Cola<>();
        recolectar(vuelos, fecha.trim(), resultado);
        return resultado;
    }

    private void recolectar(Estructuras.Nodo<Vuelo> actual, String fecha,
                            Estructuras.Cola<String> resultado) {
        if (actual == null) return; // caso base
        if (actual.dato.fecha.equals(fecha) && actual.dato.tienePocoPotencial())
            resultado.encolar(actual.dato.numero);
        recolectar(actual.siguiente, fecha, resultado); // caso recursivo
    }

    public int contarVuelosConPocoPotencial(String fecha) {
        validarFecha(fecha);
        return contar(vuelos, fecha.trim());
    }
    private int contar(Estructuras.Nodo<Vuelo> actual, String fecha) {
        if (actual == null) return 0;
        int suma = actual.dato.fecha.equals(fecha) && actual.dato.tienePocoPotencial() ? 1 : 0;
        return suma + contar(actual.siguiente, fecha);
    }
    private void validarFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty())
            throw new IllegalArgumentException("La fecha no puede estar vacía");
    }

    public String describir() {
        StringBuilder s = new StringBuilder("Aerolínea ").append(nombre).append('\n');
        for (Estructuras.Nodo<Vuelo> v = vuelos; v != null; v = v.siguiente) {
            s.append("  ").append(v.dato).append('\n');
            for (Estructuras.Nodo<Pasajero> p = v.dato.pasajeros; p != null; p = p.siguiente)
                s.append("      - ").append(p.dato).append('\n');
        }
        return s.toString();
    }
}

/* Clases del dominio: se mantienen juntas para reducir archivos y navegación. */
class Vuelo {
    final String numero, fecha, destino;
    final int capacidad;
    Estructuras.Nodo<Pasajero> pasajeros;

    Vuelo(String numero, String fecha, String destino, int capacidad) {
        if (vacio(numero) || vacio(fecha) || vacio(destino) || capacidad <= 0)
            throw new IllegalArgumentException("Datos del vuelo no válidos");
        this.numero = numero.trim(); this.fecha = fecha.trim();
        this.destino = destino.trim(); this.capacidad = capacidad;
    }
    void agregarPasajero(Pasajero p) {
        if (p == null) throw new IllegalArgumentException("Pasajero nulo");
        if (buscarPasajero(p.pasaporte) != null)
            throw new IllegalArgumentException("Pasaporte repetido: " + p.pasaporte);
        if (cantidadPasajeros() >= capacidad) throw new IllegalStateException("Vuelo lleno");
        Estructuras.Nodo<Pasajero> n = new Estructuras.Nodo<>(p);
        if (pasajeros == null) pasajeros = n;
        else {
            Estructuras.Nodo<Pasajero> a = pasajeros;
            while (a.siguiente != null) a = a.siguiente;
            a.siguiente = n;
        }
    }
    Pasajero buscarPasajero(String pasaporte) {
        for (Estructuras.Nodo<Pasajero> n = pasajeros; n != null; n = n.siguiente)
            if (n.dato.pasaporte.equals(pasaporte)) return n.dato;
        return null;
    }
    boolean eliminarPasajero(String pasaporte) {
        if (pasajeros == null) return false;
        if (pasajeros.dato.pasaporte.equals(pasaporte)) {
            pasajeros = pasajeros.siguiente;
            return true;
        }
        Estructuras.Nodo<Pasajero> anterior = pasajeros;
        while (anterior.siguiente != null) {
            if (anterior.siguiente.dato.pasaporte.equals(pasaporte)) {
                anterior.siguiente = anterior.siguiente.siguiente;
                return true;
            }
            anterior = anterior.siguiente;
        }
        return false;
    }
    int cantidadPasajeros() { return contarPasajeros(pasajeros); }
    private int contarPasajeros(Estructuras.Nodo<Pasajero> n) {
        return n == null ? 0 : 1 + contarPasajeros(n.siguiente);
    }
    boolean tienePocoPotencial() { return cantidadPasajeros() * 100 < capacidad * 55; }
    private static boolean vacio(String s) { return s == null || s.trim().isEmpty(); }
    @Override public String toString() {
        return "Vuelo " + numero + " | " + fecha + " | " + destino
                + " | " + cantidadPasajeros() + "/" + capacidad + " pasajeros";
    }
}

class Pasajero {
    final String pasaporte, nombre;
    Pasajero(String pasaporte, String nombre) {
        if (pasaporte == null || pasaporte.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Pasaporte y nombre son obligatorios");
        this.pasaporte = pasaporte.trim(); this.nombre = nombre.trim();
    }
    @Override public String toString() { return nombre + " (" + pasaporte + ")"; }
}

class Reclamo {
    final String pasaporte, numeroVuelo, descripcion;
    Reclamo(String pasaporte, String numeroVuelo, String descripcion) {
        if (pasaporte == null || pasaporte.trim().isEmpty()
                || numeroVuelo == null || numeroVuelo.trim().isEmpty()
                || descripcion == null || descripcion.trim().isEmpty())
            throw new IllegalArgumentException("Datos del reclamo incompletos");
        this.pasaporte = pasaporte.trim(); this.numeroVuelo = numeroVuelo.trim();
        this.descripcion = descripcion.trim();
    }
    @Override public String toString() {
        return "Reclamo del vuelo " + numeroVuelo + ": " + descripcion;
    }
}
