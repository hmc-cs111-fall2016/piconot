package piconot.internal

import PiconotTypes._

package object PiconotTypes {

    type StateName = String

    /** a Rule is a non-empty list of Commands. */
    type Rule = List[Command]

    /**
      * a Program is a set of states, each of which is uniquely identified by a
      * state name.
      */
    type Program = List[State]

}


/** a Command is a part of a rule (see grammar).
  * Commands can be used to move, change direction, or change state.
  */
abstract class Command

/** a Dir is used to specify the direction for picobot to move or face. */
abstract class Dir

/** Absolute directions */
object North extends Dir
object South extends Dir
object East extends Dir
object West extends Dir

/** Relative directions */
object Left extends Dir
object Right extends Dir
object Forward extends Dir
object Backward extends Dir

/** a Move command instructs picobot to take one step in the given direction */
case class Move(val dir: Dir) extends Command

/** a Face command instructs picobot to rotate in the given direction */
case class Face(val dir: Dir) extends Command

/** a Goto command causes a state change to the given state */
case class Goto(val state: StateName) extends Command

/** a state is encoded with its name and list of rules */
case class State(val name: StateName, val rules: List[Rule])
