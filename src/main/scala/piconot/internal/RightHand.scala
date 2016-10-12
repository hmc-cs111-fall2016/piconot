package piconot.internal

import picolib.semantics._

import scala.language.postfixOps

/**
  * Created by tmf on 10/11/16.
  */
object RightHand extends PicoWhile("resources" / "maze.txt") {

  /*

   Our language can't actually do this with our current functions,
   and we already spent 4 hours on this...

   If we had time, we would add "moving" function that told us what our last move was
   because we would have a While(moving(forward)), which would be the equivalent of
   state 0 in the sample solution

   Also, Tiff feels like this would require even more nesting (which we didn't implement past 1 nest)

   */



  // SAMPLE SOLUTION
  // A picobot program that can solve a maze, using the right-hand rule

  // State 0: moving north
  (0 `*x**`) → E (1)    // we can go right (to the east)
  (0 `xE**`) → N (0)    // we can't go right, try going forward (north)
  (0 `NE**`) → X (3)    // we can't go right or forward, try turning around (to the south)

  // State 1: moving east
  (1 `***x`) → S (3)    // we can go right (to the south)
  (1 `*x*S`) → E (1)    // we can't go right, try going forward (east)
  (1 `*E*S`) → X (2)    // we can't go right or forward, try turning around (to the west)

  // State 2: moving west
  (2 `x***`) → N (0)    // we can go right (to the north)
  (2 `N*x*`) → W (2)    // we can't go right, try going forward (west)
  (2 `N*W*`) → X (1)    // we can't go right or forward, try turning around (to the east)

  // State 3: moving south
  (3 `**x*`) → W (2)    // we can go right (to the west)
  (3 `**Wx`) → S (3)    // we can't go right, try going forward (south)
  (3 `**WS`) → X (0)    // we can't go right or forward, try turning around (to the north)

  run
}
