# Equipo 7 – Sistema de gestión de una aerolínea

**Universidad de Pinar del Río “Hermanos Saíz Montes de Oca”**  
Asignatura: **Estructuras de Datos** · Curso 2026-2027 · 2do examen parcial

**Integrantes:** Onel Alejandro Olivera Díaz · Pedro Luis Sarmiento Torres · María Fernanda Rodríguez Díaz · Amalia Aguiar Pereda

## 1. Problemática

Una aerolínea necesita organizar sus vuelos y pasajeros, registrar los reclamos de equipaje, controlar el orden de embarque y detectar los vuelos con poca ocupación.

Un vuelo tiene **poco potencial** cuando su ocupación es estrictamente menor al 55 % de su capacidad. Por ejemplo, 10 pasajeros de 20 representan un 50 % y sí clasifican; 11 de 20 representan exactamente un 55 % y no clasifican.

## 2. Qué hace el programa

- Registra vuelos y pasajeros y controla pasaportes repetidos y capacidad.
- Organiza los vuelos y sus pasajeros mediante una **multilista**.
- Guarda reclamos en una **pila LIFO**, que permite consultar o deshacer el último.
- Gestiona el embarque mediante una **cola FIFO**.
- Busca recursivamente los vuelos de una fecha cuya ocupación sea menor al 55 %.

## 3. Estructura simplificada del proyecto

```text
equipo7-Aerolinea/
├── src/
│   ├── Main.java          # Demostración del sistema
│   ├── Aerolinea.java     # Gestión y clases del dominio
│   ├── Estructuras.java   # Nodo, Pila y Cola enlazadas
│   └── Pruebas.java       # Pruebas esenciales
├── docs/
│   └── Presentacion_Equipo7.pptx
├── pom.xml
├── ejecutar.bat
├── ejecutar.sh
└── README.md
```

## 4. Estructuras de datos

| Estructura | Uso en el sistema | Idea fundamental |
|---|---|---|
| Multilista | Aerolínea → vuelos → pasajeros | Cada vuelo mantiene su propia lista de pasajeros |
| Pila | Historial de reclamos | LIFO: el último que entra es el primero que sale |
| Cola | Pasajeros en espera de embarque | FIFO: el primero que entra es el primero que sale |
| Recursividad | Buscar vuelos con poca ocupación | Caso base y llamada al siguiente nodo |

### Pila

La pila conserva una referencia llamada `tope`. Al apilar, el nuevo nodo apunta al antiguo tope y pasa a ser el nuevo tope. Al desapilar, se devuelve el dato del tope y la referencia avanza al nodo siguiente.

Las operaciones principales modifican una sola referencia, por lo que su coste es **O(1)**.

### Cola

La cola conserva dos referencias: `primero` y `ultimo`. Al encolar, se enlaza el nuevo nodo al final. Al desencolar, se retira el nodo del frente. Así se respeta el orden de llegada.

### Recursividad

El método `recolectar` recibe el nodo actual y la fecha que debe comprobar:

- **Caso base:** si el nodo actual es `null`, termina la recursión.
- **Caso recursivo:** revisa el vuelo actual y llama al método con el nodo siguiente.
- **Condición:** `pasajeros * 100 < capacidad * 55`.

La multiplicación por enteros evita depender de aproximaciones decimales. La comparación es estricta: el 55 % exacto no se considera poco potencial.

## 5. Pruebas

**16 comprobaciones**, agrupadas en una sola clase (`Pruebas.java`), con casos positivos, negativos y de borde:

1. La pila respeta LIFO.
2. La pila vacía devuelve `null`.
3. La cola respeta FIFO.
4. La cola vacía devuelve `null`.
5. Se encuentran vuelos y pasajeros.
6. El conteo de pasajeros es recursivo.
7. La pila funciona integrada con la aerolínea (consultar y deshacer).
8. La cola funciona integrada con el embarque.
9. La recursividad aplica el límite estricto del 55 % (11 de 20 no cuenta).
10. Una fecha sin vuelos produce un resultado vacío.
11. Un vuelo o pasajero inexistente devuelve `null`.
12. Registrar en un vuelo inexistente lanza excepción.
13. Un pasaporte repetido lanza excepción.
14. Un vuelo con capacidad 0 lanza excepción.
15. Un vuelo lleno no acepta más pasajeros.
16. Una fecha vacía lanza excepción.

**Última ejecución:** 16 de 16 pruebas correctas.

## 6. Reparto de la exposición oral

Cada integrante explica un contenido técnico de la presentación (`docs/Presentacion_Equipo7.pptx`):

| Integrante | Diapositivas | Contenido |
|---|---|---|
| **Amalia Aguiar Pereda** | 2 y 6 | Problemática y cola FIFO |
| **Onel Alejandro Olivera Díaz** | 3 y 4 | Estructuras utilizadas y multilista de vuelos y pasajeros |
| **Pedro Luis Sarmiento Torres** | 7 y 8 | Recursividad (caso base, caso recursivo, condición del 55 %), demostración y pruebas |
| **María Fernanda Rodríguez Díaz** | 5 y 9 | Pila LIFO (referencia `tope`, O(1)) y conclusiones |

## 7. Declaración sobre el uso de Inteligencia Artificial

Se usó la IA **Claude (Anthropic)** como apoyo, en estas partes concretas:

- **Código:** generó la versión base del proyecto (estructuras `Nodo`, `Pila` y `Cola`, las clases del dominio, la multilista y los métodos recursivos).
- **Pruebas:** redactó las pruebas iniciales y, después de una revisión, amplió con los casos negativos (excepciones, elementos no encontrados, vuelo lleno).
- **Documentación:** redactó borradores de este `README.md` y de la presentación, y los corrigió para que coincidan con el código final.
- **Configuración:** `pom.xml`, `.gitignore` y scripts de ejecución.

El equipo **modificó y simplificó el código** (por ejemplo, agrupó las clases en menos archivos), lo ejecutó, lo revisó y puede explicarlo con sus propias palabras.

## 8. Contenidos de la asignatura

- TDA Pila: LIFO, apilar, desapilar y cima.
- TDA Cola: FIFO, encolar y desencolar.
- Nodos y listas enlazadas.
- Estructuras multidimensionales y multilistas.
- Recursividad: caso base y caso recursivo.
