package piconot.external

import PiconotTypes._

package object PiconotTypes {
  type StateName = String
}

/** a state is encoded with its name and list of rules */
case class State(val name: StateName, val rules: Seq[Rule])

/** a Command is a part of a rule. Commands can be used to move,
  * change direction, or change state.
  */
abstract class Command

case class Rule(val move: Option[Move], val face: Option[Face], val goto: Option[Goto])

/** a Move command instructs picobot to take one step in the given direction */
case class Move(val dir: Dir) extends Command

/** a Face command instructs picobot to rotate in the given direction */
case class Face(val dir: Dir) extends Command

/** a Goto command causes a state change to the given state */
case class Goto(val stateName: StateName) extends Command

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


object Goto {
  implicit def stateName2Goto(name: StateName) = Goto(name)
}
