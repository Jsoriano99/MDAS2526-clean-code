# Legacy Trivia Game - Refactoring Kata

A coding kata focused on refactoring legacy Java code through a programming trivia game.

## Workshop Overview

This is a legacy codebase that implements a trivia board game with programming-focused questions. The code intentionally contains various code smells, design issues, and opportunities for improvement - making it perfect for practicing refactoring techniques in a workshop setting.

## Game Rules

- 2-6 players move around a 12-space board
- Players answer questions from 4 categories:
  - **Software History** (spaces 1, 5, 9)
  - **Programming Languages** (spaces 2, 6, 10) 
  - **Refactoring** (spaces 3, 7, 11)
  - **Testing** (spaces 4, 8, 12)
- Correct answers earn gold coins
- Wrong answers send players to the penalty box
- First player to 6 gold coins wins

## Getting Started

### Prerequisites
- Java 17
- Gradle (or use included wrapper)

### Running the Game
```bash
./gradlew run
```

### Running Tests
```bash
./gradlew test
```

## Workshop Instructions

### Git Tags

Run this in your terminal to get to the starting point of the workshop:

`git checkout workshop-start`

Run this in your terminal to jump ahead where tests are added:

`git checkout workshop-tests`


### Phase 1: Understand the Code
1. **DO NOT** modify `PlayGame.java` - use it only to understand the game mechanics
2. Run the game manually to understand how it works
3. Examine the failing test in `GameTest.java`
4. Identify code smells and design issues in `Game.java`

### Phase 2: Add Test Coverage
- Write comprehensive tests for the `Game` class
- Focus on behavior rather than implementation details
- Ensure tests pass with the current implementation

### Phase 3: Refactor Safely
- Apply refactoring techniques while keeping tests green
- Extract methods and classes
- Eliminate code duplication
- Improve naming and readability

## Common Code Smells to Address

- **Long Method**: Methods exceeding 25 lines
- **Duplicated Code**: Similar logic repeated across methods
- **Large Class**: `Game` class handles too many responsibilities
- **Primitive Obsession**: Using arrays instead of proper data structures
- **Feature Envy**: Methods accessing data from other objects
- **Magic Numbers**: Hard-coded values without clear meaning

## Learning Objectives

- Practice identifying code smells
- Apply refactoring techniques safely with tests
- Experience working with legacy code
- Improve code readability and maintainability
- Learn to refactor incrementally

## Tips for Success

1. **Small Steps**: Make one small change at a time
2. **Run Tests**: Verify tests pass after each change
3. **Commit Often**: Save progress with frequent commits
4. **Focus on Behavior**: Preserve the game's functionality
5. **Question Everything**: Challenge assumptions about the current design

Happy refactoring!