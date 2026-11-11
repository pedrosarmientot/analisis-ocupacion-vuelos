
public final class Estructuras {
    private Estructuras() {}

    static class Nodo<E> {
        E dato;
        Nodo<E> siguiente;
        Nodo(E dato) { this.dato = dato; }
    }

    /** Cola FIFO: el primero que entra es el primero que sale. */
    static class Cola<E> {
        private Nodo<E> primero, ultimo;

        boolean estaVacia() { return primero == null; }

        void encolar(E dato) {
            Nodo<E> nuevo = new Nodo<>(dato);
            if (estaVacia()) primero = ultimo = nuevo;
            else { ultimo.siguiente = nuevo; ultimo = nuevo; }
        }

        E frente() { return estaVacia() ? null : primero.dato; }

        E desencolar() {
            if (estaVacia()) return null;
            E dato = primero.dato;
            primero = primero.siguiente;
            if (primero == null) ultimo = null;
            return dato;
        }
    }

    /** Pila LIFO: el último que entra es el primero que sale. */
    static class Pila<E> {
        private Nodo<E> tope;

        boolean estaVacia() { return tope == null; }

        void apilar(E dato) {
            Nodo<E> nuevo = new Nodo<>(dato);
            nuevo.siguiente = tope;
            tope = nuevo;
        }

        E cima() { return estaVacia() ? null : tope.dato; }

        E desapilar() {
            if (estaVacia()) return null;
            E dato = tope.dato;
            tope = tope.siguiente;
            return dato;
        }
    }
}
