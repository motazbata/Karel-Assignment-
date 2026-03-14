# Karel Assignment (atypon assignment)

This repository contains a **Stanford Karel** program written in Java (`Homework.java`). The program analyzes the world size (rows/columns) and then places beepers in specific patterns to “divide” the world depending on its dimensions.

## Files

- `Homework.java` — Main Karel solution (extends `stanford.karel.SuperKarel`)
- `report (1).pdf` — Project report / write-up

## What the program does

When Karel starts, it:

1. **Measures the world size**
   - Moves to the wall to compute the **width** (number of rows printed as `Number of rows`)
   - Turns left and moves to the wall to compute the **height** (number of columns printed as `Number of columns`)

2. **Handles special cases**
   - If the world is too small to divide (ex: `1x1`, `1x2`, `2x1`), it prints:
     - `Cannot be divided!`
     - and stops.

3. **Places beepers based on world dimensions**
   The logic branches based on whether the world is:
   - `2x2` (places a diagonal beeper pattern)
   - single dimension (`1xN` or `Nx1`)
   - double dimension (`2xN` or `Nx2`)
   - mixed parity sizes (even×odd or odd×even)
   - square even×even cases
   - general odd×odd or even×even handling

4. **Outputs statistics**
   At the end it prints:
   - `Total number of moves: ...`
   - `Total number of beepers: ...`

## How to run

This project assumes you are using the **Stanford Karel Java library** (the `stanford.karel.SuperKarel` base class).

Typical steps:

1. Open the project in your Java IDE (or the Stanford Karel environment).
2. Ensure the Stanford Karel libraries are included in the classpath.
3. Run the `Homework` class in a Karel world.

> Note: Exact setup depends on the course/IDE template you were provided.

## Notes on implementation

- The class overrides `move()` and `putBeeper()` to count:
  - number of moves (`numberOfMoves`)
  - number of beepers placed (`numOfBeepers`)
- The code includes helper methods for movement and beeper placement such as:
  - `moveUntilWall()`
  - `moveStepsWithBeepers(...)`
  - `placeDiagonalBeepers(...)`
  - `placeBeepersSingleDimension(...)`
  - `placeBeepersDoubleDimension(...)`

## Author

- GitHub: `motazbata`
