# Legacy Trivia Game — Práctica de Refactorización

Práctica de la asignatura **Modelado y Diseño Avanzado de Software** (MDAS, curso 2025/2026) enfocada en la aplicación de reglas de Clean Code y técnicas de refactorización sobre un juego de trivia de programación.

## 🎮 Reglas del Juego

- De 2 a 6 jugadores se mueven por un tablero de 12 casillas.
- Los jugadores responden preguntas de 4 categorías:
  - **Software History** (casillas 1, 5, 9)
  - **Programming Languages** (casillas 2, 6, 10)
  - **Refactoring** (casillas 3, 7, 11)
  - **Testing** (casillas 4, 8, 12)
- Las respuestas correctas otorgan monedas de oro.
- Las respuestas incorrectas envían al jugador a la casilla de penalización.
- Gana el primer jugador en conseguir 6 monedas de oro.

## 🚀 Primeros Pasos

### Requisitos
- Java 17
- Gradle (usar el wrapper incluido `./gradlew`)

### Ejecutar el juego
```bash
./gradlew run
```

### Ejecutar los tests
```bash
./gradlew test
```

### Tests de mutación (PIT)
```bash
./gradlew pitest
```

## 📋 Plan de Prácticas — Bloque 2: Código Limpio y Refactorización

El código original de `Game.java` contenía múltiples code smells de forma intencionada. Cada semana se aplicó un conjunto de reglas de Clean Code y técnicas de refactorización. Los cambios están registrados en el historial de commits.

| Semana | Tema | Commits | Reglas aplicadas |
|:---:|---|:---:|---|
| **S1** | Reglas de Nombrado | 2 | Meaningful Names, Avoid Disinformation, Magic Numbers |
| **S2** | Comentarios y Formato | 1 | Redundant Comments, Consistent Formatting, Braces |
| **S3** | Reglas de Funciones | 5 | DRY, Small Functions, CQS, Do One Thing, Dead Code |
| **S4** | Refactorización Manual | 5 | Extract Method, Rename, Consolidate Conditional, Explaining Variable |
| **S5** | Refactorización Automática | 2 | Quick-fix Generics, Rename Symbol (Shift+F6), Inline Variable |

### Estructura del Proyecto

```
legacy-trivia/
├── src/
│   ├── main/java/com/diamantetechcoaching/
│   │   ├── Game.java          ← clase principal (refactorizada)
│   │   └── PlayGame.java      ← juego interactivo (NO modificar)
│   ├── main/resources/questions/
│   │   ├── software_history.txt
│   │   ├── programming_languages.txt
│   │   ├── refactoring.txt
│   │   └── testing.txt
│   └── test/java/com/diamantetechcoaching/
│       └── GameTest.java      ← 23 tests automatizados
├── build.gradle
└── README.md
```

### Code Smells Abordados

- ✅ **Long Method**: Métodos extraídos (`announceTurn`, `announceLocationAndAskQuestion`, `processCorrectAnswer`, etc.)
- ✅ **Duplicated Code**: 4 métodos `load*Questions()` unificados en uno; avance de turno y movimiento extraídos
- ✅ **Large Class**: Métodos extraídos (7 nuevos en S3, 9 en S4). La clase `Player` queda pendiente como mejora futura
- ✅ **Primitive Obsession**: Pendiente de extraer a clase `Player` (4 arrays paralelos)
- ✅ **Magic Numbers**: Reemplazados por constantes (`MAX_PLAYERS`, `BOARD_SIZE`, `WINNING_COINS_COUNT`, etc.)
- ✅ **String comparison con ==**: Corregido a `.equals()`
- ✅ **Dead Code**: `createRockQuestion()` eliminado
- ✅ **Raw Types**: Migrados a genéricos con diamond operator

### Herramientas Utilizadas

- **OpenCode** (asistente de IA) — detección de code smells, generación de refactorizaciones, documentación
- **IntelliJ IDEA / VS Code** — refactorización automática (Quick-fix, Rename Symbol, Inline Variable)
- **JUnit 5 + AssertJ** — tests automatizados como red de seguridad
- **PIT Mutation Testing** — verificación de calidad de tests

## 📝 Notas

- `PlayGame.java` está marcado como **"NO MODIFICAR"**. Solo se usa para entender la mecánica del juego de forma interactiva.
- Los tests (`GameTest.java`) deben pasar después de cada cambio. Son la red de seguridad que garantiza que las refactorizaciones no rompen el comportamiento.
- El historial de commits documenta cada refactorización aplicada con el nombre de la regla correspondiente.

## 🔗 Enlaces

- **Repositorio**: [github.com/Jsoriano99/MDAS2526-clean-code](https://github.com/Jsoriano99/MDAS2526-clean-code)
- **Informe LaTeX**: `Cleancode/main.tex`
