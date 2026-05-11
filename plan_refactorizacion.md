# Plan de Refactorización y Guía: Clean Code Naming (legacy-trivia)

¡Hola! Como es tu primera vez aplicando estos principios, he preparado este plan no solo para proponer los cambios, sino también como una **guía** para que aprendas a identificar estos problemas en el futuro.

## ¿Cómo buscar violaciones de "Naming" en código Legacy?

Cuando te enfrentes a un código antiguo (Legacy), debes leerlo haciéndote las siguientes preguntas:

1. **¿El nombre revela la intención (Intention-Revealing)?** Si tienes que mirar el código de una función o buscar dónde se declara una variable para entender qué es, el nombre es malo.
2. **¿Es un nombre engañoso (Disinformation)?** Si el nombre dice una cosa pero hace otra, es un error crítico.
3. **¿Es pronunciable y fácil de buscar (Pronounceable / Searchable Names)?** Evita iniciales sueltas como `br` o `is`.
4. **¿Hay "Números Mágicos"?** Un número suelto en el código (como un `6` o un `12`) es un concepto sin nombre.

---

## Análisis y Cambios Propuestos en `Game.java`

Aplicando las preguntas anteriores al archivo `Game.java`, he encontrado estas violaciones claras que refactorizaremos:

### 1. Nombres Engañosos (Disinformation)
> **Problema:** Un nombre que miente sobre lo que hace.

- **Ubicación:** `didPlayerWin()` (línea 341).
- **El código actual:** `return !(purses[currentPlayer] == 6);`
- **¿Por qué está mal?** La función se llama "didPlayerWin" (¿ganó el jugador?). Sin embargo, devuelve `true` si el jugador **NO** tiene 6 monedas. Es decir, devuelve `true` cuando el juego debe continuar, no cuando gana. Esto confunde a cualquiera que lea el código.
- **Propuesta:** Renombrar el método a algo como `isGameInProgress()` o `playerHasNotWonYet()`.

### 2. Abreviaturas e Iniciales (Pronounceable / Avoid Encodings)
> **Problema:** Variables como `br` obligan a hacer un "mapeo mental".

- **Ubicación:** Métodos como `loadSoftwareHistoryQuestions()` (líneas 40-41).
- **El código actual:** 
  `InputStream is = ...`
  `BufferedReader br = ...`
- **¿Por qué está mal?** `is` y `br` son impronunciables y solo se entienden si recuerdas la clase que instancian.
- **Propuesta:** Renombrar `is` a `inputStream` y `br` a `reader`.

### 3. Nombres del Dominio del Problema poco claros (Intention-Revealing)
> **Problema:** Nombres que no tienen sentido en el contexto del negocio (un juego de trivia).

- **Ubicación:** `int[] purses = new int[6];`
- **El código actual:** Usa "purses" (bolsos/monederos) para contar los puntos.
- **¿Por qué está mal?** En un juego de trivia actual, los jugadores ganan "puntos" (scores) o "monedas" (coins), no bolsos.
- **Propuesta:** Renombrar `purses` a `coins` o `scores`.
- **Ubicación 2:** `int[] places = new int[6];` -> "places" es confuso, mejor `boardPositions` o `playerLocations`.

### 4. Números Mágicos (Conceptos sin nombre)
> **Problema:** Números quemados en el código que no explican su significado.

- **Ubicación:** Aparecen repetidamente el `6` y el `12`.
  - `int[] places = new int[6];` (¿Qué es 6? El número máximo de jugadores).
  - `if (places[currentPlayer] > 12)` (¿Qué es 12? El tamaño del tablero).
  - `purses[currentPlayer] == 6` (¿Qué es 6? Las monedas necesarias para ganar).
- **Propuesta:** Extraer estos números a constantes con nombres descriptivos:
  - `private static final int MAX_PLAYERS = 6;`
  - `private static final int BOARD_SIZE = 12;`
  - `private static final int WINNING_COINS_COUNT = 6;`

---

## Siguientes Pasos

Si estás de acuerdo con estos cambios y entiendes el porqué de cada uno, dímelo y procederé a aplicar estas modificaciones directamente en el archivo `Game.java`, añadiendo los comentarios que requiere tu práctica de la universidad.
