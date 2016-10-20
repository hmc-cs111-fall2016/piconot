
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

class StateAndSurroundings(name: String, surroundings: IR.Surroundings) {}

class State(name: String) {
  def &(surroundings: String) = {
    // parse surroundings
    val surroundings = IR.Surroundings(Blocked, Open, Anything, Anything))
    StateAndSurroundings(name, surroundings)
  }
}

object State {
  implicit def string2State(str: String): State = {
    State(str)
  }
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


