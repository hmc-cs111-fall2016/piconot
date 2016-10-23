package picobot.external.ir

import picolib.{semantics => API}

/* Represents the entire program */
case class Prog(n: Int)//start: StartState, chunks: List[StateChunk]) 
  
/* Represents the starting state */
case class StartState(name: String)

/* Represents a set of rules for a given state */
case class StateChunk(name: String, rules: List[Rule])

case class Rule(surroundings: API.Surroundings, 
                movement: Option[API.MoveDirection],
                newState: Option[String])
