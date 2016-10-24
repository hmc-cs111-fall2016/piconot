package piconot.external

import scala.language.implicitConversions
import PiconotTypes._



/** Top level functions which imitate keywords in our language */
object PiconotSugar {

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
