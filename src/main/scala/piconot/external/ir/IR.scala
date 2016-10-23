package picobot.external.ir

import picolib.{semantics => API}

/* Represents the entire program */
case class Prog(n: Int)//start: StartState, chunks: List[StateChunk]) 
  
/* Represents the starting state */
case class StartState(name: String)

/* Represents a set of rules for a given state */
case class StateChunk(name: String, rules: List[Rule])

case class Rule(surroundings: API.Surroundings, 
                movementName: Option[String],
                newState: Option[String]) {
  def getMovement = movementName match {
    case None => API.StayHere
    case Some("N") => API.North
    case Some("E") => API.East
    case Some("W") => API.West
    case Some("S") => API.South
  }
}

case class Surr(walls: String, blanks: String) {
  
  /* Helper function to determine the status of a direction
   * given two strings specifying the walls and blanks.
   * Unspecified behavior if a user specifies that a direction is both a wall
   * and a blank.
   */
  def surroundStatus(walls: String, blanks: String, direction: Char) = {
    if (walls != null && (walls contains direction))
      API.Blocked
    else if (blanks != null && (blanks contains direction))
      API.Open
    else
      API.Anything
  }

  def translate = {
    val n = surroundStatus(walls, blanks, 'N')
    val e = surroundStatus(walls, blanks, 'E')
    val w = surroundStatus(walls, blanks, 'W')
    val s = surroundStatus(walls, blanks, 'S')
    API.Surroundings(n,e,w,s)
  }
}

