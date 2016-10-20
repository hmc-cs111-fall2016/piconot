package picobot.internal

import picolib.{semantics => IR}
import scala.language.implicitConversions

/*
val rules = List(
 "state1" & "N! E_" -> (East, "state1"),
 "state1" & "NW! SE_" -> (West, "state2")
)
*/
// implicitly convert first string to object (of type State) with '&' methdod which returns an
// object (StateAndSurroundings) which has an -> method which takes in tuple of, direction and state


// "a" & "b" // becomes a StateAndSurroundings

// StateAndSurroundings -> (East, "str") // returns a Rule

// test all of it together

case class StateAndSurroundings(name: String, surroundings: IR.Surroundings) {
  def ->(args: (IR.MoveDirection, String)) = {
    val (direction, targetState) = args
    IR.Rule(
      IR.State(name),
      surroundings,
      direction,
      IR.State(targetState)
    )
  }
}

case class State(name: String) {
  def and(surroundings: String) = {
    // parse surroundings
    val reg = """([N|E|W|S]{1,4}\!)? *([N|E|W|S]{1,4}_)?""".r
    val surroundings = IR.Surroundings(IR.Blocked, IR.Open, IR.Anything, IR.Anything)
    StateAndSurroundings(name, surroundings)
  }
}

object State {
  implicit def string2State(str: String): State = State(str)
}




// run(rules)

/*
  run should do these things(?):

  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  object EmptyBot extends Picobot(emptyMaze, rules)
    with TextDisplay with GUIDisplay

  stage = EmptyBot.mainStage

  EmptyBot.run()

*/


