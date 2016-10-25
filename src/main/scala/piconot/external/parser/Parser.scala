package piconot.external.parser

import scala.language.postfixOps
import scala.util.parsing.combinator._

import picolib.semantics._


/*
 * Our Syntax
 *
 * rule ::= state "if" pattern "go" nextState
 * state ::= "North" | "South" | "East" | "West"
 * pattern ::= something
 *             | nothing
 *             | something "and" nothing
 * something ::= "something" dir
 * nothing ::= "nothing" dir
 * dir ::= [N | E | W | S]*
 * nextState ::= state
 *
 *
 */

object PiconotParser extends RegexParsers {

  // for parsing comments
  override protected val whiteSpace = """(\s|#.*)+""".r

  // parsing interface
  def apply(s: String): ParseResult[List[Rule]] = parseAll(program, s)

  def program: Parser[List[Rule]] = rule*

  def rule: Parser[Rule] =
    state ~ "if" ~ pattern ~ "go" ~ nextState
	{ case stateNow ~ "if" ~ surroundings ~ "go"  ~ Dir ⇒
	  Rule(DirToState(stateNow), surroundings, Dir, DirToState(Dir)) }

  def state: Parser[MoveDirection] = 
    ( ("North" ^^^ North)
	| ("South" ^^^ South)
	| ("East" ^^^ East)
    | ("West" ^^^ West))

  def pattern: Parser[Surroundings] =
    ( something
    | nothing
    | something ~ "and" ~ nothing 
	| nothing ~ "and" ~ something
	{ case sth ⇒ sth
	  case nth ⇒ nth
	  case sth ~ "and" ~ nth ⇒ 
	  var combinedList = Array[RelativeDescription](Anything, Anything, Anything, Anything)
	    for(i <- 0 until 3){
	  	  if(sth(i) == Blocked){
	  	    combinedList(i) == Blocked
	  	  }
	  	  if(nth(i) == Open){
	  		combinedList(i) == Open //does not matter that this code allows line above to be overwritten;
	  			// won't happen in correct program
	  	  }
	  	}
	})




//only consider these cases for something and nothing for now to get Empty working
  def something: Parser[Array[RelativeDescription]] = """"?<=Something\\Q(\\E)(.*)(?=\\Q)\\E)""".r ^^ 
    { case "Something(N)" ⇒  Array[RelativeDescription](Blocked, Anything, Anything, Anything) 
    case "Something(E)" ⇒  Array[RelativeDescription](Anything, Blocked, Anything, Anything)
    case "Something(W)" ⇒  Array[RelativeDescription](Anything, Anything, Blocked, Anything)
    case "Something(S)" ⇒  Array[RelativeDescription](Anything, Anything, Anything, BLocked)
    // case "Something(N E)" ⇒  Surroundings(Open, Open, Anything, Anything)
    // case "Something(N E W)" ⇒  Surroundings(Open, Anything, Anything, Anything)
    case "Something(N W)" ⇒  Array[RelativeDescription](Open, Anything, Open, Anything)
    }
  def nothing: Parser[Array[RelativeDescription]] = """"?<=Nothing\\Q(\\E)(.*)(?=\\Q)\\E)""".r ^^ 
  { case "Nothing(N)" ⇒  Array[RelativeDescription](Open, Anything, Anything, Anything)
    case "Nothing(E)" ⇒  Array[RelativeDescription](Anything, Open, Anything, Anything)
    case "Nothing(W)" ⇒  Array[RelativeDescription](Anything, Anything, Open, Anything)
    case "Nothing(S)" ⇒  Array[RelativeDescription](Anything, Anything, Anything, Open)
   }

  def nextState: Parser[MoveDirection] = 
    state


  def DirToState(dir: MoveDirection): State =
  ( (North ^^^ 0)
  | (South ^^^ 1)
  | (East ^^^ 2)
  | (West ^^^ 3) )

}

