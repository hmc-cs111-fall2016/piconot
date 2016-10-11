package piconot.internal

import scala.language.implicitConversions
import PiconotTypes._

package object PiconotTypes {
  type Rule = Seq[Command]
  type StateName = String
}

/** a state is encoded with its name and list of rules */
case class State(val name: StateName, val rules: Seq[Rule])

/** a Command is a part of a rule. Commands can be used to move,
  * change direction, or change state.
  */
abstract class Command

/** a Move command instructs picobot to take one step in the given direction */
case class Move(val dir: Dir) extends Command

/** a Face command instructs picobot to rotate in the given direction */
case class Face(val dir: Dir) extends Command

/** a Goto command causes a state change to the given state */
case class Goto(val state: StateName) extends Command

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

object Rule {
  def apply(commands: Command*) = commands
}

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
  def rule(move: Move) = Rule(move)
  def rule(move: Move, face: Face) = Rule(move, face)
  def rule(move: Move, face: Face, goto: Goto) = Rule(move, face, goto)
  def rule(face: Face) = Rule(face)
  def rule(face: Face, goto: Goto) = Rule(face, goto)
  def rule(goto: Goto) = Rule(goto)
  def rule(move: Move, goto: Goto) = Rule(move, goto)

  /** Create a state with given name and rules. */
  def state(name: StateName)(body: Rule*) = State(name, body)

  def runPiconot( startstate: StateName,
           startdir: Dir, states: State*) {
    //run
  }
}
