package piconot.external
import picolib.semantics._
import scala.util.parsing.combinator._

/*
 * Parser used to parse our External DSL for creating simple rules in Piconot.
 * 
 * Syntax for a valic Picobot rule in Anna & Sophia's DSL:
 * 
 *	rule ::= currentState [freeDirections] [occDirections] ":" dir nextState
 *  (freeDirections and occDirections are in brackets since they are optional)
 *  currentState ::= digit
 *  freeDirections ::= comma-separated list of "N","E","W","S" between parenthesis
 *  occDirections ::= comma-separated list of "N","E","W","S" between brackets
 *  dir := "N"|"E"|"W"|"S"|"_"
 *  nextState := digit | * | +
 */
object Parser extends RegexParsers {
  
  // Parse a list of rules
  def apply(s: String): ParseResult[List[Rule]] = parseAll(program, s)
  
  def program: Parser[List[Rule]] = rule*
  
  // Rules require a current state, next direction, and next state.
  // A list of free directions and a list of occupied directions are optional.
  def rule : Parser[Rule] = (
    currentState ~ freeDirections ~ ":" ~ dir ~ nextState ^^
      { case currState ~ free ~ ":" ~ dir ~ nextState   ⇒
        Condition(currState, free, Occupied(List()), dir, nextState).createRule }
    | currentState ~ occDirections ~ ":" ~ dir ~ nextState ^^
      { case currState ~ occ ~ ":" ~ dir ~ nextState   ⇒
        Condition(currState, Free(List()), occ, dir, nextState).createRule }
    | currentState ~ freeDirections ~ occDirections ~ ":" ~ dir ~ nextState ^^
      { case currState ~ free ~ occ ~ ":" ~ dir ~ nextState   ⇒
        Condition(currState, free, occ, dir, nextState).createRule }
 )
  
  // Converts current state to and integer.
  def currentState : Parser[Int] = 
     ("""^\d""".r ^^ { s => (s.toInt)})
   
  // Converts list of directions between parenthesis to a Free object
  // used as an intermediate representation.
  def freeDirections : Parser[Free] =  
   "(" ~> repsep(dir, ",") <~ ")" ^^ { (fd: List[MoveDirection]) => Free(fd) }
   
  // Converts list of directions between brackets to an occupied object
  // used as an intermediate representation.
  def occDirections : Parser[Occupied] =  
   "[" ~> repsep(dir, ",") <~ "]" ^^ { (od: List[MoveDirection]) => Occupied(od) }
  
  // Converts N,E,W,S, and _ to appropriate MoveDirection objects.
  def dir: Parser[MoveDirection] =
       (   ("N" ^^^ North)
         | ("E" ^^^ East)
         | ("W" ^^^ West)
         | ("S" ^^^ South)
         | ("_" ^^^ StayHere) )
  
  // The next state can be specified by an integer (which specifies how many states
  // to move back or ahead), an '*' (which specifies to remain in the same state),
  // or a '+' (which specifies to move 1 state ahead).
  def nextState : Parser[Int] = 
   ("""^-?[0-9]\d*""".r ^^ { s => (s.toInt)}
   | """^\*""".r ^^ {s => 0}
   | """^\+""".r ^^ {s => 1})
   
  // For parsing comments
  override protected val whiteSpace = """(\s|//.*)+""".r
}