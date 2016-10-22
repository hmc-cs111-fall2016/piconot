package picobot.internal

import picolib.{semantics => API}
import scala.language.implicitConversions
import scala.util.matching.Regex

/*
val rules = List(
 "state1" & "N! E_" -> (East, "state1"),
 "state1" & "NW! SE_" -> (West, "state2")
)
*/
// implicitly convert first string to object (of type State) with '&' methdod which returns an
// object (StateAndSurroundings) which has an -> method which takes in tuple of, direction and state

case class StateAndSurroundings(name: String, surroundings: API.Surroundings) {
  def ->(args: (API.MoveDirection, String)) = {
    val (direction, targetState) = args
    API.Rule(
      API.State(name),
      surroundings,
      direction,
      API.State(targetState)
    )
  }
}

case class State(name: String) {
  /* Helper function for and
   * Determines the status of a direction
   * given two strings specifying the walls and blanks.
   * Unspecified behavior if a user specifies that a direction is both a wall
   * and a blank
   */
  def surroundStatus(walls: String, blanks: String, direction: Char) = {
    if (walls != null && (walls contains direction))
      API.Blocked
    else if (blanks != null && (blanks contains direction))
      API.Open
    else
      API.Anything
  }

  def and(surr: String) : StateAndSurroundings = {
    // parse surroundings
    val reg = "([N|E|W|S]{1,4}\\!)? *([N|E|W|S]{1,4}_)?".r
    val reg(walls, blanks) = surr

    val n = surroundStatus(walls, blanks, 'N')
    val e = surroundStatus(walls, blanks, 'E')
    val w = surroundStatus(walls, blanks, 'W')
    val s = surroundStatus(walls, blanks, 'S')

    val surroundings = API.Surroundings(n,e,w,s)
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


