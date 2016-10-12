package piconot.internal

import picolib.semantics
import picolib.semantics.{Surroundings, Anything, Open, Picobot}
import picolib.maze._
import PiconotTypes._

object PiconotRunner {
  def run(startstate: StateName, startdir: Dir, states: Seq[State]) = {
    states.map(evalState).fold(List())(_ ++ _)
  }

  def evalState(state: State): List[semantics.Rule] = {
    state.rules.map(evalRule(state.name, _)).fold(List())(_ ++ _)
  }

  def evalRule(stateName: StateName, rule: Rule): List[semantics.Rule] = {
    val dirs = List(North, East, West, South)

    dirs.map((dir : Dir) => {
      val (surroundings, move) = evalMove(dir, rule)

      semantics.Rule (
        semantics.State(combineStateName(stateName, dir)),
        surroundings,
        move,
        nextState(stateName, dir, rule)
        )
      }
    )
  }

  def evalMove(currentDir: Dir, rule: Rule) =
    rule.move match {
      case Some(Move(dir)) => absDir(currentDir, dir) match {
        // We only require that the direction we want to move in is open
        case North => (Surroundings(Open, Anything, Anything, Anything), semantics.North)
        case East  => (Surroundings(Anything, Open, Anything, Anything), semantics.East)
        case West  => (Surroundings(Anything, Anything, Open, Anything), semantics.West)
        case South => (Surroundings(Anything, Anything, Anything, Open), semantics.South)
      }

      // If we're not trying to move, we don't care about the surroundings
      case None => (Surroundings(Anything, Anything, Anything, Anything), semantics.StayHere)
  }

  def nextState(currentState: StateName, currentDir: Dir, rule: Rule) = {
    val nextStateDir = rule match {
      case Rule(_, Some(Face(dir)), _)       => absDir(currentDir, dir)
      case Rule(Some(Move(dir)), None, _) => absDir(currentDir, dir)
      case _                                 => currentDir
    }

    val nextStateName = rule.goto.getOrElse(Goto(currentState)).stateName

    semantics.State(combineStateName(nextStateName, nextStateDir))
  }

  def combineStateName(stateName: StateName, nextStateDir: Dir) =
    s"${stateName}_${nextStateDir.toString()}"

  def absDir(currentDir: Dir, dir: Dir) = dir match {
    case Right => currentDir match {
      case North => East
      case East => South
      case South => West
      case West => North
    }

    case Left => currentDir match {
      case North => West
      case West => South
      case South => East
      case East => North
    }

    case Backward => currentDir match {
      case North => South
      case South => North
      case East => West
      case West => East
    }

    case Forward => currentDir
    case _ => dir
  }
}
