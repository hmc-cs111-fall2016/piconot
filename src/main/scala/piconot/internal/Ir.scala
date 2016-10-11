package piconot.internal

import scala.language.implicitConversions
import PiconotTypes._

package object PiconotTypes {
  type StateName = String
  type Program = List[State]
}

/** a state is encoded with its name and list of rules */
case class State(val name: StateName, val rules: RuleList)

/** a RuleList defines the rules for a state */
abstract class RuleList

/** a Rule instructs picobot how to move, rotate, and change state in a given
 *  situation. Rules are applied in order until one succeeds.
 */
abstract class Rule

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

/** a Rule is a list of Commands. We implement this list behavior recursively.
 */
object EMPTY_RULE extends Rule
case class NonemptyRule(val init: Rule, val last: Command) extends Rule {
  def ~(command: Command) = NonemptyRule(this, command)
}

/** a RuleList is a list of Rules. We implement this list behavior recursively.
 */
object EMPTY_RULE_LIST extends RuleList
case class NonemptyRuleList(val init: RuleList, val last: Rule)
  extends RuleList {
    def |(ruleToAdd: Rule) = NonemptyRuleList(this, ruleToAdd)
  }

/** a ProgramRunner is used to encapsulate a Program (a list of States) and
 *  run that program.
 */
class ProgramRunner(val myProgram: Program) {
  // todo: implement run

  def run() = {}
}

/** Companion object to convert a program into a ProgramRunner */
object ProgramRunner {
  implicit def program2ProgramRunner(myProgram: Program) = new ProgramRunner(myProgram)
}

object Rule {
  /** We need to convert a Command to a Rule so that we can combine ensuing
   *  commands with ~
   */
  implicit def Command2Rule(command: Command) = NonemptyRule(EMPTY_RULE: Rule, command)
}

object RuleList {
  /** We need to convert a Rule to a RuleList so that we can combine ensuing
   *  Rules with |
   */
  implicit def Rule2RuleList(rule: Rule) = NonemptyRuleList(EMPTY_RULE_LIST: RuleList, rule)
}

/** Top level functions which imitate keywords in our language */
object Piconot {

  def state(name: StateName, rules: RuleList) = State(name, rules)

  def move(dir: Dir) = Move(dir)

  def face(dir: Dir) = Face(dir)

}
