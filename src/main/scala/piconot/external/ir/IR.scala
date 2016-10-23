package picobot.external.ir

import picolib.{semantics => API}

/* Represents the entire program */
case class Prog(start: String, chunks: List[StateChunk]) {
  def sortedRules = {
    // "sorts" the list such that the first element is the state named start
    // thus, this is the state the picobot will start in
    chunks.sortWith((sc1: StateChunk, sc2: StateChunk) => sc1.name == start)
      .map(_.getRules).flatten
  }
}

/* Represents a set of rules for a given state */
case class StateChunk(name: String, rules: List[Rule]) {
  def getRules = rules.map((r: Rule) => API.Rule(
                                          API.State(name), 
                                          r.surroundings, 
                                          r.getMovement, 
                                          API.State(r.newState.getOrElse(name))
                                        ))
}

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

