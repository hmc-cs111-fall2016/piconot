package piconot.internal

import scala.language.implicitConversions
import picolib.maze.Maze
import picolib.semantics._
import scala.collection.mutable.MutableList
import scalafx.application.JFXApp

/**
  * @author ben
  *
  * This design and implementation leaves a *lot* to be desired. But I wanted to
  * give a sample solution that wasn't too innovative on syntax design and that
  * contained a couple of implementation techniques that you might not have
  * thought of at first.
  *
  * No, the name "Picobor" is not a typo -- this is a boring language :).
  */

class PicoWhile(val mazeFilename: String) extends JFXApp {

  // the list of rules, which is built up as the Picobor program executes
  private val rules = MutableList.empty[Rule]
  private val program = PicoWhile.this

  def addRule(rule: Rule) = rules += rule

  def run() = {
    val maze = Maze(mazeFilename)
    val picobot =
      new Picobot(maze, rules.toList) with TextDisplay with GUIDisplay
    stage = picobot.mainStage
    picobot.run()
  }

  /////////////////////////////////////////////////////////////////////////////
  // Internal DSL definition
  /////////////////////////////////////////////////////////////////////////////

  private var nextState: Int = 0

  implicit def intToState(i: Int): State = State(i.toString)
  def anySurr: Surroundings = Surroundings(Anything, Anything, Anything, Anything)

  // converts to surroundings with one open direction
  def free(dir: MoveDirection): Surroundings = dir match {
    case North => Surroundings(Open, Anything, Anything, Anything)
    case East => Surroundings(Anything, Open, Anything, Anything)
    case West => Surroundings(Anything, Anything, Open, Anything)
    case South => Surroundings(Anything, Anything, Anything, Open)
  }

  // helper function - returns the opposite relative description of surrounding
  def opp(r: RelativeDescription): RelativeDescription = r match {
    case Open => Blocked
    case Blocked => Open
    case Anything => Anything
  }

  // returns the "opposite surroundings"
  def opposite(surr: Surroundings): Surroundings = {
    Surroundings(opp(surr.north), opp(surr.east), opp(surr.west), opp(surr.south))
  }

  // ensures that states flow correctly - recursive
  def fixStates(rules: List[Rule], loopState: Int): List[Rule] = rules match {
      // creates rule for looping back to original while loop
    case List() =>
      nextState += 1
      List(Rule(nextState-1, anySurr, StayHere, loopState))

      // fixes loop states for inner while and adds rule for exiting inner loop
    case first :: tail if (first.startState == first.endState) =>
        val fixedStates = List(
          first.copy(startState = nextState, endState = nextState),
          Rule(nextState, opposite(first.surroundings), StayHere, nextState+1)
        )
        nextState += 1

        fixedStates ::: fixStates(tail, loopState)

      // fixes states
    case first :: tail =>
      val fixedStates = List(
        first.copy(startState = nextState, endState = nextState+1)
      )
      nextState += 1

      fixedStates ::: fixStates(tail, loopState)
  }

  def move(dir: MoveDirection): Rule = {
    Rule(nextState, anySurr, dir, nextState+1)
  }

  // inner loop
  // assume no whiles inside
  // only one rule allowed inside
  def WhileInner(condition: Surroundings)(body: Rule): Rule = {
    Rule(nextState, condition, body.moveDirection, nextState)
  }

  // outer loop
  def WhileOuter(condition: Surroundings)(body: List[Rule]): Unit = {
    // This is the start state which checks at the start of the while loop
    // that the while condition has been met
    val temp = nextState
    program.addRule(Rule(nextState, condition, StayHere, nextState+1))
    nextState += 1

    // ensures states flow correctly
    fixStates(body, temp).map(program.addRule)

    // Exit while loop if condition is false
    program.addRule(Rule(temp, opposite(condition), StayHere, nextState))
  }

  // TODO DELETE
  def printRules() = print(rules)
}