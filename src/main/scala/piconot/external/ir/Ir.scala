package piconot.external

import scala.language.implicitConversions
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

////////////////////////////////////////////////////////////////////////////////
// Implementation stuff: not part of the abstract syntax
////////////////////////////////////////////////////////////////////////////////

object Goto {
  implicit def stateName2Goto(name: StateName) = Goto(name)
}

/** Top level functions which imitate keywords in our language */
object Piconot {

  /** Create a command */
  def move(dir: Dir) = Move(dir)
  def face(dir: Dir) = Face(dir)

  /** Functions that convert from one or more commands to a rule. We use
   *  overloading instead of optional arguments so that keywords are
   *  unnecessary. This also ensures that commands are given in the order
   *  <move> <face> <goto>.
   */
  def rule(move: Move) = Rule(Some(move), None, None)
  def rule(face: Face) = Rule(None, Some(face), None)
  def rule(goto: Goto) = Rule(None, None, Some(goto))

  def rule(move: Move, face: Face) = Rule(Some(move), Some(face), None)
  def rule(move: Move, goto: Goto) = Rule(Some(move), None, Some(goto))
  def rule(face: Face, goto: Goto) = Rule(None, Some(face), Some(goto))

  def rule(move: Move, face: Face, goto: Goto) = Rule(Some(move), Some(face), Some(goto))


  /** Create a state with given name and rules. */
  def state(name: StateName)(body: Rule*) = State(name, body)

  def runPiconot(states: State*) = PiconotRunner.run(states)
}
